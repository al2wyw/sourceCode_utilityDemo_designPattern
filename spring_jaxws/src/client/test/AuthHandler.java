package client.test;
import java.util.Set;

import javax.xml.namespace.QName;

import javax.xml.soap.*;

import javax.xml.ws.handler.MessageContext;

import javax.xml.ws.handler.soap.SOAPHandler;

import javax.xml.ws.handler.soap.SOAPMessageContext;

public class AuthHandler implements SOAPHandler<SOAPMessageContext> {

         public Set<QName> getHeaders() {

                   // TODO Auto-generated method stub

                   return null;

         }

         public void close(MessageContext arg0) {

                   // TODO Auto-generated method stub

         }

         public boolean handleFault(SOAPMessageContext arg0) {

                   // TODO Auto-generated method stub

                   return false;

         }

         public boolean handleMessage(SOAPMessageContext ctx) {

                   Boolean request_p = (Boolean) ctx

                                     .get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

 

                   if (request_p) {

                            try {

                                     SOAPMessage msg = ctx.getMessage();

                                     SOAPEnvelope env = msg.getSOAPPart().getEnvelope();

                                     SOAPHeader hdr = env.getHeader();

 

                                     if (hdr == null)

                                               hdr = env.addHeader();


                                     QName qname_user = new QName("http://www.myws.com/",

                                                        "myService");
                                     

                                     SOAPHeaderElement helem_user = hdr.addHeaderElement(qname_user);

                                     helem_user.setActor(SOAPConstants.URI_SOAP_1_2_ROLE_NEXT);

                                     helem_user.addTextNode("admin&admin");
                                     
                                     QName qname_extra = new QName("http://www.myws.com/",

                                             "ExtraService");
                                     
                                     SOAPHeaderElement helem_extra = hdr.addHeaderElement(qname_extra);
                                     
                                     helem_extra.setActor(SOAPConstants.URI_SOAP_1_2_ROLE_NEXT);
                                     
                                     helem_extra.setMustUnderstand(true);

                                     helem_extra.addTextNode("test");

                                     msg.saveChanges();

                                     //msg.writeTo(System.out);

                                     return true;

                            } catch (Exception e) {

                                     e.printStackTrace();

                            }

                   }

                   return false;

         }

}
