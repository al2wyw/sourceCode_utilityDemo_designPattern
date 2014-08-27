package test;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.transform.stream.StreamSource;



import org.xml.sax.SAXException;
public class PersonValidation {

	public static void main(String[] args) throws SAXException, IOException {
		StreamSource ss = new StreamSource(new FileInputStream(new File("persons.xml")));
		if(ss != null){
			SchemaFactory sf = SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Validator va = sf.newSchema(new File("address.xsd")).newValidator();//use import to solve multiple xsd files for one xml file
			va.validate(ss);
			System.out.println("test");
		}
	}

}
