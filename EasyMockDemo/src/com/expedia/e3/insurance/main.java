package com.expedia.e3.insurance;

import org.junit.Test;
import org.junit.Assert;

import java.io.CharArrayWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.xml.serialize.OutputFormat;
import org.example.person.*;

public class main {
	
	@Test
	public void test() {
		String expected = "<persons>\n    <person>\n        <firstname><![CDATA[<test]]></firstname>\n        <lastname>Smith</lastname>\n    </person>\n    <person/>\n</persons>\n";
		Persons persons= new Persons();
		List<PersonType> personList = persons.getPerson();
		PersonType p1 = new PersonType();
		p1.setFirstname("<test");
		p1.setLastname("Smith");
		PersonType p2 = new PersonType();
		personList.add(p1);
		personList.add(p2);
		CharArrayWriter writer = new CharArrayWriter(1000);
		String result = bean2Xml(persons,writer);
		Assert.assertNotNull(result);
		Assert.assertEquals(expected, result);
	}

	public String bean2Xml(Object bean,CharArrayWriter writer) {
		String xmlString = null;
		if (null == bean)
			return xmlString;
		JAXBContext context;
		try {
			context = JAXBContext.newInstance(bean.getClass());
			Marshaller m = context.createMarshaller();
			OutputFormat of = new OutputFormat();
			of.setOmitXMLDeclaration(true);
			of.setIndenting(true);
            CDataContentHandler handler = new CDataContentHandler(of);
            handler.setOutputCharStream(writer);
            handler.setNamespaces(true);
            handler.setPrintNamespacePrefixes(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		m.marshal(bean, handler.asContentHandler());
		xmlString = writer.toString();
		return xmlString;
	}
}
