package sensor;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class URLGenerate {
	
	private static int port = 8000;
	private final static String PROTOKOLL = "http://";
	private final static String SENSOR_ID = "/Sensor";
			
	public static String getNextSensorURL() {
		String own_id = "";
		
		try {
			own_id = PROTOKOLL+InetAddress.getLocalHost().getHostAddress()+":"+port+SENSOR_ID;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		port++;
		return own_id;
	}
}
