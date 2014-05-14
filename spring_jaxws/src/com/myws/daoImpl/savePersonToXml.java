package com.myws.daoImpl;
import java.io.File;
import java.io.FileWriter;

import com.myws.dao.savePerson;
import com.myws.model.Person;

import javax.xml.stream.*;
public class savePersonToXml implements savePerson{
	
	@Override
	public void save(Person p) {
		// TODO Auto-generated method stub
		XMLOutputFactory factory = XMLOutputFactory.newInstance();
		String file=getClass().getResource("/").getFile();
		File xml=new File(file+"/Person.xml");
		if(!xml.exists()){
			try{
				xml.createNewFile();
			}catch(Exception e){
				log.error(e.getMessage(),e);
			}
		}
		XMLStreamWriter writer = null;
		try{
			writer=factory.createXMLStreamWriter(new FileWriter(xml));
			String NS="http://www.myws.com/";
			log.info("create xml stream writer");
			writer.writeStartDocument();
			writer.writeCharacters("\n\t");
			writer.setDefaultNamespace(NS);
//			writer.writeStartElement("Person");
			writer.writeStartElement("per", "Person", NS);
			writer.writeDefaultNamespace(NS);
			writer.writeNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
			writer.writeAttribute("xsi", 
					"http://www.w3.org/2001/XMLSchema-instance",
					"schemaLocation", 
					NS+" test.xsd");
			writer.setDefaultNamespace(NS);
			writer.writeCharacters("\n\t\t");
			writer.writeStartElement("name");
			writer.writeCharacters("  test");
			writer.writeEndElement();
			writer.writeCharacters("\n\t\t");
			writer.writeStartElement("id");
			writer.writeCharacters(p.getId());
			writer.writeEndElement();
			writer.writeCharacters("\n\t\t");
			writer.writeStartElement("salary");
			writer.writeCharacters(String.valueOf(p.getSalary()));
			writer.writeEndElement();
			writer.writeCharacters("\n\t");
			writer.writeEndElement();
			writer.writeCharacters("\n");
			writer.writeEndDocument();
			writer.flush();
			log.info("save person to xml");
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}finally{
			if(writer!=null){
				try {
					writer.close();
				} catch (XMLStreamException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
