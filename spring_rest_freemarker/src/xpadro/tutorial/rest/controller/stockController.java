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
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import xpadro.tutorial.rest.exception.MyException;
import xpadro.tutorial.rest.exception.ProductNotFoundException;
import xpadro.tutorial.rest.model.Product;
import xpadro.tutorial.rest.model.Warehouse;
import xpadro.tutorial.rest.repository.WarehouseRepository;

//test whether exceptionhandler will handle the exception from the other controller, no
@Controller
public class stockController {
	private static Logger logger = Logger.getLogger("main");

	@Autowired
	private WarehouseRepository warehouseRepository;
	
	@RequestMapping(value="/stocks/{warehouseId}/products/{productId}", method=RequestMethod.GET)
	public @ResponseBody Product getProduct(@PathVariable("warehouseId") int warehouseId, @PathVariable("productId") int productId) {
		return warehouseRepository.getProduct(warehouseId, productId);
	}
}
