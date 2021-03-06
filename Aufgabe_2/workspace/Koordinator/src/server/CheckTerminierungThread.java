package server;

import java.util.concurrent.LinkedBlockingQueue;

import koordinator.Nachricht;

public class CheckTerminierungThread implements Runnable {
    private boolean running;
    private KoordinatorImpl koordinator_ref;
    private LinkedBlockingQueue<Nachricht> queue = new LinkedBlockingQueue<Nachricht>();

    public CheckTerminierungThread(LinkedBlockingQueue<Nachricht> queue, KoordinatorImpl koordinator_ref) {
        this.queue = queue;
        this.koordinator_ref = koordinator_ref;
        this.running = true;
    }

    @Override
    public void run() {
        Nachricht msg = null;
        while (this.running) {

            try {
                msg = queue.take();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            if (this.running) {
                if (koordinator_ref.checkTerminierung(msg)) {
                    this.running = false;
                }
            }
        }
        this.queue.clear();
    }

    public void setRunning(boolean running) {
        this.running = running;
        Nachricht dummy_msg = new Nachricht();

        if (!running) {
            queue.add(dummy_msg);
        }
    }

}
