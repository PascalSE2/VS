package ggtProzess;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

import koordinator.Koordinator;
import koordinator.Nachricht;
import koordinator.NachrichtenTyp;
import koordinator.ggT_Prozess;
import koordinator.ggT_ProzessPOA;
import koordinator.monitor.Monitor;

public class ggT_ProzessImpl extends ggT_ProzessPOA {

    private LinkedBlockingQueue<Nachricht> linker_channel = new LinkedBlockingQueue<>();
    private LinkedBlockingQueue<Nachricht> rechter_channel = new LinkedBlockingQueue<>();
    private ChannelReader linker_channel_reader = new ChannelReader(linker_channel, this, false);
    private ChannelReader rechter_channel_reader = new ChannelReader(rechter_channel, this, true);
    ReentrantLock sende_lock = new ReentrantLock();

    private ggT_Prozess linkerNachbar;
    private ggT_Prozess rechterNachbar;
    private String rechterNachbarID;
    private String linkerNachbarID;
    private volatile int ggt;
    private boolean marker_linker_channel;
    private boolean marker_rechter_channel;
    private volatile boolean terminierung;
    private int sequenz_nummer;
    private String name_id;
    private int min_delay;
    private int max_delay;
    private Koordinator kRef;
    private Monitor monitor;

    public ggT_ProzessImpl(Koordinator kRef, String name_id, int min_delay, int max_delay) {
        this.kRef = kRef;
        this.name_id = name_id;
        this.min_delay = min_delay;
        this.max_delay = max_delay;
        Thread linker_thread = new Thread(this.linker_channel_reader);
        Thread rechter_thread = new Thread(this.rechter_channel_reader);
        linker_thread.start();
        rechter_thread.start();
    }

    public void berechne_ggT(int ggt) {
        int rest = 0;
        int rand = ((int) (Math.random()) * this.max_delay + this.min_delay);

        try {
            Thread.sleep(rand);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (ggt < this.ggt) {
            rest = ((this.ggt - 1) % ggt) + 1;

            this.ggt = rest;
            this.terminierung = false;
            sendBroadcast(NachrichtenTyp.GGT);
        }
    }

    public synchronized void sendBroadcast(NachrichtenTyp typ) {
        Nachricht msg = new Nachricht();
        msg.typ = typ;
        msg.ggt = this.ggt;
        msg.name_id = this.name_id;
        msg.sequenz = this.sequenz_nummer;
        msg.terminierung = this.terminierung;

        if (linkerNachbar != null) {
            linkerNachbar.addRechterChannel(msg);
        }

        if (rechterNachbar != null) {
            rechterNachbar.addLinkerChannel(msg);
        }
    }

    protected void nachrichtAuswerten(Nachricht msg, boolean von_rechts) {
        String id = null;

        if (msg.typ == NachrichtenTyp.START) {
//            System.out.println("Prozess " + name_id + " startet Berechnung!");
            sendBroadcast(NachrichtenTyp.GGT);
        } else if (msg.typ == NachrichtenTyp.GGT) {

            if (von_rechts) {
                this.monitor.rechnen(this.name_id, this.rechterNachbarID, msg.ggt);
            } else {
                this.monitor.rechnen(this.name_id, this.linkerNachbarID, msg.ggt);
            }

            berechne_ggT(msg.ggt);

        } else if (msg.typ == NachrichtenTyp.MARKER) {
//            System.out.println("linkerNachrbar: "+this.linkerNachbarID+", rechterNachrbar:"+ this.rechterNachbarID);
            if (msg.sequenz > this.sequenz_nummer) {

                this.marker_linker_channel = false;
                this.marker_rechter_channel = false;
                this.terminierung = true;
                this.sequenz_nummer = msg.sequenz;
                sendBroadcast(NachrichtenTyp.MARKER);

            }

//            if (msg.sequenz == this.sequenz_nummer) {

                if (von_rechts) {
//                System.out.println(this.name_id + " Marker: " + msg.sequenz + "von rechts");
                    this.marker_rechter_channel = true;
                    id = this.rechterNachbarID;
                }
                if (!von_rechts) {
//                System.out.println(this.name_id + " Marker: " + msg.sequenz + "von links");
                    this.marker_linker_channel = true;
                    id = this.linkerNachbarID;
                }
                
                
                Nachricht terminierungs_msg = new Nachricht();

                terminierungs_msg.typ = NachrichtenTyp.TERMINIERUNG;
                terminierungs_msg.terminierung = terminierung;
                terminierungs_msg.name_id = this.name_id;
                
                
                if (this.marker_linker_channel && this.marker_rechter_channel) {
                    
                    kRef.meldeTerminierungsStatus(terminierungs_msg);
                }

                this.monitor.terminieren(name_id, id, terminierung);
//            }
        }
    }

    @Override
    public void setLinkerNachbar(ggT_Prozess linker_nachbar) {
        this.linkerNachbar = linker_nachbar;
        this.linkerNachbarID = linker_nachbar.getId();
    }

    @Override
    public void setRechterNachbar(ggT_Prozess rechter_nachbar) {
        this.rechterNachbar = rechter_nachbar;
        this.rechterNachbarID = rechter_nachbar.getId();
    }

    @Override
    public ggT_Prozess getLinkerNachbar() {
        return this.linkerNachbar;
    }

    @Override
    public ggT_Prozess getRechterNachbar() {
        return this.rechterNachbar;
    }

    @Override
    public void addRechterChannel(Nachricht msg) {
        if (msg != null) {
            this.rechter_channel.add(msg);
        }
    }

    @Override
    public void addLinkerChannel(Nachricht msg) {
        if (msg != null) {
            this.linker_channel.add(msg);
        }
    }

    @Override
    public void set_ggT(int ggt) {
        this.ggt = ggt;
    }

    @Override
    public void setMonitor(Monitor m) {
        this.monitor = m;
    }

    @Override
    public String getId() {
        return this.name_id;
    }

    public ChannelReader getLinker_channel_reader() {
        return linker_channel_reader;
    }

    public ChannelReader getRechter_channel_reader() {
        return rechter_channel_reader;
    }

    public ReentrantLock getSende_lock() {
        return sende_lock;
    }

    @Override
    public void printErgebnis() {
        this.monitor.ergebnis(name_id, this.ggt);
    }

    @Override
    public void exit() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.exit(0);
            }
        }).start();
    }

    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return this.name_id.hashCode();
    }

}
