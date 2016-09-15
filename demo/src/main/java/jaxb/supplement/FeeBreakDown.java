
package jaxb.supplement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Supplements" type="{http://www.trip.com/webservices/}ArrayOfSupplement"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "supplements"
})
@XmlRootElement(name = "FeeBreakDown", namespace = "http://www.trip.com/webservices/")
public class FeeBreakDown {

    @XmlElement(name = "Supplements", namespace = "http://www.trip.com/webservices/", required = true)
    protected ArrayOfSupplement supplements;

    /**
     * Gets the value of the supplements property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSupplement }
     *     
     */
    public ArrayOfSupplement getSupplements() {
        return supplements;
    }

    /**
     * Sets the value of the supplements property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSupplement }
     *     
     */
    public void setSupplements(ArrayOfSupplement value) {
        this.supplements = value;
    }

}
