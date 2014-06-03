/*
 */

#include <time.h>
#include <sys/time.h>
#include <stdio.h>
#include <signal.h>
#include <pthread.h>
#include <string.h>
#include <unistd.h>
#include <stdlib.h>
#include <linux/unistd.h>
#include <netinet/in.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <sys/epoll.h>
#include <fcntl.h>

#include "datagram.h"


#define gettid() syscall(__NR_gettid)
#define sigev_notify_thread_id _sigev_un._tid

#define PRIO 80
#define POLICY SCHED_FIFO
//#define POLICY SCHED_RR
//#define POLICY SCHED_OTHER
#define CLOCK CLOCK_MONOTONIC

#define MAX_SLOTS 16
#define BEACON_FENSTER 20LL
#define ERSTE_SICHERHEITS_PAUSE 4LL
#define ZEITSCHLITZ 4LL
#define	ZWEITE_SICHERHEITS_PAUSE 12LL
#define ZYKLUS (BEACON_FENSTER + ERSTE_SICHERHEITS_PAUSE + MAX_SLOTS * ZEITSCHLITZ + ZWEITE_SICHERHEITS_PAUSE)

#define SEND_BEACON 0
#define SEND_DATA 1
/*
 *
 */
int main(int argc, char** argv) {
	int state = SEND_BEACON;
	
    struct timespec now;
	
    int fd;
    char* sourceaddr;
    int sourceport;

    char buf[1024];
    char buftmp[1024];
    char output[1024];

    int rc;
    int signr;
    struct sigevent beacon_sigev;
	struct sigevent send_sigev;
    timer_t beacon_timer;
	timer_t send_timer;
    struct sched_param schedp;
    sigset_t sigset;
    uint64_t superframeStartTime;
    int64_t superframeStartTimeError;
    uint64_t timeOffset;
    uint64_t nsecNow;
    int finished;

    struct itimerspec tspec;

    unsigned int frameCounter;
    unsigned int lastFrameCounter;
	//unsigned int lastBeacon;

    uint32_t beaconDelay;
	uint32_t randBeaconDelay;
	
    char hostname[128];
	char own_hostname[128];

	rc = gethostname(own_hostname, sizeof(own_hostname));
	
	if( rc < 0 ){
	fprintf(stderr, "Fehler bei gethostname!");
      exit(1);
    }
	
    //FILE* file;

    if( argc != 4 ){
      printf("Usage: clocksyn <MulticastAdresse> <portnummer> <slot>\n");
      exit(1);
    }
	char* mcastAdr = argv[1];
    int port = atoi( argv[2]);
	int slot  = atoi(argv[3]);
	
	if(slot >= MAX_SLOTS || slot < 0){
		fprintf(stderr, "Falscher Slot! Moegliche Slots: 0-15");
		exit(1);
	}

    //Initialisiere Socket.
    //Trete der Multicast-Gruppe bei
    //Aktiviere Signal SIGIO
    fd = initSocket( mcastAdr, port );
    if( fd < 0 ){
      exit(1);
    }

    //Definiere Ereignis fuer die Timer
    //Beim Ablaufen des Timers soll das Signal SIGALRM/SIGUSR1
    //an die aktuelle Thread gesendet werden.
	
	//Signal für sende Beacon
    beacon_sigev.sigev_notify = SIGEV_THREAD_ID | SIGEV_SIGNAL;
    beacon_sigev.sigev_signo = SIGALRM;
    beacon_sigev.sigev_notify_thread_id = gettid();

	//Signal fuer sende in der mitte des Slots
    send_sigev.sigev_notify = SIGEV_THREAD_ID | SIGEV_SIGNAL;
    send_sigev.sigev_signo = SIGUSR1;
    send_sigev.sigev_notify_thread_id = gettid();
	
    //Erzeuge den Timer
	timer_create(CLOCK, &beacon_sigev, &beacon_timer);
    timer_create(CLOCK, &send_sigev, &send_timer);	
	


    //Umschaltung auf Real-time Scheduler.
    //Erfordert besondere Privilegien.
    //Deshalb hier deaktiviert.
    ///*
    memset(&schedp, 0, sizeof (schedp));
    schedp.sched_priority = PRIO;
    sched_setscheduler(0, POLICY, &schedp);
    //*/


    //Lege fest, auf welche Signale beim
    //Aufruf von sigwaitinfo gewartet werden soll.
    sigemptyset(&sigset);
    sigaddset(&sigset, SIGIO);                  //Socket hat Datagramme empfangen
    sigaddset(&sigset, SIGALRM);                //Beacon_Timer ist abgelaufen
    sigaddset(&sigset, SIGINT);                 //Cntrl-C wurde gedrueckt
	sigaddset(&sigset, SIGUSR1);                //send_Timer ist abgelaufen
    sigprocmask(SIG_BLOCK, &sigset, NULL);

	/*

    //Erzeuge Datei zum Mittschnitt der Ereignisse
    file = fopen( "capture.dat", "w" );
    if( file == NULL ){
      perror( "fopen" );
      exit(1);
    }

	*/

    //Framecounter initialisieren
    frameCounter = 0;
    lastFrameCounter = 0;
    superframeStartTime = 0;

    //Differenz zwischen der realen Zeit und der synchronisierten Anwendungszeit.
    //Die synchronisierte Anwendungszeit ergibt sich aus der Beaconnummer.
    //Sie wird gerechnet vom Startzeitpunkt des Superframes mit der Beaconnummer 0
    timeOffset = 0;

    //Merker fuer Programmende
    finished = 0;
	
	randBeaconDelay = BEACON_FENSTER + ERSTE_SICHERHEITS_PAUSE;
	//erster Beacon
	tspec.it_interval.tv_sec = 0;
    tspec.it_interval.tv_nsec = 0;
    nsec2timespec( &tspec.it_value, (ZYKLUS + randBeaconDelay) /*msec*/ *1000*1000 );
    timer_settime(beacon_timer, 0, &tspec, NULL);
	
    while( finished == 0 ){

        //Lese empfangene Datagramme oder warte auf Signale
        //Diese Abfrage ist ein wenig tricky, da das I/O-Signal (SIGIO)
        //flankengesteuert arbeitet.
        signr=0;
        while( signr == 0 ){
          //Pruefe, ob bereits Datagramme eingetroffen sind.
          //Die muessen erst gelesen werden, da sonst fuer diese kein SIGIO-Signal ausgeloest wird.
          //Signal wird erst gesendet beim Uebergang von Non-Ready nach Ready (Flankengesteuert!)
          //Also muss Socket solange ausgelesen werden, bis es Non-Ready ist.
          //Beachte: Socket wurde auf nonblocking umgeschaltet.
          //Wenn keine Nachricht vorhanden ist, kehrt Aufruf sofort mit -1 zurueck. errno ist dann EAGAIN.
          rc = recvMessage( fd, buf, sizeof(buf), &sourceaddr, &sourceport );
          if( rc > 0 ){
            //Ok, Datagram empfangen. Beende Schleife
            signr = SIGIO;
            break;
          }
		  
          //Warte auf ein Signal.
          //Die entsprechenden Signale sind oben konfiguriert worden.
          siginfo_t info;
          if (sigwaitinfo(&sigset, &info) < 0){
            perror( "sigwait" );
            exit(1);
          }
          if( info.si_signo == SIGALRM ){
            //Beacon_Timer ist abgelaufen
            signr = SIGALRM;
            break;
          }else if(info.si_signo == SIGUSR1){
		     //Send_Timer ist abgelaufen
            signr = SIGUSR1;
            break;
		  }else if( info.si_signo == SIGINT ){
            //Cntrl-C wurde gedrueckt
            signr = SIGINT;
            break;
          }
        }

        //So, gueltiges Ereignis empfangen.
        //Nun geht es ans auswerten.
        /* Get current time */
        clock_gettime(CLOCK, &now);

        switch( signr ){
          case SIGALRM:
		  
			if(state == SEND_BEACON){
				//beacon_Timer ist abgelaufen.
				//Senden eines Beacon
				rc = encodeBeacon( buf, sizeof(buf), (lastFrameCounter + 1), randBeaconDelay, own_hostname );
				
				if(rc < 0){
					fprintf(stderr, "Fehler beim erstellen eines Beacon!");
				}else{	
					rc = sendMessage(fd, buf, mcastAdr, port);		
					if(rc < 0){
						fprintf(stderr, "Fehler beim senden des Beacon!");
					}	
				}
				printf("\nBeacon %i gesendet\n",(lastFrameCounter + 1));		
			}
			
            break;
			
			case SIGUSR1:
				
				if(state == SEND_DATA){
					//send_Timer ist abgelaufen.
					//Senden einer Slot Message
					rc = encodeSlotMessage( buf, sizeof(buf), slot, own_hostname );
					if(rc < 0){
						fprintf(stderr, "Fehler beim erstellen einer SlotMessage!");
					}else{	
						rc = sendMessage(fd, buf, mcastAdr, port);		
						if(rc < 0){
							fprintf(stderr, "Fehler beim senden einer Message!");
						}	
					}
					printf("\nNachricht auf Slot %i gesendet\n",slot);
					
					
					//Neue Zufahlszeit generieren
					randBeaconDelay = randomNumber(BEACON_FENSTER);
					//Konfiguriere Beacon_Timer fuer naechsten Superframe.
					tspec.it_interval.tv_sec = 0;
					tspec.it_interval.tv_nsec = 0;
					nsec2timespec( &tspec.it_value, superframeStartTime + (ZYKLUS + randBeaconDelay)/*msec*/ *1000*1000 );
					timer_settime(beacon_timer, TIMER_ABSTIME, &tspec, NULL);
					
					state = SEND_BEACON;
				}

            break;
          case SIGINT:
            //Cntrl-C wurde gedrueckt.
            //Programm beenden.
            finished = 1;
            break;
          case SIGIO:
            //Datagramm empfangen.

            if( buf[0] == 'B' ){
			
              //Empfangenes Datagram ist ein Beacon
              rc = decodeBeacon( buf, &frameCounter, &beaconDelay, hostname, sizeof(hostname) );
              if( rc < 0 ){
                printf( "### Invalid Beacon: '%s'\n", buf );
              } else if( frameCounter != lastFrameCounter){
				  lastFrameCounter = frameCounter;
				  printf("\nBeacon %i empfangen\n",(lastFrameCounter));
				  
				 //Beacon Timer stoppen
				tspec.it_interval.tv_sec = 0;
				tspec.it_interval.tv_nsec = 0;
				tspec.it_value.tv_sec = 0;
				tspec.it_value.tv_nsec = 0;
				timer_settime(beacon_timer, TIMER_ABSTIME, &tspec, NULL);
				  
				//Berechne den Zeitpunkt, an dem der Superframe begann
                superframeStartTime = timespec2nsec( &now ) - beaconDelay;

                //Starte Zeitmessung mit dem ersten empfangenen Beacon
                if( timeOffset == 0 ){
                  //Differenz zwischen der realen Zeit und der synchronisierten Anwendungszeit.
                  //Die synchronisierte Anwendungszeit ergibt sich aus der Beaconnummer.
                  //Sie wird gerechnet vom Startzeitpunkt des Superframes mit der Beaconnummer 0
                  timeOffset = superframeStartTime - frameCounter * ZYKLUS /* msec */ * 1000 * 1000;
                }

                //Berechne nsec seit dem Empfang des ersten Beacons
                nsecNow = timespec2nsec( &now ) - timeOffset;

                //Berechne den Fehler zwischen dem tatsaechlichen Startzeitpunkt des Superframes und dem erwarteten Zeitpunkt
                superframeStartTimeError = superframeStartTime - timeOffset - frameCounter * ZYKLUS /* msec */ * 1000 * 1000;
				
				/*
				//Synchronisiere die Zeit falls diese Uhr nachgeht
				if(superframeStartTimeError){
					timeOffset += superframeStartTimeError;
				}
				*/
				//Konfiguriere Send_Timer so das bei der haelfte seines Slots gesendet wird.
                tspec.it_interval.tv_sec = 0;
                tspec.it_interval.tv_nsec = 0;
                nsec2timespec( &tspec.it_value, superframeStartTime + (BEACON_FENSTER + ERSTE_SICHERHEITS_PAUSE + slot * ZEITSCHLITZ + (ZEITSCHLITZ >> 1))/*msec*/ *1000*1000 );
                timer_settime(send_timer, TIMER_ABSTIME, &tspec, NULL);
				
				state = SEND_DATA;
				
                snprintf( buftmp, sizeof(buftmp), "'%s'", buf );
                snprintf( output, sizeof(output), "---: %11.6f %-37s %9.3f\n", (nsecNow)/1.e9, buftmp, superframeStartTimeError/1.e6 );
                fputs( output, stdout );
				}
            } else if( buf[0] == 'D' ){
              //Empfangenes Datagram ist Slot Message
			  printf("\nNachricht empfangen\n");
              //Berechne nsec seit dem Empfang des ersten Beacons
              nsecNow = timespec2nsec( &now ) - timeOffset;
              snprintf( buftmp, sizeof(buftmp), "'%s'", buf );
              snprintf( output, sizeof(output), "   : %11.6f %s\n", (nsecNow)/1.e9, buftmp );
              fputs( output, stdout );
            } else {
              //Unknown Message
              snprintf( output, sizeof(output), "### Unknown Message: '%s'\n", buf );
              fputs( output, stdout );
            }

            break;
        }

    ////////////////////////////////////////////////////
	}

    //und aufraeumen
	timer_delete(beacon_timer);
	timer_delete(send_timer);

    /* switch to normal */
    schedp.sched_priority = 0;
    sched_setscheduler(0, SCHED_OTHER, &schedp);
	return 0;
}