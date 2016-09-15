
package jaxb.supplement;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the jaxb.supplement package. 
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


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: jaxb.supplement
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link FeeBreakDown }
     * 
     */
    public FeeBreakDown createFeeBreakDown() {
        return new FeeBreakDown();
    }

    /**
     * Create an instance of {@link ArrayOfSupplement }
     * 
     */
    public ArrayOfSupplement createArrayOfSupplement() {
        return new ArrayOfSupplement();
    }

    /**
     * Create an instance of {@link PerRoomSupplement }
     * 
     */
    public PerRoomSupplement createPerRoomSupplement() {
        return new PerRoomSupplement();
    }

    /**
     * Create an instance of {@link SupplementAge }
     * 
     */
    public SupplementAge createSupplementAge() {
        return new SupplementAge();
    }

    /**
     * Create an instance of {@link Supplement }
     * 
     */
    public Supplement createSupplement() {
        return new Supplement();
    }

    /**
     * Create an instance of {@link ArrayOfSupplementAge }
     * 
     */
    public ArrayOfSupplementAge createArrayOfSupplementAge() {
        return new ArrayOfSupplementAge();
    }

    /**
     * Create an instance of {@link PerPersonSupplement }
     * 
     */
    public PerPersonSupplement createPerPersonSupplement() {
        return new PerPersonSupplement();
    }

}
