package server;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import bank.Monitor;

public class SendMessage implements Runnable {

    private LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>();
    private BankImpl ref;
    private static ArrayList<Monitor> zombieListe = new ArrayList<Monitor>();
    private boolean running = true;
    public SendMessage(BankImpl ref, LinkedBlockingQueue<String> queue) {
        this.ref = ref;
        this.queue = queue;
    }
    
    @Override
    public void run() {
        String msg = null;
        while(running){
            ArrayList<Monitor> tmpList = ref.getMonitorListe();
            try {
                msg = queue.take();
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            for (Monitor monitor : tmpList) {
                try {
                    monitor.meldung(msg);
                    
                } catch (Exception e) {
                    zombieListe.add(monitor);
                }
            }

            for (Monitor monitor : zombieListe) {
                if (tmpList.contains(monitor)) {
                    tmpList.remove(monitor);
                }
            }  
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

}
