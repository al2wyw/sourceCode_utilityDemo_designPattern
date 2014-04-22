package myws;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class hello {
	@WebMethod
	public String name(String n){
		return "Hello "+n;
	}
}
