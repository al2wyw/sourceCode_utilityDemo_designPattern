package jaxb;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.ArrayList;

/**
 * Created by johnny.ly on 2016/7/28.
 */
public class testQName {

    private static final String TEST =  "<?xml version=\"1.0\" encoding=\"UTF-8\"?>  \n" +
            "    <!--命名空间示例-->  \n" +
            "    <books>   \n" +
            "       <!--使用前缀命名空间-->  \n" +
            "       <jd:book xmlns:jd=\"http://www.jd.com\">   \n" +
            "          <jd:title>Harry Potter</jd:title>   \n" +
            "          <jd:author>J K. Rowling</jd:author>   \n" +
            "       </jd:book>   \n" +
            "       <!--使用默认命名空间-->  \n" +
            "       <book xmlns=\"http://www.dangdang.com\">   \n" +
            "          <title>Learning XML</title>   \n" +
            "          <author>Erik T. Ray</author>   \n" +
            "       </book>   \n" +
            "     </books>   ";

    public static void main(String[] args) throws Exception{
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);//performe Namespace processing
        SAXParser parser = factory.newSAXParser();
        //step2: create a handler
        DefaultHandler handler = new DefaultHandler(){

            @Override
            public void startElement (String uri, String localName,
                                      String qName, Attributes attributes) throws SAXException
            {
                System.out.format("uri: %s localName: %s qname: %s\n",uri,localName,qName);
                if(localName.equals("title")) {
                    title = true;
                }
            }
            @Override
            public void endElement(String namespaceURI, String localName, String qName)
                    throws SAXException {
                // End of processing current element
                if (title) {
                    title = false;
                }
            }
            @Override
            public void characters(char[] ch, int start, int length) {
                // Processing character data inside an element
                if (title) {
                    String bookTitle = new String(ch, start, length);
                    bookList.add(bookTitle);
                }
            }
            @Override
            public void endDocument ()throws SAXException {
                System.out.println("we have books: "+bookList);
            }
            ArrayList<String> bookList = new ArrayList<String>();
            private boolean title =false;
        };

        parser.parse(new ByteInputStream(TEST.getBytes("UTF-8"),TEST.getBytes("UTF-8").length),handler);
    }
}

//org.w3c.dom -> Node -> Document Element Attr Text
//org.xml.sax -> XMLReader, ContentHandler, DTDHandler, SAXException, SAXParseException

//javax.xml.parsers -> DocumentBuilder, SAXParser (neither can write, use transform.Source to write(transform))
//javax.xml.stream -> XMLStreamReader, XMLStreamWriter (can read can write, stream-style api)