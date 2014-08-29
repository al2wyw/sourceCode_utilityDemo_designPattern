package test;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import step3.Guitar;
import step3.Guitars;

public class unmarshallNode {

	public static void main(String[] args) throws JAXBException, ParserConfigurationException, SAXException, IOException {
		// TODO Auto-generated method stub
		DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
		df.setNamespaceAware(true);
		DocumentBuilder db = df.newDocumentBuilder();
		Document dom = db.parse(new File("test.xml"));

		JAXBContext context = JAXBContext.newInstance(Guitars.class);
		Unmarshaller um = context.createUnmarshaller();
		Schema schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(new File("guitars.xsd"));//"http://www.w3.org/2001/XMLSchema"
        um.setSchema(schema);
        
		NodeList nodeList = dom.getElementsByTagName("guitar");
		
		Element node = (Element) nodeList.item(1);
		
		Guitar g = (Guitar)um.unmarshal(node);
		
		System.out.println(g.getId());
	}

}
