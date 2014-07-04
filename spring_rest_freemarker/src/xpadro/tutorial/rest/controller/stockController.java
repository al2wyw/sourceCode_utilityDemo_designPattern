package xpadro.tutorial.rest.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import xpadro.tutorial.rest.exception.MyException;
import xpadro.tutorial.rest.exception.ProductNotFoundException;
import xpadro.tutorial.rest.model.Product;
import xpadro.tutorial.rest.model.Warehouse;
import xpadro.tutorial.rest.repository.WarehouseRepository;

//test whether exceptionhandler will handle the exception from the other controller, no
@Controller
@SessionAttributes("productSes")
public class stockController {
	private static Logger logger = Logger.getLogger("main");

	@Autowired
	private WarehouseRepository warehouseRepository;
	
	//if @SessionAttributes present, and productSes is present in the session, this method will not be called
	@ModelAttribute(value="productSes") //belongs to model not modelandview
	public Product initProduct(){
		System.out.println("run");
		return new Product(4,"test");
	}
	@RequestMapping(value="/stocks/{warehouseId}/products/{productId}", method=RequestMethod.GET)
	public ModelAndView getProduct(@PathVariable("warehouseId") int warehouseId, 
			@PathVariable("productId") int productId,
			@RequestParam("select") String[] select,
			@RequestParam("box") List<String> box,
			Model map) {
//		for(String s:select){
//			System.out.println(s);
//		}
//		for(String b:box){
//			System.out.println(b);
//		}
		Product p=warehouseRepository.getWarehouse(warehouseId).getProduct(productId);
		Product p1=warehouseRepository.getWarehouse(warehouseId).getProduct(productId+1);
		map.addAttribute("select", select);
		map.addAttribute("box", box);
		map.addAttribute("productSes", p);
		map.addAttribute("productSes", p1);
		ModelAndView mav = new ModelAndView("stockview");//mav will override model if the same name appear and mav is returned
		//mav.addObject("productSes", p);
		return mav;//if return string, p1 will be shown
	}
	
	//test for model attribute to retrieve the model from session
	@RequestMapping(value="/stocks/modelattr", method=RequestMethod.GET)
	public String getProduct(@ModelAttribute(value="productSes") Product p,Model map){
		map.addAttribute("product",p);
		return "modelattr";
	}
}
