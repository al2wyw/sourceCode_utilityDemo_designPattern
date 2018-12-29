package com.test;

import javax.servlet.http.HttpServletResponse;

import com.lookup.LookupTest;
import com.model.Information;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.aop.TestService;
import com.aop.myMethodBeforeAdvice;
import com.model.Person;
import com.qualifier.customQ;

import java.util.List;

@Controller
public class testController implements BeanFactoryAware{
	
	@Autowired
	@Qualifier("eles")
	private test ttt;
	
	@Autowired(required=false)
	private test_for_no_autowired_candidates t;
	
	private test1 t1;
	
	private test2 t2;
	
	@Value("random number is #{T(java.lang.Math).random()}")
	private String test;
	
	@Value("#{'test for appending '+props['abc']}")
	private String value;
	
	@Value("#{config.callWithValue('test')}")
	private String config;
	
/*	@Value("#{systemProperties.myProp}")
	private String pro;*/
	
	private BeanFactory b;
	
	@Autowired//required = "true" by default
	public testController(test1 t1){
		this.t1 = t1;
		System.out.println("testcontroller born with test1!");
	}
	
	@Autowired
	private TestService testService;
	
	@Autowired
	private autowireByNameTester tester;
	
	@Autowired
	private testScopedProxy testScopedProxy;
	
	@Autowired
	private test_lazy_autowired test_lazy_autowired;
	
	@Autowired
	@Lazy
	private LazyInterceptor test_lazy_autowired_jdk;
	
	@Autowired
	private com.qualifier.tester testert;
	
	@Autowired
	@customQ("dot")
	private com.test.service dot;
	
	@Autowired
	private test_Resource_PropertyEditor bean;
	
	@Autowired
	private autowiredSon son;
	
	@Autowired
	private test_transactional_Person retriever;

	@Autowired
	private test_transactional_Information testLimit;

	@Autowired
	private myMethodBeforeAdvice flag;

	@Autowired
	private test_aspectj_ltw test_aspectj_ltw;

	@Autowired
	private LookupTest lookupTest;
	
/*	@Autowired
	public testController(test2 t2){
		this.t2 = t2;
		System.out.println("testcontroller born with test2!");
	}*/
	
	@RequestMapping(value="test", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public String test() {
		
		test_lazy_autowired.lazyAutowiredIntercept();
		
		test_lazy_autowired_jdk.lazyAutowiredIntercept();
		
		//test_factorybean
		Object o = b.getBean("test");
		String s = null;
		if(o instanceof test_factorybean)
			s = "test_factorybean";
		else
			s = "test4";
		
		//test_lazy
		o = null;
		o = b.getBean("test123");
		if(o != null && o instanceof test_lazy)
			((test_lazy)o).lazyIntercept();
		
		s+= (String)((test)b.getBean("eles")).getEles().get(1);
		s+=ttt.getEles().get(2);
		
		s+=test;
		
		s+=value;
		
		s+=config;
		
		testService.changeValue(100);

		testService.methodName();
		
		tester.tester();
		
		testScopedProxy.showTime();
		
		testert.call();
		
		dot.doCall();
		
		bean.call();
		
		son.test();

		test_aspectj_ltw.test();

		lookupTest.test();
		
		System.out.println("this works Jrebel test....");
		
		testTransaction();
		
		return s;
		
	}

	@RequestMapping(value="flag", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public String setUpFlagOfAdvice(@RequestParam("f") boolean f){
		flag.setFlag(f);
		return "OK";
	}
	
	@RequestMapping(value="clear", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public String clearAllTables(){
		retriever.clearPerson();
		return "done";
	}

	@RequestMapping(value="getInfo", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Information> getInfoByLimit(@RequestParam("offset") int offset,@RequestParam("limit") int limit){
		List<Information> result = testLimit.getInfo(offset,limit);
		return result;
	}

	@RequestMapping(value="test/with/args", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public String testWithStringArg(){
		testService.methodArgs("test with string");
		testService.methodArgs(123);
		return "done";
	}
	
	
	@InitBinder
	public void setHeaders(HttpServletResponse response){
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate");
		response.setDateHeader("Expires", 0);
	}


	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		
		b = beanFactory;
	}
	
	private void testTransaction(){
		Person p = new Person();
		
		p.setId("0x1201");
		p.setName("Perter");
		p.setSex("male");
		p.setSalary(9345.34D);
		retriever.insertPerson(p);
		
		p.setId("0x1202");
		p.setName("Ken");
		p.setSex("male");
		p.setSalary(6345.53D);
		retriever.insertPerson(p);
		
		p.setId("0x1203");
		p.setName("Anne");
		p.setSex("female");
		p.setSalary(3245.93D);
		retriever.insertPerson(p);
	}
}
