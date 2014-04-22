package com.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class HelloWorldWS {

	@WebMethod()
	public String getHelloWorld() {		 
		return "Hello world"; 
	}
	
}
