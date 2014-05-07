package server;


import java.util.concurrent.LinkedBlockingQueue;

import koordinator.Nachricht;

public class ProcessAnmeldung implements Runnable {

    private LinkedBlockingQueue<Nachricht> queue = new LinkedBlockingQueue<Nachricht>();
    private KoordinatorImpl kRef;
    private boolean running;

    public ProcessAnmeldung(LinkedBlockingQueue<Nachricht> queue, KoordinatorImpl kRef) {
        this.queue = queue;
        this.kRef = kRef;
        this.running = true;
    }

    @Override
    public void run() {
        Nachricht msg = null;
        while (running) {

            try {
                msg = queue.take();
                
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            if (running) {
            }
            
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
        Nachricht dummy_msg = new Nachricht();
        
        if (!running) {
            queue.add(dummy_msg);
        }
    }

}
