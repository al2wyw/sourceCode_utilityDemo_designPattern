
/**
 * Copyright 2014 Expedia, Inc. All rights reserved.
 * EXPEDIA PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.expedia.e3.insurance;

import java.io.CharArrayWriter;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.xml.serialize.OutputFormat;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * Unit Test for Class CDataContentHandler
 * 
 * @author <a href="mailto:v-yangli@expedia.com">v-yangli</a>
 * 
 */
public class CDataContentHandlerTest
{
//    @Test
//    public void test
    public static void main(String[] args) throws SAXException, ParserConfigurationException, IOException, TransformerException
    {
        // TODO v-yangli Auto-generated method stub
        CharArrayWriter writer = new CharArrayWriter(1000);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document dom = db.newDocument();
        
        Element books = dom.createElement("books");
        Element book = dom.createElement("book");
        book.setTextContent("test");
        books.appendChild(book);
        dom.appendChild(books);
        
//        TransformerFactory tf = TransformerFactory.newInstance();
//        Transformer tfer = tf.newTransformer();
//        tfer.setOutputProperty(OutputKeys.INDENT, "yes");
//        tfer.transform(new DOMSource(dom), new StreamResult(writer));
        
        CDataContentHandler handler = new CDataContentHandler(new OutputFormat());
//        char[] test = new char[]{'<','t','e','s','t','>'};
//        handler.characters(test, 0, test.length);
        handler.setOutputCharStream(writer);
        handler.startDocument();
        handler.startElement("books", null);
        handler.startElement("book", null);

        handler.endElement("book");
        handler.endElement("books");
        handler.endDocument();
        System.out.println("test");
        System.out.println(writer.toString());
    }
}

