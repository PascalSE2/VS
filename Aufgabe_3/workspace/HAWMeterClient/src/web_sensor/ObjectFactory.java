
package web_sensor;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the web_sensor package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _DisplayNichtBekanntException_QNAME = new QName("web_sensor", "DisplayNichtBekanntException");
    private final static QName _DisplayWirdVerwendetException_QNAME = new QName("web_sensor", "DisplayWirdVerwendetException");
    private final static QName _KoordinatorWahlException_QNAME = new QName("web_sensor", "KoordinatorWahlException");
    private final static QName _URLVergebenException_QNAME = new QName("web_sensor", "URLVergebenException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: web_sensor
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DisplayNichtBekanntException }
     * 
     */
    public DisplayNichtBekanntException createDisplayNichtBekanntException() {
        return new DisplayNichtBekanntException();
    }

    /**
     * Create an instance of {@link DisplayWirdVerwendetException }
     * 
     */
    public DisplayWirdVerwendetException createDisplayWirdVerwendetException() {
        return new DisplayWirdVerwendetException();
    }

    /**
     * Create an instance of {@link KoordinatorWahlException }
     * 
     */
    public KoordinatorWahlException createKoordinatorWahlException() {
        return new KoordinatorWahlException();
    }

    /**
     * Create an instance of {@link URLVergebenException }
     * 
     */
    public URLVergebenException createURLVergebenException() {
        return new URLVergebenException();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DisplayNichtBekanntException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "web_sensor", name = "DisplayNichtBekanntException")
    public JAXBElement<DisplayNichtBekanntException> createDisplayNichtBekanntException(DisplayNichtBekanntException value) {
        return new JAXBElement<DisplayNichtBekanntException>(_DisplayNichtBekanntException_QNAME, DisplayNichtBekanntException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DisplayWirdVerwendetException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "web_sensor", name = "DisplayWirdVerwendetException")
    public JAXBElement<DisplayWirdVerwendetException> createDisplayWirdVerwendetException(DisplayWirdVerwendetException value) {
        return new JAXBElement<DisplayWirdVerwendetException>(_DisplayWirdVerwendetException_QNAME, DisplayWirdVerwendetException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link KoordinatorWahlException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "web_sensor", name = "KoordinatorWahlException")
    public JAXBElement<KoordinatorWahlException> createKoordinatorWahlException(KoordinatorWahlException value) {
        return new JAXBElement<KoordinatorWahlException>(_KoordinatorWahlException_QNAME, KoordinatorWahlException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link URLVergebenException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "web_sensor", name = "URLVergebenException")
    public JAXBElement<URLVergebenException> createURLVergebenException(URLVergebenException value) {
        return new JAXBElement<URLVergebenException>(_URLVergebenException_QNAME, URLVergebenException.class, null, value);
    }

}
