package soapParse;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPMessage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by johnny.ly on 2016/6/23.
 */
public class soapMessageParse {

    private static final String BODY_START = "<soap:Body>";

    private static final String BODY_END = "</soap:Body>";

    public static void main(String[] args) throws  Exception {
        testSOAPMessage();
        //testDOMMessage(true);
        //testDOMMessage(false);

        int bodyStart = soap.indexOf(BODY_START);
        int bodyEnd = soap.indexOf(BODY_END);
        if(bodyStart!=-1&&bodyEnd!=-1){
            int start = bodyStart+BODY_START.length();
            int end = bodyEnd;
            System.out.println(soap.substring(start, end));
        }
    }

    private static void testDOMMessage(boolean flag){
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            factory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            String test;
            if(flag)
                test = soap;
            else
                test = soap2;
            Document document = builder.parse(new ByteArrayInputStream(test.getBytes()));
            document.normalizeDocument();
            Node body = document.getDocumentElement().getElementsByTagName("soap:Body").item(0);//竟然要加soap namespace prefix
            System.out.println(body.getFirstChild().getChildNodes().getLength());
            Node child;
            if(flag)
                child=body.getFirstChild().getFirstChild();
            else
                child=body.getFirstChild().getNextSibling().getFirstChild();
            do{
                //如果节点间有空格换行符等，有多余的<#text>节点,它们的node name为null
                System.out.println(child.getChildNodes().getLength());
                System.out.println("<"+child.getNodeName()+">"+ child.getTextContent()+"</"+child.getNodeName()+">");
                child = child.getNextSibling();
            }while (child!=null);
        }catch (ParserConfigurationException e){

        }catch (SAXException e){

        }catch (IOException e){

        }
        System.out.println("done");
    }

    private static void testSOAPMessage() throws Exception{
        MessageFactory msgFactory = MessageFactory.newInstance();
        SOAPMessage message = msgFactory.createMessage(new MimeHeaders(), new ByteArrayInputStream(soap.getBytes()));
        Iterator iterator = message.getSOAPBody().getChildElements();
        while(iterator.hasNext()){
            SOAPElement element = (SOAPElement)iterator.next();
            System.out.println(element.getNodeName() + " " + element.getPrefix());
        }
        System.out.println("done");
    }

    //soap 是没有<?xml version="1.0" encoding="utf-8"?>的
    private static final String soap="<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Header><LoginHeader xmlns=\"http://tourico.com/webservices/\"><username xmlns=\"http://tourico.com/travelservices/\">string</username><password xmlns=\"http://tourico.com/travelservices/\">string</password><culture xmlns=\"http://tourico.com/travelservices/\">None or en_US or zh_CN</culture><version xmlns=\"http://tourico.com/travelservices/\">string</version></LoginHeader></soap:Header><soap:Body><SearchHotels xmlns=\"http://tourico.com/webservices/\">"+"<sDestination>string</sDestination><sHotelCityName>string</sHotelCityName><sHotelLocationName>string</sHotelLocationName><sHotelName>string</sHotelName><dtCheckIn>date</dtCheckIn><dtCheckOut>date</dtCheckOut><roomsInformation><RoomInfo><AdultsNum>int</AdultsNum><ChildNum>int</ChildNum><ChildAges><ChildAge>int</ChildAge><ChildAge>int</ChildAge></ChildAges></RoomInfo><RoomInfo><AdultsNum>int</AdultsNum><ChildNum>int</ChildNum><ChildAges><ChildAge>int</ChildAge><ChildAge>int</ChildAge></ChildAges></RoomInfo></roomsInformation><maxPrice>decimal</maxPrice><starLevel>decimal</starLevel><fAvailableOnly>boolean</fAvailableOnly></SearchHotels></soap:Body></soap:Envelope>";

    private static final String soap2 =
            "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "  <soap:Header>\n" +
            "    <LoginHeader xmlns=\"http://tourico.com/webservices/\">\n" +
            "      <username xmlns=\"http://tourico.com/travelservices/\">Ali105</username>\n" +
            "      <password xmlns=\"http://tourico.com/travelservices/\">111111 </password>\n" +
            "      <culture xmlns=\"http://tourico.com/travelservices/\">en_US</culture>\n" +
            "      <version xmlns=\"http://tourico.com/travelservices/\">string</version>\n" +
            "    </LoginHeader>\n" +
            "  </soap:Header>\n" +
            "  <soap:Body>\n" +
            "    <SearchHotels xmlns=\"http://tourico.com/webservices/\">\n" +
            "      <sDestination>string</sDestination>\n" +
            "      <sHotelCityName>string</sHotelCityName>\n" +
            "      <sHotelLocationName>string</sHotelLocationName>\n" +
            "      <sHotelName>string</sHotelName>\n" +
            "      <dtCheckIn>date</dtCheckIn>\n" +
            "      <dtCheckOut>date</dtCheckOut>\n" +
            "      <roomsInformation>\n" +
            "        <RoomInfo>\n" +
            "          <AdultsNum>int</AdultsNum>\n" +
            "          <ChildNum>int</ChildNum>\n" +
            "          <ChildAges>\n" +
            "            <ChildAge>int</ChildAge>\n" +
            "            <ChildAge>int</ChildAge>\n" +
            "          </ChildAges>\n" +
            "        </RoomInfo>\n" +
            "        <RoomInfo>\n" +
            "          <AdultsNum>int</AdultsNum>\n" +
            "          <ChildNum>int</ChildNum>\n" +
            "          <ChildAges>\n" +
            "            <ChildAge>int</ChildAge>\n" +
            "            <ChildAge>int</ChildAge>\n" +
            "          </ChildAges>\n" +
            "        </RoomInfo>\n" +
            "      </roomsInformation>\n" +
            "    </SearchHotels>\n" +
            "  </soap:Body>\n" +
            "</soap:Envelope>";
}
