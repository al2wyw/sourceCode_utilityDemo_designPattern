package webservice;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebResult;

import org.apache.log4j.Logger;

import java.util.*;
import java.io.*;

import javax.xml.XMLConstants;
import javax.xml.stream.*;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;
@WebService(name="showPeople",targetNamespace="http://myws.com/")
public class ShowPeopleService {
	private static Logger log=Logger.getLogger(ShowPeopleService.class);
	@WebMethod
	@WebResult(name="people")
	public List<Person> show(){
		validate();
		try{
			List<Person> list=new ArrayList<Person>(10);
			File f=new File(this.getClass().getResource("/People.xml").getFile());
			XMLInputFactory factory=XMLInputFactory.newFactory();
			XMLStreamReader reader=factory.createXMLStreamReader(new FileReader(f));
			log.info("create pointer xml reader");
			while(reader.hasNext()){
				int i=reader.next();
				log.info(translate(i));
				if(i==XMLStreamConstants.START_ELEMENT){
					String ele= reader.getLocalName();
					if(ele.equals("Person")){
						Person p=new Person();
						log.info("create a new person");
						while(reader.hasNext()){
							int j=reader.next();
							log.info(translate(j));
							if(j==XMLStreamConstants.START_ELEMENT){
								String test=reader.getLocalName();
								if(test.equals("name")){
									p.setName(reader.getElementText());
								}else if(test.equals("id")){
									p.setId(reader.getElementText());
								}else if(test.equals("salary")){
									p.setSalary(Double.parseDouble(reader.getElementText()));
									break;
								}
							}
						}
						list.add(p);
						log.info("add the new person to list");
					}
				}
			}
			reader.close();
			return list;
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
		return null;
	}
	
	 public void validate() {
		  try {
		   String strLang = XMLConstants.W3C_XML_SCHEMA_NS_URI;
		   SchemaFactory factory = SchemaFactory.newInstance(strLang);

		   InputStream isSchema = getClass().getResourceAsStream("/People.xsd");
		   StreamSource ss = new StreamSource(isSchema);
		   Schema schema = factory.newSchema(ss);

		   Validator validator = schema.newValidator();
		   
		   InputStream isXML = getClass().getResourceAsStream("/People.xml");
		   
		   StreamSource source = new StreamSource(isXML);
		   validator.validate(source);
		   log.info("Result : Valid!");

		  } catch (Exception e) {
			  log.error(e.getMessage(),e);
			  log.error("Result : Invalid!");
		  }
	
	 }
	 public String translate(int i){
		 String t = "test";
		 switch(i){
		 case XMLStreamConstants.ATTRIBUTE  : t = "XMLStreamConstants.ATTRIBUTE";
			 break;
		 case XMLStreamConstants.CDATA  : t = "XMLStreamConstants.CDATA";
			 break;
		 case XMLStreamConstants.CHARACTERS  : t = "XMLStreamConstants.CHARACTERS";
			 break;
		 case XMLStreamConstants.COMMENT  : t = "XMLStreamConstants.COMMENT";
			 break;
		 case XMLStreamConstants.DTD  : t = "XMLStreamConstants.DTD";
			 break;
		 case XMLStreamConstants.END_DOCUMENT  : t = "XMLStreamConstants.END_DOCUMENT";
			 break;
		 case XMLStreamConstants.END_ELEMENT  : t = "XMLStreamConstants.END_ELEMENT";
			 break;
		 case XMLStreamConstants.ENTITY_DECLARATION  : t = "XMLStreamConstants.ENTITY_DECLARATION";
			 break;
		 case XMLStreamConstants.ENTITY_REFERENCE  : t = "XMLStreamConstants.ENTITY_REFERENCE";
			 break;
		 case XMLStreamConstants.NAMESPACE  : t = "XMLStreamConstants.NAMESPACE";
			 break;
		 case XMLStreamConstants.NOTATION_DECLARATION  : t = "XMLStreamConstants.NOTATION_DECLARATION";
			 break;
		 case XMLStreamConstants.PROCESSING_INSTRUCTION  : t = "XMLStreamConstants.PROCESSING_INSTRUCTION";
			 break;
		 case XMLStreamConstants.SPACE  : t = "XMLStreamConstants.SPACE";
			 break;
		 case XMLStreamConstants.START_DOCUMENT : t = "XMLStreamConstants.START_DOCUMENT";
			 break;
		 case XMLStreamConstants.START_ELEMENT : t = "XMLStreamConstants.START_ELEMENT";
			 break;
		 }
		 return t;
	 }
}
