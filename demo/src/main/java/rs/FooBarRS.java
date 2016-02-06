package rs;


import demoObject.Person;
import demoObject.Student;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by johnny.ly on 2016/2/4.
 */
public interface FooBarRS {
    @GET
    @Path("person")
    @Produces(MediaType.APPLICATION_XML)
    Person showPerson();

    @GET
    @Path("student")
    @Produces(MediaType.APPLICATION_XML)
    Student showStudent();
}
