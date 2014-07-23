package xpadro.tutorial.rest.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import xpadro.tutorial.rest.model.Product;
import xpadro.tutorial.rest.repository.WarehouseRepository;

@Controller
public class ProductAddFormController {
	
	@Autowired
	@Qualifier("warehouseRepository")
	private WarehouseRepository warehouseRepository;
	
	@RequestMapping(value="/Product", method=RequestMethod.GET)
	public String showForm(HttpServletResponse response,Model map){
		response.setHeader( "Pragma", "no-cache" );
	    response.addHeader( "Cache-Control", "must-revalidate" );
	    response.addHeader( "Cache-Control", "no-cache" );
	    response.addHeader( "Cache-Control", "no-store" );
	    response.setDateHeader("Expires", 0);
	    map.addAttribute("product", new Product());
	    map.addAttribute("option", "option for show the form");
		return "spring";
	}
	
	@RequestMapping(value="/Product", method=RequestMethod.POST)
	public String postForm(@Valid @ModelAttribute("product") Product product,BindingResult errors,Model map,HttpServletResponse response){
		response.setHeader( "Pragma", "no-cache" );
	    response.addHeader( "Cache-Control", "must-revalidate" );
	    response.addHeader( "Cache-Control", "no-cache" );
	    response.addHeader( "Cache-Control", "no-store" );
	    response.setDateHeader("Expires", 0);
		if(errors.hasErrors()){
			System.out.println("test");
			return "spring";
		}
		warehouseRepository.addProduct(1, product); //hardcode warehouse id
		return "redirect:addResult"; //must have a requestmapping resolver
	}
	
	@RequestMapping(value="/addResult", method=RequestMethod.GET)
	public String showResult(){
		return "addResult";
	}
	/*
	 * Set the locale
	 * LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);  
			if (localeResolver == null) {  
			    throw new IllegalStateException("No LocaleResolver found: not in a DispatcherServlet request?");  
			}  
		LocaleEditor localeEditor = new LocaleEditor();  
		localeEditor.setAsText(lang);  
		localeResolver.setLocale(request, response, (Locale) localeEditor.getValue());  
	 * 
	 */
}
