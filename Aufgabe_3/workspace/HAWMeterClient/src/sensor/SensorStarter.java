package sensor;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.ws.Endpoint;

import net.java.dev.jaxb.array.StringArray;
import web_sensor.DisplayNichtBekanntException_Exception;
import web_sensor.DisplayWirdVerwendetException_Exception;
import web_sensor.KoordinatorWahlException_Exception;
import web_sensor.SensorService;
import web_sensor.URLVergebenException_Exception;
import web_sensor.WebSensor;

//import 

public class SensorStarter {

	private final static int PORT_VERSUCHE = 100;
	private final static int ANMELDE_VERSUCHE = 10;

	private static String eigene_sensor_id ;
	private static Sensor eigener_sensor;
	private static String[] array_display_id;
	private static String koordinator_id;
	
	public static void main(String[] args) throws InterruptedException {
		// beispiel aufrufe
		// erster <Eigene URL> <Sensor1 URL> <Sensor2 URL> ....
		// neu <Eigene URL> <Bekannte URL> <Sensor1 URL> <Sensor2 URL> .....

		URL location;
//		String[] array_display_id = null;
//		String eigene_sensor_id = null;
		String bekannte_sensor_id;
//		String koordinator_id = null;
		Endpoint endpoint = null;
		int count = 0;
		boolean stop = false;

//		Sensor eigener_sensor = null;
		WebSensor bekannter_sensor;
		WebSensor koordinator;

		SensorService service;

		String operation[] = { "neu" };
		int offset = 0;

		for (int i = 0; i < args.length; i++) {
			for (int j = 0; j < operation.length; j++) {
				if (args[i].equalsIgnoreCase(operation[j])) {
					offset = i;
				}
			}
		}
		
		array_display_id = getArrayOfAnzeige(args, offset + 1);
		
		if (args[offset].equalsIgnoreCase("neu")) {
//			eigener_sensor = new Sensor(eigene_sensor_id, array_display_id);
			endpoint = publishSensor(true);
			if (endpoint != null && endpoint.isPublished()) {
			    try {
                    eigener_sensor.initDisplays(array_display_id);
                    eigener_sensor.addSensor(eigene_sensor_id, array_display_id);         
                    eigener_sensor.startKoordinatorTimer();
                    System.out.println();
                    for (int i = 0; i < array_display_id.length; i++) {
                        System.out.println("Nutze Display : " + array_display_id[i]);
                    }
                    System.out.println("\nKoordinator wurde gestartet!\n"); 
                } catch (DisplayNichtBekanntException e) {
                    System.err.println(e.getMessage());
                    if (endpoint != null) {
                        endpoint.stop();
                        System.out.println("Sensor Beendet");
                        System.exit(0);
                    }
                }


            }else {
                System.out.println("Kein Port gefunden!");
            }

		}else {
			try {
				// kontaktiere bekannten Sensor und hole Koordinator.
				bekannte_sensor_id = args[0];
				location = new URL(bekannte_sensor_id);
				service = new SensorService(location);
				bekannter_sensor = service.getWebSensorPort();

				StringArray anzeigen_array = new StringArray();
				for (int i = 0; i < array_display_id.length; i++) {
					anzeigen_array.getItem().add(array_display_id[i]);
				}
				
//				koordinator_id = bekannter_sensor.getKoordinatorId();
//				eigener_sensor = new Sensor(eigene_sensor_id, koordinator_id);
//				
//				// kontaktiere koordinator zum anmelden			
//				location = new URL(koordinator_id);
//				service = new SensorService(location);
//				koordinator = service.getWebSensorPort();

				for (count = ANMELDE_VERSUCHE; count > 0; count--) {
					try {
						koordinator_id = bekannter_sensor.getKoordinatorId();
//						eigener_sensor = new Sensor(eigene_sensor_id, koordinator_id);
						// kontaktiere koordinator zum anmelden			
						location = new URL(koordinator_id);
						service = new SensorService(location);
						koordinator = service.getWebSensorPort();
						endpoint = publishSensor(false);
						if (endpoint != null && endpoint.isPublished()) {
						    System.out.println("\n Koordinator: "+koordinator_id+"\n");
						    eigener_sensor.initDisplays(array_display_id);
						    
						koordinator.anmelden(eigene_sensor_id, anzeigen_array);
						eigener_sensor.stopWahlTimer();
						eigener_sensor.startWahlTimer();
				        for (int i = 0; i < array_display_id.length; i++) {
				            System.out.println("\nNutze Display : " + array_display_id[i]);
				        }
						System.out.println("\nAnmeldung Erfolgreich!\n");
						}else {
                            System.out.println("Kein Port gefunden!");
                        }
						break;
					} catch (DisplayNichtBekanntException_Exception e) {
						System.err.println(e.getMessage());
                        if (eigener_sensor != null) {
                            eigener_sensor.stopWahlTimer(); 
                        }
						stop = true;
						break;
					} catch (DisplayWirdVerwendetException_Exception e) {
						System.err.println(e.getMessage());
                        if (eigener_sensor != null) {
                            eigener_sensor.stopWahlTimer(); 
                        }
						stop = true;
						break;
					} catch (KoordinatorWahlException_Exception e) {
						System.err.println(e.getMessage());
                        if (eigener_sensor != null) {
                            eigener_sensor.stopWahlTimer(); 
                        }

						Thread.sleep(1000);
					} catch (URLVergebenException_Exception e) {
                        System.err.println(e.getMessage());
                        stop = true;
                        if (eigener_sensor != null) {
                            eigener_sensor.stopWahlTimer(); 
                        }
                        break;
                    }catch (Exception e) {
                        System.err.println("Koordinator wurde beendet");
                        if (eigener_sensor != null) {
                            eigener_sensor.stopWahlTimer(); 
                        }
                        Thread.sleep(1000);
                    }
				}
				
				if (count <= 0) {
					System.out.println("Anmelde Versuche fehlgeschlagen bitte neu starten!");
				}

				if (stop && endpoint != null) {
					endpoint.stop();
					System.out.println("Sensor Beendet");
					System.exit(0);
				}

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (Exception e) {
			    System.err.println("Bekannter Sensor nicht erreichbar!");
            }

		}
		
	}

	private static String[] getArrayOfAnzeige(String args[], int offset) {
		String anzeigen[] = new String[args.length - offset];

		for (int i = 0; i < anzeigen.length; i++) {
			anzeigen[i] = args[offset + i];
		}

		return anzeigen;
	}
	
	private static Endpoint publishSensor(boolean koordinator) {
		
		Endpoint endpoint = null;
		int count = 0;
		for (count = PORT_VERSUCHE; count > 0; count--) {
			try {
				eigene_sensor_id = URLGenerate.getNextSensorURL();
				if (koordinator) {
					eigener_sensor = new Sensor(eigene_sensor_id, array_display_id);
				}else {
					eigener_sensor = new Sensor(eigene_sensor_id, koordinator_id);
				}

				endpoint = Endpoint.publish(eigene_sensor_id, eigener_sensor);
				System.out.println(eigene_sensor_id
						+ " ist nun Veroeffentlicht!");
				break;
			} catch (Exception e) {
				// Bind exception fangen.
				System.err.println("Port vergeben (" + count
						+ " Versuch), neuer Versuch wird gestartet!");
			}
		}
		
		return endpoint;
	}

}
