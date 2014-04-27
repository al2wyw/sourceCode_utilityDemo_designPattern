package client.test;
import client.com.myws.*;

import java.lang.reflect.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.Response;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;
import javax.xml.ws.handler.soap.*;

import client.test.AuthHandler;
public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			URL url=new URL("http://192.168.100.104:8080/spring_jaxws/test?wsdl");
			TestService ser=new TestService(url);
			ser.setHandlerResolver(new HandlerResolver() {

                public List<Handler> getHandlerChain(PortInfo arg0) {

                         List<Handler> handlerList = new ArrayList<Handler>();

                         handlerList.add(new AuthHandler());

                         return handlerList;

                }

       });
			Class<?> klass = AnnotationTest.class;
			Method m=klass.getMethod("nameAsync", String.class);
			Object res=null;
			if(m!=null)
				res=m.invoke(ser.getPort(AnnotationTest.class ), "Peter");
			Response<NameResponse> test=(Response<NameResponse>)res;
//			AnnotationTest port=ser.getAnnotationTestPort();
//
//			Response<NameResponse> test=port.nameAsync("test");
			while(!test.isDone()){
				System.out.println("not yet");
			}
			String out=test.get().getOutcome();
			System.out.println(out);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
