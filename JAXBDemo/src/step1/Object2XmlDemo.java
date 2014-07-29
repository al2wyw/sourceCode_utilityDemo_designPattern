package step1;

import java.io.File;  
import java.util.HashSet;  
import java.util.Set;  
  

import javax.xml.bind.JAXBContext;  
import javax.xml.bind.JAXBException;  
import javax.xml.bind.Marshaller;  
import javax.xml.bind.annotation.XmlElement;
  
//Marshaller  
public class Object2XmlDemo {  
    public static void main(String[] args) {  
  
        Customer customer = new Customer();  
        customer.setId(100);  
        customer.setName("suo");  
        customer.setAge(29);  
          
        Book book = new Book();  
        book.setId("1");  
        book.setName("Ê¥¾­");  
        book.setPrice(100);  
          
        Book book2 = new Book();  
        book2.setId("2");  
        book2.setName("¿íÈÝ");  
        book2.setPrice(50);  
          
        Set<Book> bookSet = new HashSet<Book>();  
        bookSet.add(book);  
        bookSet.add(book2);  
          
       
        Book mybook1=new Book();
        mybook1.setId("3");
        mybook1.setName("Ê·¼Ç");
        mybook1.setPrice(20);
        customer.setBook(bookSet);  
        customer.setMybook(mybook1);
          
        try {  
            File file = new File("file1.xml");  
            JAXBContext jaxbContext = JAXBContext.newInstance(Customer.class);  
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();  
            // output pretty printed  
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
            jaxbMarshaller.marshal(customer, file);  
            jaxbMarshaller.marshal(customer, System.out);  
        } catch (JAXBException e) {  
            e.printStackTrace();  
        }  
    }  
}  