package client.test;
import client.com.myws.*;

import java.net.URL;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			URL url=new URL("http://192.168.100.104:8080/TestServce?wsdl");
			TestService ser=new TestService(url);
			AnnotationTest port=ser.getAnnotationTestPort();
			port.name("test");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
