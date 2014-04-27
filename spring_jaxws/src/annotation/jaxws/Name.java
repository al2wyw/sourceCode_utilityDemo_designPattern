
package annotation.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "name", namespace = "http://www.myws.com")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "name", namespace = "http://www.myws.com")
public class Name {

    @XmlElement(name = "customer", namespace = "")
    private String customer;

    /**
     * 
     * @return
     *     returns String
     */
    public String getCustomer() {
        return this.customer;
    }

    /**
     * 
     * @param customer
     *     the value for the customer property
     */
    public void setCustomer(String customer) {
        this.customer = customer;
    }

}
