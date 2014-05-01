
package client.webservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the client.webservice package. 
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

    private final static QName _ClientIPResponse_QNAME = new QName("http://webservice/", "clientIPResponse");
    private final static QName _ClientIP_QNAME = new QName("http://webservice/", "clientIP");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: client.webservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ClientIPResponse }
     * 
     */
    public ClientIPResponse createClientIPResponse() {
        return new ClientIPResponse();
    }

    /**
     * Create an instance of {@link ClientIP }
     * 
     */
    public ClientIP createClientIP() {
        return new ClientIP();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ClientIPResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "clientIPResponse")
    public JAXBElement<ClientIPResponse> createClientIPResponse(ClientIPResponse value) {
        return new JAXBElement<ClientIPResponse>(_ClientIPResponse_QNAME, ClientIPResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ClientIP }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "clientIP")
    public JAXBElement<ClientIP> createClientIP(ClientIP value) {
        return new JAXBElement<ClientIP>(_ClientIP_QNAME, ClientIP.class, null, value);
    }

}
