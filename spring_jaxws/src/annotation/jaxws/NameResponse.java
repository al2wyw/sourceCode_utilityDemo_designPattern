
package annotation.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "nameResponse", namespace = "http://www.myws.com")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "nameResponse", namespace = "http://www.myws.com")
public class NameResponse {

    @XmlElement(name = "outcome", namespace = "")
    private String outcome;

    /**
     * 
     * @return
     *     returns String
     */
    public String getOutcome() {
        return this.outcome;
    }

    /**
     * 
     * @param outcome
     *     the value for the outcome property
     */
    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

}
