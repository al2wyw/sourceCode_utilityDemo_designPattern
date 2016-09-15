
package jaxb.supplement;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for PerPersonSupplement complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PerPersonSupplement">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.trip.com/webservices/}Supplement">
 *       &lt;sequence>
 *         &lt;element name="SuppAgeGroup" type="{http://www.trip.com/webservices/}ArrayOfSupplementAge" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PerPersonSupplement", namespace = "http://www.trip.com/webservices/", propOrder = {
    "suppAgeGroup"
})
@XmlRootElement
public class PerPersonSupplement
    extends Supplement
{

    @XmlElement(name = "SuppAgeGroup", namespace = "http://www.trip.com/webservices/")
    protected ArrayOfSupplementAge suppAgeGroup;

    /**
     * Gets the value of the suppAgeGroup property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSupplementAge }
     *     
     */
    public ArrayOfSupplementAge getSuppAgeGroup() {
        return suppAgeGroup;
    }

    /**
     * Sets the value of the suppAgeGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSupplementAge }
     *     
     */
    public void setSuppAgeGroup(ArrayOfSupplementAge value) {
        this.suppAgeGroup = value;
    }

}
