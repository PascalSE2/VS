
package web_sensor;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "SensorService", targetNamespace = "web_sensor", wsdlLocation = "http://192.168.56.1:8000/Sensor?wsdl")
public class SensorService
    extends Service
{

    private final static URL SENSORSERVICE_WSDL_LOCATION;
    private final static WebServiceException SENSORSERVICE_EXCEPTION;
    private final static QName SENSORSERVICE_QNAME = new QName("web_sensor", "SensorService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://192.168.56.1:8000/Sensor?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        SENSORSERVICE_WSDL_LOCATION = url;
        SENSORSERVICE_EXCEPTION = e;
    }

    public SensorService() {
        super(__getWsdlLocation(), SENSORSERVICE_QNAME);
    }

    public SensorService(WebServiceFeature... features) {
        super(__getWsdlLocation(), SENSORSERVICE_QNAME, features);
    }

    public SensorService(URL wsdlLocation) {
        super(wsdlLocation, SENSORSERVICE_QNAME);
    }

    public SensorService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, SENSORSERVICE_QNAME, features);
    }

    public SensorService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SensorService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns WebSensor
     */
    @WebEndpoint(name = "WebSensorPort")
    public WebSensor getWebSensorPort() {
        return super.getPort(new QName("web_sensor", "WebSensorPort"), WebSensor.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns WebSensor
     */
    @WebEndpoint(name = "WebSensorPort")
    public WebSensor getWebSensorPort(WebServiceFeature... features) {
        return super.getPort(new QName("web_sensor", "WebSensorPort"), WebSensor.class, features);
    }

    private static URL __getWsdlLocation() {
        if (SENSORSERVICE_EXCEPTION!= null) {
            throw SENSORSERVICE_EXCEPTION;
        }
        return SENSORSERVICE_WSDL_LOCATION;
    }

}
