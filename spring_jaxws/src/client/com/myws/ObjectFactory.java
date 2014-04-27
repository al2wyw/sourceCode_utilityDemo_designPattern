
package client.com.myws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the client.com.myws package. 
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

    private final static QName _Name_QNAME = new QName("http://www.myws.com", "name");
    private final static QName _NameResponse_QNAME = new QName("http://www.myws.com", "nameResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: client.com.myws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link NameResponse }
     * 
     */
    public NameResponse createNameResponse() {
        return new NameResponse();
    }

    /**
     * Create an instance of {@link Name }
     * 
     */
    public Name createName() {
        return new Name();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Name }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.myws.com", name = "name")
    public JAXBElement<Name> createName(Name value) {
        return new JAXBElement<Name>(_Name_QNAME, Name.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NameResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.myws.com", name = "nameResponse")
    public JAXBElement<NameResponse> createNameResponse(NameResponse value) {
        return new JAXBElement<NameResponse>(_NameResponse_QNAME, NameResponse.class, null, value);
    }

}
