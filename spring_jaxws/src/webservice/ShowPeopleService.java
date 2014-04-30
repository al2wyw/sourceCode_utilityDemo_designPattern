package webservice;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebResult;

import org.apache.log4j.Logger;

import java.util.*;
import java.io.*;
import javax.xml.stream.*;
import javax.xml.stream.events.*;
@WebService(name="showPeople",targetNamespace="http://myws.com/")
public class ShowPeopleService {
	private static Logger log=Logger.getLogger(ShowPeopleService.class);
	@WebMethod
	@WebResult(name="people")
	public List<Person> show(){
		try{
			List<Person> list=new ArrayList<Person>(10);
			File f=new File(this.getClass().getResource("/People.xml").getFile());
			XMLInputFactory factory=XMLInputFactory.newFactory();
			XMLStreamReader reader=factory.createXMLStreamReader(new FileReader(f));
			log.info("create pointer xml reader");
			while(reader.hasNext()){
				int i=reader.next();
				if(i==XMLEvent.START_ELEMENT){
					javax.xml.namespace.QName n=reader.getName();
					String ele= n.getLocalPart();
					if(ele.equals("Person")){
						Person p=new Person();
						log.info("create a new person");
						while(reader.hasNext()){
							int j=reader.next();
							if(j==XMLEvent.START_ELEMENT){
								javax.xml.namespace.QName nn=reader.getName();
								String test=nn.getLocalPart();
								if(test.equals("name")){
									
									p.setName(reader.getElementText());
								}else if(test.equals("id")){
									p.setId(reader.getElementText());
								}else{
									p.setSalary(Double.parseDouble(reader.getElementText()));
								}
							}
						}
						list.add(p);
						log.info("add the new person to list");
					}
				}
			}
			return list;
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
		return null;
	}
}
