package sensor;

import java.util.HashMap;
import java.util.TimerTask;

import com.sun.xml.internal.ws.client.ClientTransportException;

import web_sensor.WebSensor;

public class WahlAlgorithmus extends TimerTask {
    
    private Sensor sensor;
    private String sensor_id;
    private HashMap<String, WebSensor> sensoren_service_map = new HashMap<String, WebSensor>();

    public WahlAlgorithmus(Sensor sensor, String sensor_id, HashMap<String, WebSensor> sensoren_service_map) {
        this.sensor = sensor;
        this.sensor_id = sensor_id;
        this.sensoren_service_map = sensoren_service_map;
    }

    @Override
    public void run() {
        if (!this.cancel()) {
            this.sensor.getMap_lock().lock();
            System.out.println("Koordinator wahl wird gestartet!");
            sensor.setKoordinator_wahl(true);
            String next_koordinator = sensor_id;
            
            for (String it : sensoren_service_map.keySet()) {
                // -1 := it ist groe�er als next Koordinator
                if (next_koordinator.compareTo(it) < 0) {
                    try {
                        // prueft ob Sensor noch erreichbar ist
                        sensoren_service_map.get(it).isAlive();
                        // sollte der methoden aufruf noch funktionieren ist der
                        // Sensor nicht erreichbar!
                        next_koordinator = it;
                    } catch (ClientTransportException e) {
                        // URL nicht erreichbar.
                        // wird beim n�chsten trigern geloescht!
                    } catch (Exception e) {
                        // URL nicht erreichbar.
                        // wird beim n�chsten trigern geloescht!
                    }
                }
            }

            this.sensor.setKoordinator_id(next_koordinator);

            if (next_koordinator.equals(this.sensor_id)) {
                this.sensor.startKoordinatorTimer();
                this.sensor.stopWahlTimer();
            }
            this.sensor.setKoordinator_wahl(false);
            this.sensor.getMap_lock().unlock();
        }
    }
    
    

}
