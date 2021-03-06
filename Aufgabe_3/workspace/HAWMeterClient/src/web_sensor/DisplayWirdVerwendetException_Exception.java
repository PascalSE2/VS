
package web_sensor;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "DisplayWirdVerwendetException", targetNamespace = "web_sensor")
public class DisplayWirdVerwendetException_Exception
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private DisplayWirdVerwendetException faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public DisplayWirdVerwendetException_Exception(String message, DisplayWirdVerwendetException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param faultInfo
     * @param cause
     * @param message
     */
    public DisplayWirdVerwendetException_Exception(String message, DisplayWirdVerwendetException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: web_sensor.DisplayWirdVerwendetException
     */
    public DisplayWirdVerwendetException getFaultInfo() {
        return faultInfo;
    }

}
