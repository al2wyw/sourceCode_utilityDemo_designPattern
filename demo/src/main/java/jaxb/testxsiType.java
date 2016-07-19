package jaxb;

import jaxb.customer.Address;
import jaxb.customer.Customer;
import jaxb.customer.ObjectFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

/**
 * Created by johnny.ly on 2016/7/18.
 */
public class testxsiType {
    public static void main(String[] args) throws  Exception{
        JAXBContext context = JAXBContext.newInstance("jaxb.customer");
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);

        ObjectFactory objectFactory = new ObjectFactory();
        Customer customer = new Customer();
        Address address = new Address();
        address.setStreet("tests block 12 st");
        address.setId("test123_sdf234");
        customer.setContactInfo(address);

        marshaller.marshal(objectFactory.createCustomer(customer),System.out);
    }
}
