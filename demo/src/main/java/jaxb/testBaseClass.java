package jaxb;

import jaxb.supplement.ArrayOfSupplementAge;
import jaxb.supplement.PerPersonSupplement;
import jaxb.supplement.Supplement;
import jaxb.supplement.SupplementAge;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

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
        ArrayOfSupplementAge age = new ArrayOfSupplementAge();
        SupplementAge supplementAge = new SupplementAge();
        supplementAge.setSuppFrom(1);
        supplementAge.setSuppTo(100);
        age.getSupplementAge().add(supplementAge);
        perPersonSupplement.setSuppAgeGroup(age);
        supplement = perPersonSupplement;
        marshaller.marshal(supplement, System.out);//actually it marshall PerPersonSupplement, the output is perPersonSupplement

    }

}
