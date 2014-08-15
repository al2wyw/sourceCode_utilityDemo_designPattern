package test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.xml.serialize.OutputFormat;

import step2.JAXB2Tester;
import step2.Property;
import step2.Resouce;
import step3.*;

//do not use ContentHandler, it is kind of complicated and the class are all deprecated
public class JAXBContentHandlerMain {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		Property property = new Property();
		property.setElementName("elementName<test>");
		property.setEntityField("entityField");
		property.setSequence("sequence");
		property.setStyleId(11111);

		Property property1 = new Property();
		property1.setElementName("elementName111");
		property1.setEntityField("entityField111");
		property1.setSequence("sequence1111");
		property1.setStyleId(22222);

		List<Property> list = new ArrayList<Property>();
		list.add(property);
		list.add(property1);

		Resouce resouce = new Resouce();
		resouce.setPicLarge("picLarge");
		resouce.setProperties(list);

		JAXBContext context;
		FileWriter writer;
		
		try {
			context = JAXBContext.newInstance(resouce.getClass());
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.setProperty(Marshaller.JAXB_ENCODING, "UTF8");
			writer = new FileWriter(new File("test.xml"));
			OutputFormat of = new OutputFormat();
			of.setEncoding("UTF-8");
			of.setPreserveSpace(true);
			of.setIndent(10);
	        of.setIndenting(true);
	        of.setLineSeparator("\n");
	        CDATAContentHandler handler = new CDATAContentHandler(of);
	        handler.setOutputCharStream(writer);
	        handler.setOutputFormat(of);
			m.marshal(resouce, handler.asContentHandler());
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		Resouce o = (Resouce)JAXB2Tester.xml2Bean(Resouce.class, "test.xml");
		for(Property p:o.getProperties()){
			System.out.println(p.getElementName());
			System.out.println(p.getEntityField());
		}
	}

}
