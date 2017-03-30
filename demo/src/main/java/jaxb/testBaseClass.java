package jaxb;

import jaxb.supplement.ArrayOfSupplementAge;
import jaxb.supplement.PerPersonSupplement;
import jaxb.supplement.Supplement;
import jaxb.supplement.SupplementAge;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Created by johnny.ly on 2016/9/14.
 */
public class testBaseClass {
    public static void main(String[] args) throws  Exception {
        JAXBContext context = JAXBContext.newInstance(Supplement.class); //this class and sub/sup class will be exposed to jaxb
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);

        Supplement supplement = null;
        PerPersonSupplement perPersonSupplement = new PerPersonSupplement();
        perPersonSupplement.setSuppId(532452345);
        perPersonSupplement.setSuppName("test for use");
        //perPersonSupplement.setSupptType(1); //marshall default to 0
        ArrayOfSupplementAge age = new ArrayOfSupplementAge();
        SupplementAge supplementAge = new SupplementAge();
        supplementAge.setSuppFrom(1);
        supplementAge.setSuppTo(100);
        age.getSupplementAge().add(supplementAge);
        //perPersonSupplement.setSuppAgeGroup(age);
        //nillable=true will affect the marshall, required=true no validation
        //nillable=true will unmarshall to null, required=true no validation
        supplement = perPersonSupplement;
        marshaller.marshal(supplement, System.out);//actually it marshall PerPersonSupplement, the output is perPersonSupplement

        Unmarshaller unmarshaller = context.createUnmarshaller();
        StringWriter writer = new StringWriter();
        marshaller.marshal(supplement, writer);
        Supplement res = (Supplement)unmarshaller.unmarshal(new ByteArrayInputStream(writer.toString().getBytes()));
        System.out.println(res.getSuppId());
    }

}
