package client.test;
import java.net.URL;

import client.webservice.*;

public class clientIPClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			URL url=new URL("http://192.168.100.104:8080/spring_jaxws/clientIP?wsdl");
			WebservicecontextService service=new WebservicecontextService(url);
			Webservicecontext port=service.getWebservicecontextPort();
			System.out.println("Client IP "+port.clientIP());

		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
