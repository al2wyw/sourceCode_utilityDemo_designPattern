
package jaxb.man;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the man package. 
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

    private final static QName _Man_QNAME = new QName("", "jaxb/man");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: man
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ManType }
     * 
     */
    public ManType createManType() {
        return new ManType();
    }

    /**
     * Create an instance of {@link AddressType }
     * 
     */
    public AddressType createAddressType() {
        return new AddressType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ManType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "jaxb/man")
    public JAXBElement<ManType> createMan(ManType value) {
        return new JAXBElement<ManType>(_Man_QNAME, ManType.class, null, value);
    }

}
