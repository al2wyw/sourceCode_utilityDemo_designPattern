package jaxb;

import jaxb.classroom.AddressType;
import jaxb.classroom.Classroom;
import jaxb.student.Student;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.SchemaFactory;
import java.io.File;

/**
 * Created by johnny.ly on 2016/6/23.
 */
public class testNameSpaceDemo {
    public static void main(String[] args) throws  Exception{
        testStudent();
        testClassRoom();

    }

    private static void testStudent() throws Exception{
        JAXBContext context = JAXBContext.newInstance("jaxb.student");
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
        marshaller.setSchema(SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(testNameSpaceDemo.class.getClassLoader().getResource("/jaxb/student/student.xsd")));
        Student student = new Student();
        student.setAge(10);
        student.setName("peter");
        student.setPhone("23432432432");
        student.setSex("男");
        marshaller.marshal(student, System.out);
    }

    private static void testClassRoom() throws Exception{
        JAXBContext context = JAXBContext.newInstance("jaxb.classroom");
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT,true);
        Classroom classroom = new Classroom();
        jaxb.classroom.Student student = new jaxb.classroom.Student();
        student.setAge(10);
        student.setName("peter");
        student.setPhone("23432432432");
        student.setSex("男");
        classroom.setStudent(student);
        jaxb.classroom.AddressType address = new AddressType();
        address.setBlock(3);
        address.setStreet("test for new block");
        classroom.setAddress(address);
        marshaller.marshal(classroom,System.out);
    }
}
