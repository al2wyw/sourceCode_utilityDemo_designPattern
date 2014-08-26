package test;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.transform.Result;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.xml.sax.SAXException;
public class PersonValidation {

	public static void main(String[] args) throws FileNotFoundException, XMLStreamException, SAXException {
		XMLInputFactory xf=XMLInputFactory.newInstance();
		XMLStreamReader sr = xf.createXMLStreamReader(new FileInputStream(new File("persons.xml")));
		if(sr != null){
			SchemaFactory sf = SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Validator va = sf.newSchema(new File("person.xsd")).newValidator();
			va.validate(sr.);
		}
	}

}
