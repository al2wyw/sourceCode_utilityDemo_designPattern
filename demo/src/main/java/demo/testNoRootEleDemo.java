package demo;

import man.AddressType;
import man.ManType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import java.io.StringReader;

/**
 * Created by johnny.ly on 2016/4/28.
 */
public class testNoRootEleDemo {
    private static final String target="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<man>\n" +
            "\t<name>peter</name>\n" +
            "\t<sex>famle</sex>\n" +
            "\t<id>4455</id>\n" +
            "\t<address>\n" +
            "\t\t<street>test test a</street>\n" +
            "\t\t<block>170</block>\n" +
            "\t</address>\n" +
            "</man>";

    public static void main(String[] args) throws  Exception{

        //JAXBContext.newInstance() if class has rootxmlelement, can pass class only, otherwise pass class with objectfactory or package name
        JAXBContext context = JAXBContext.newInstance("man");//JAXBContext.newInstance(ManType.class); will make unmarshal error, beacause ManType is not jaxb element, it is a type
        ManType t = new ManType();
        t.setName("peter");
        t.setId(110);
        t.setSex("male");
        AddressType addressType = new AddressType();
        addressType.setBlock(17);
        addressType.setStreet("test a test gl");
        t.setAddress(addressType);
        context.createMarshaller().marshal(new JAXBElement<ManType>(new QName("man"),ManType.class,t),System.out);
        Object c = context.createUnmarshaller().unmarshal(new StringReader(target));
        System.out.print(c);
    }
}
/**
 * jaxbcontext newInstance no objectFactory with an @XmlElementDecl
 * marshaller marshall no @XmlRootElement
*/