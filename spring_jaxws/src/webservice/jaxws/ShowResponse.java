
package webservice.jaxws;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "showResponse", namespace = "http://myws.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "showResponse", namespace = "http://myws.com/")
public class ShowResponse {

    @XmlElement(name = "people", namespace = "")
    private List<webservice.Person> people;

    /**
     * 
     * @return
     *     returns List<Person>
     */
    public List<webservice.Person> getPeople() {
        return this.people;
    }

    /**
     * 
     * @param people
     *     the value for the people property
     */
    public void setPeople(List<webservice.Person> people) {
        this.people = people;
    }

}
