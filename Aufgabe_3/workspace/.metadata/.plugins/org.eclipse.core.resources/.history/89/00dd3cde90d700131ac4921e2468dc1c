package messwertfuerwebserviceaufgabe;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class MesswertFuerWebserviceAufgabe {

    public static void main(String[] args) throws InterruptedException {

        final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(1);

        final Runnable ticker = new Runnable() {
            @Override
            public void run() {
                long lTicks = new Date().getTime();
                int messwert = ((int) (lTicks % 20000)) / 100;
                if (messwert > 100) {
                    messwert = 200 - messwert;
                }
                //versenden der Trigger-Nachricht
                //....
                //....
                System.out.printf("%6d %10d\n", messwert, lTicks);
            }
        };
        //Starten:
        final ScheduledFuture<?> tickerHandle =
            scheduler.scheduleAtFixedRate(ticker, 2000 - new Date().getTime() % 2000, 2000, TimeUnit.MILLISECONDS);

        //Stoppen nach 20 sec einplanen:
        scheduler.schedule(new Runnable() {
            @Override
            public void run() {
                tickerHandle.cancel(true);
                scheduler.shutdown();
            }
        }, 20, TimeUnit.SECONDS);
    }
}
