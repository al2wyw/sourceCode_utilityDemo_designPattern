@XmlSchema(
xmlns = {
    @XmlNs(namespaceURI = "http://www.mytest.com", prefix = ""),
    @XmlNs(namespaceURI = "http://www.another.com", prefix = "test")
},
namespace = "http://www.mytest.com",
elementFormDefault = javax.xml.bind.annotation.XmlNsForm.QUALIFIED,
attributeFormDefault = javax.xml.bind.annotation.XmlNsForm.UNQUALIFIED)
package step3;
import javax.xml.bind.annotation.XmlSchema;
import javax.xml.bind.annotation.XmlNs;
