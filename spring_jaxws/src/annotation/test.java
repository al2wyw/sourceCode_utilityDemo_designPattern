package annotation;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.xml.ws.soap.*;
//still generate name.java and nameResponse.java
@WebService(name="annotation_test",targetNamespace ="http://www.myws.com")
@MTOM
public class test {
	@WebMethod
	@WebResult(name="outcome")
	public String name(@WebParam(name="customer")String n){
		return n;
	}
}
