package rs;

import demoObject.Person;
import demoObject.Student;
import org.apache.cxf.jaxrs.client.Client;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.provider.JAXBElementProvider;

import javax.ws.rs.core.MediaType;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by johnny.ly on 2016/2/4.
 */
public class testCxfClient {
    public static void main(String args[]) throws  Exception{
        FooBarRS rs = JAXRSClientFactory.create("http://localhost:8080/rs/foobar", FooBarRS.class);
        //setup the proxy
        Client c = WebClient.client(rs);
        c.accept(MediaType.APPLICATION_XML);

        Person p = rs.showPerson();
        System.out.println(p.getName());

        Person pp =  WebClient.create("http://localhost:8080/rs/foobar/person")
                .accept(MediaType.APPLICATION_XML)
                .get(Person.class);
        System.out.println(pp.getName());


        List<Object> providers = new ArrayList<Object>();
        JAXBElementProvider<Student> provider = new JAXBElementProvider<Student>();

        URL url = testCxfClient.class.getResource("../");
        String path = url.getPath()+"demoObject/student.xsd";
        System.out.println(path);
        File fin = new File(path);
        System.out.println(fin.exists());
        provider.setMarshallerProperties(new HashMap<String, Object>());
        provider.setSchemaLocation(path);
        providers.add(provider);
        WebClient wc = WebClient.create("http://localhost:8080/rs/foobar/student",providers);
        Student s = wc.accept(MediaType.APPLICATION_XML)
                .get(Student.class);
        System.out.println(s.getName()+" "+s.getSex()+" "+s.getPhone()+ " "+s.getAge());
    }
}
