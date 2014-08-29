package step2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.util.ValidationEventCollector;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import com.sun.xml.bind.marshaller.CharacterEscapeHandler;

//by default, marshaller will change <&> to &gt; &lt; &amp;
//by default, unmarshaller will change &gt; &lt; &amp; to <&>
//by default, unmarshaller will remove <![CDATA[]]>, but will not reomve &lt;![CDATA[]]&gt;
//commonly, marshaller need to set up property while unmarshaller does not need
public class JAXB2Tester {

	/**
	 * 将生成的xml转换为对象
	 * 
	 * @param zClass
	 *            转换为实例的对象类类型
	 * @param xmlPath
	 *            需要转换的xml路径
	 */
	public static Object xml2Bean(Class<?> zClass, String xmlFile, String s) {
		Object obj = null;
		JAXBContext context = null;
		if (null == xmlFile || "".equals(xmlFile))
			return obj;
		try {
			context = JAXBContext.newInstance(zClass);
			// if the file is encoded in utf8? will this still work fine?
			InputStream iStream = new FileInputStream(xmlFile);
			Unmarshaller um = context.createUnmarshaller();
			ValidationEventCollector vec = new  ValidationEventCollector(); 
			um.setEventHandler(vec);
			if(s != null){
			Schema schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(new File(s));//"http://www.w3.org/2001/XMLSchema"
	        um.setSchema(schema);
			}
			obj = (Object) um.unmarshal(iStream);
			return obj;
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch(SAXException e){
			e.printStackTrace();
		}
		return obj;
	}

	public static String bean2Xml(Object bean,String s) {
		String xmlString = null;
		JAXBContext context;
		FileWriter writer;
		if (null == bean)
			return xmlString;
		try {
			context = JAXBContext.newInstance(bean.getClass());
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            if(s!=null)
            m.setProperty(Marshaller.JAXB_SCHEMA_LOCATION,s);
            m.setProperty(CharacterEscapeHandler.class.getName(), new CharacterEscapeHandler(){
            	
				@Override
				public void escape(char[] arg0, int arg1, int arg2,
						boolean arg3, Writer arg4) throws IOException {
					//do not escape < > &
					arg4.write(arg0,arg1,arg2);
				}
            	
            });
            String file = s.split(" ")[1];
            Schema schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(new File(file));//"http://www.w3.org/2001/XMLSchema"
            m.setSchema(schema);
//            m.setEventHandler(new ValidationEventHandler(){
//
//				@Override
//				public boolean handleEvent(ValidationEvent arg0) {
//					
//					return true;//true will keep going
//				}
//            		
//            });
			writer = new FileWriter(new File("test.xml"));
			m.marshal(bean, writer);
			xmlString = writer.toString();
			System.out.println(xmlString);
			return xmlString;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xmlString;
	}

}