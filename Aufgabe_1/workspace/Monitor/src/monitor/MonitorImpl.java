package monitor;

import bank.MonitorPOA;

public class MonitorImpl extends MonitorPOA {

	public void meldung(String msg) {
		System.out.println(msg);
	}

	public synchronized void exit() {
		// MonitorClient.getThread().start();
		
		new Thread( new Runnable() {
            @Override public void run() {
                System.exit(0);
            }
          } ).start();
		
	}

}
