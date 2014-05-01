package webservice;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
@WebService
public class webservicecontext {
	private static Logger log=Logger.getLogger(webservicecontext.class);
	@Resource
	private WebServiceContext ctx;
	@WebMethod
	@WebResult
	public String clientIP(){
		javax.servlet.http.HttpServletRequest request = 
				(javax.servlet.http.HttpServletRequest)ctx.getMessageContext().get(MessageContext.SERVLET_REQUEST);
		log.info("get servlet request");
		String s=request.getRemoteAddr();
		return s;
	}
}
