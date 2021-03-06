
package jaxb.student;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the jaxb.student package. 
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

    private final static QName _Sex_QNAME = new QName("http://www.johnny.com/webservices/", "sex");
    private final static QName _Phone_QNAME = new QName("http://www.johnny.com/webservices/", "phone");
    private final static QName _Age_QNAME = new QName("http://www.johnny.com/webservices/", "age");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: jaxb.student
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Student }
     * 
     */
    public Student createStudent() {
        return new Student();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.johnny.com/webservices/", name = "sex")
    public JAXBElement<String> createSex(String value) {
        return new JAXBElement<String>(_Sex_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.johnny.com/webservices/", name = "phone")
    public JAXBElement<String> createPhone(String value) {
        return new JAXBElement<String>(_Phone_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.johnny.com/webservices/", name = "age")
    public JAXBElement<Integer> createAge(Integer value) {
        return new JAXBElement<Integer>(_Age_QNAME, Integer.class, null, value);
    }

}
