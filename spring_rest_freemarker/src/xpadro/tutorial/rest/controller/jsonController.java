package xpadro.tutorial.rest.controller;

import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import xpadro.tutorial.rest.model.jsonResponse;

@Controller
public class jsonController {
	
	@RequestMapping(value="/json/{type}", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody jsonResponse getResponse(@PathVariable("type") String type){
		jsonResponse response = new jsonResponse();
		response.setType(type);
		response.setSuccessMsg("get the json successfully");
		response.setBody("http://test.com/view/code");
		return response;
	}
	
	@InitBinder
	public void setHeaders(HttpServletResponse response){
		response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate");
        response.setDateHeader("Expires", 0);
	}
	
	 @InitBinder
	 public void initBinder(WebDataBinder binder)
	 {
	    	
	 }
}
