package ggtProzess;

import java.util.concurrent.LinkedBlockingQueue;

import koordinator.Nachricht;

public class ChannelReader implements Runnable {

    private LinkedBlockingQueue<Nachricht> queue = new LinkedBlockingQueue<Nachricht>();
    private ggT_ProzessImpl ggT_Ref;
    private boolean running;
    private boolean rechter_channel;

    public ChannelReader(LinkedBlockingQueue<Nachricht> queue, ggT_ProzessImpl ggT_Ref, boolean rechter_channel) {
        this.queue = queue;
        this.ggT_Ref = ggT_Ref;
        this.rechter_channel = rechter_channel;
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
                ggT_Ref.getSende_lock().lock();
                this.ggT_Ref.nachrichtAuswerten(msg, this.rechter_channel);
                ggT_Ref.getSende_lock().unlock();
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
