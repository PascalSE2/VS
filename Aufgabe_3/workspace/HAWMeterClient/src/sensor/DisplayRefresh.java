package sensor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TimerTask;

import web_sensor.WebSensor;

public class DisplayRefresh extends TimerTask {

    private Sensor sensor;
    private String sensor_id;
    HashMap<String, WebSensor> sensoren_service_map = new HashMap<String, WebSensor>();
    HashMap<String, String[]> sensoren_display_map = new HashMap<String, String[]>();
    ArrayList<String> zombies = new ArrayList<String>();

    public DisplayRefresh(Sensor sensor, String sensor_id, HashMap<String, WebSensor> sensoren_service_map, HashMap<String, String[]> sensoren_display_map) {
        this.sensor = sensor;
        this.sensor_id = sensor_id;
        this.sensoren_service_map = sensoren_service_map;
        this.sensoren_display_map = sensoren_display_map;
    }

    @Override
    public void run() {
        this.sensor.getMap_lock().lock();

        for (String it : sensoren_service_map.keySet()) {
            if (!this.sensor_id.equals(it)) {
                try {
                    sensoren_service_map.get(it).trigger();
                } catch (Exception e) {
                    zombies.add(it);
                }
            } else {
                this.sensor.trigger();
            }
        }

        for (String it : this.zombies) {
            try {
                this.sensor.updateSensorMap(it, this.sensoren_display_map.get(it), false);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }

        for (String iter : this.zombies) {
            this.sensor.removeSensor(iter, this.sensoren_display_map.get(iter));
        }

        this.zombies.clear();
        this.sensor.getMap_lock().unlock();
    }
}
