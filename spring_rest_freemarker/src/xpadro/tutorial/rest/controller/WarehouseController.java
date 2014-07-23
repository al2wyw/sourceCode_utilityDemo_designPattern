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

/**
 * Contains CRUD operations on warehouses and its products.
 * @author xpadro
 *
 */
@Controller
public class WarehouseController {
	private static Logger logger = Logger.getLogger("main");

	@Autowired
	private WarehouseRepository warehouseRepository;

	@ModelAttribute(value="product1")
	public Product getFirstProduct(@RequestParam(value="product1",required=false) Integer id){
		if(id==null){
			id=new Integer(1);
			System.out.println("the product id is "+id+", called from getFirstProduct");
		}
		return warehouseRepository.getWarehouse(1).getProduct(id);
	}
	
	@RequestMapping(value="/test1", method=RequestMethod.GET)
	public void test(){
		//throw new MyException("2");
	}
	/**
	 * Returns the warehouse requested by its id.
	 * @param id
	 * @return The requested warehouse
	 */
	@RequestMapping(value="/warehouses/{warehouseId}", method=RequestMethod.GET)
	public @ResponseBody Warehouse getWarehouse(@PathVariable("warehouseId") int id) {
		return warehouseRepository.getWarehouse(id);
	}
	
	/**
	 * Adds a new product to the specified warehouse.
	 * @param warehouseId The id of the warehouse where to add the new product
	 * @param product The new product to be created
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/warehouses/{warehouseId}/products", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void addProduct(@PathVariable("warehouseId") int warehouseId, @RequestBody Product product, 
			HttpServletRequest request, HttpServletResponse response) {
		
		warehouseRepository.addProduct(warehouseId, product);
		response.setHeader("Location", request.getRequestURL().append("/").append(product.getId()).toString());
	}
	
	/**
	 * Removes a product from a specified warehouse.
	 * @param warehouseId The id of the warehouse where to remove the product
	 * @param productId The id of the product to be removed
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/warehouses/{warehouseId}/products/{productId}", method=RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removeProduct(@PathVariable("warehouseId") int warehouseId, @PathVariable("productId") int productId, 
			HttpServletRequest request, HttpServletResponse response) {
		
		warehouseRepository.removeProduct(warehouseId, productId);
	}
	
	/**
	 * Returns the product from a specified warehouse.
	 * @param warehouseId The id of the warehouse where to get the product
	 * @param productId
	 * @return
	 */
	@RequestMapping(value="/warehouses/{warehouseId}/products/{productId}", method=RequestMethod.GET)
	public @ResponseBody Product getProduct(@PathVariable("warehouseId") int warehouseId, @PathVariable("productId") int productId) {
		return warehouseRepository.getProduct(warehouseId, productId);
	}
	

	@RequestMapping(value="/warehouses/{warehouseId}/{productId}", method=RequestMethod.GET)
	public ModelAndView showProduct(@PathVariable("warehouseId") int warehouseId, @PathVariable("productId") int productId,HttpServletRequest request, Model model,ModelAndView mav) {
		Product p = warehouseRepository.getProduct(warehouseId, productId);
		Product p1 = warehouseRepository.getProduct(warehouseId, productId + 1);
		//request,session attribute can not have the same name with model object 
		model.addAttribute("product",p1);
		//request.getSession().setAttribute("product", p);
		mav.setViewName("product");
		mav.addObject("product", p);
		return mav;
	}
	
	@RequestMapping(value="/warehouses/{stockId}/products", method=RequestMethod.PUT , consumes="application/json")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void modifyProduct(@PathVariable("stockId") int stockId, @RequestBody Product product,
			HttpServletRequest request, HttpServletResponse response) {
		warehouseRepository.modifyProduct(stockId, product);
		response.setHeader("Location", request.getRequestURL().append("/").append(product.getId()).toString());
	}
	
	@RequestMapping(value="/warehouses/mav/{stockId}/{productId}", method=RequestMethod.GET)
	public ModelAndView showProduct(@PathVariable("stockId") int stockId, @PathVariable("productId") int productId) {
		
		ModelAndView mv = new ModelAndView("test");
		Product test = warehouseRepository.getProduct(stockId, productId);
		mv.addObject("product", test);
		return mv; 
	}
	
	@RequestMapping(value="/warehouses/{stockId}/{id}/{description}", method=RequestMethod.GET)
	public ModelAndView showProduct(@PathVariable("stockId") int stockId,@ModelAttribute Product product) {
		warehouseRepository.addProduct(stockId, product);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("test");
		return mv; 
	}
	
	@RequestMapping(value = "/warehouses/matrix/{ownerId}/products/{petId}", method = RequestMethod.GET)
	public void findProduct(
	@MatrixVariable Map<String, String> matrixVars,
	@MatrixVariable(pathVar="petId") Map<String, String> petMatrixVars) {
	// /spring/warehouses/matrix/42;q=11;r=12/products/21;q=22;s=23
	// matrixVars: ["q" : [11,22], "r" : 12, "s" : 23]
	// petMatrixVars: ["q" : 22, "s" : 23]
		System.out.println(matrixVars);
		System.out.println(petMatrixVars);
	}

	/**
	 * Handles ProductNotFoundException and returns a 404 response status code
	 */
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler({ProductNotFoundException.class, MyException.class})
	public void handleProductNotFound(Exception pe) {
		logger.warn("Product not found. Code: "+pe.getMessage()+ " exception type is "+pe.getClass().getName());
	}
	
	
	@InitBinder
	public void testBinder(WebDataBinder binder) {
		
		//binder.registerCustomEditor(requiredType, field, propertyEditor);
	}
}
