
package web_sensor;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.FaultAction;
import net.java.dev.jaxb.array.StringArray;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "WebSensor", targetNamespace = "web_sensor")
@SOAPBinding(style = SOAPBinding.Style.RPC)
@XmlSeeAlso({
    net.java.dev.jaxb.array.ObjectFactory.class,
    web_sensor.ObjectFactory.class
})
public interface WebSensor {


    /**
     * 
     */
    @WebMethod
    @Action(input = "web_sensor/WebSensor/triggerRequest", output = "web_sensor/WebSensor/triggerResponse")
    public void trigger();

    /**
     * 
     */
    @WebMethod
    @Oneway
    @Action(input = "web_sensor/WebSensor/isAlive")
    public void isAlive();

    /**
     * 
     * @param displayId
     * @param sensorId
     * @throws KoordinatorWahlException_Exception
     * @throws URLVergebenException_Exception
     * @throws DisplayWirdVerwendetException_Exception
     * @throws DisplayNichtBekanntException_Exception
     */
    @WebMethod
    @Action(input = "web_sensor/WebSensor/anmeldenRequest", output = "web_sensor/WebSensor/anmeldenResponse", fault = {
        @FaultAction(className = DisplayWirdVerwendetException_Exception.class, value = "web_sensor/WebSensor/anmelden/Fault/DisplayWirdVerwendetException"),
        @FaultAction(className = DisplayNichtBekanntException_Exception.class, value = "web_sensor/WebSensor/anmelden/Fault/DisplayNichtBekanntException"),
        @FaultAction(className = URLVergebenException_Exception.class, value = "web_sensor/WebSensor/anmelden/Fault/URLVergebenException"),
        @FaultAction(className = KoordinatorWahlException_Exception.class, value = "web_sensor/WebSensor/anmelden/Fault/KoordinatorWahlException")
    })
    public void anmelden(
        @WebParam(name = "sensor_id", partName = "sensor_id")
        String sensorId,
        @WebParam(name = "display_id", partName = "display_id")
        StringArray displayId)
        throws DisplayNichtBekanntException_Exception, DisplayWirdVerwendetException_Exception, KoordinatorWahlException_Exception, URLVergebenException_Exception
    ;

    /**
     * 
     * @param displayId
     * @param sensorId
     */
    @WebMethod
    @Action(input = "web_sensor/WebSensor/addSensorRequest", output = "web_sensor/WebSensor/addSensorResponse")
    public void addSensor(
        @WebParam(name = "sensor_id", partName = "sensor_id")
        String sensorId,
        @WebParam(name = "display_id", partName = "display_id")
        StringArray displayId);

    /**
     * 
     * @return
     *     returns java.lang.String
     * @throws KoordinatorWahlException_Exception
     */
    @WebMethod(operationName = "getKoordinator_id")
    @WebResult(name = "koordinator_id", partName = "koordinator_id")
    @Action(input = "web_sensor/WebSensor/getKoordinator_idRequest", output = "web_sensor/WebSensor/getKoordinator_idResponse", fault = {
        @FaultAction(className = KoordinatorWahlException_Exception.class, value = "web_sensor/WebSensor/getKoordinator_id/Fault/KoordinatorWahlException")
    })
    public String getKoordinatorId()
        throws KoordinatorWahlException_Exception
    ;

    /**
     * 
     * @param displayId
     * @param sensorId
     */
    @WebMethod
    @Action(input = "web_sensor/WebSensor/removeSensorRequest", output = "web_sensor/WebSensor/removeSensorResponse")
    public void removeSensor(
        @WebParam(name = "sensor_id", partName = "sensor_id")
        String sensorId,
        @WebParam(name = "display_id", partName = "display_id")
        StringArray displayId);

}