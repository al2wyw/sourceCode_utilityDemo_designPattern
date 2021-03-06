package com.test;

import com.aop.TestService;
import com.aop.myMethodBeforeAdvice;
import com.aop.testInterface;
import com.lookup.LookupTest;
import com.qualifier.customQ;
import com.utils.LoggerUtils;
import com.validation.BizModel;
import com.validation.BizService;
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

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.text.DateFormat;
import java.util.Date;

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
		LoggerUtils.getLogger().info("testcontroller born with test1!");
	}
	
	@Autowired
	private TestService testService;

	@Autowired
	private testInterface testInterface;
	
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
	private myMethodBeforeAdvice flag;

	private test_aspectj_ltw test_aspectj_ltw = new test_aspectj_ltw();

	@Autowired
	private LookupTest lookupTest;

	@Autowired
	private BizService bizService;
	
/*	@Autowired
	public testController(test2 t2){
		this.t2 = t2;
		log.info("testcontroller born with test2!");
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

		LoggerUtils.getLogger().info("this works Jrebel test....");
		
		return s;
		
	}

	@RequestMapping(value="flag", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public String setUpFlagOfAdvice(@RequestParam("f") boolean f){
		flag.setFlag(f);
		return "OK";
	}

	@RequestMapping(value="test/with/args", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public String testWithStringArg(){
		testService.methodArgs("not intercept");
		testService.methodArgs("test with string", "intercepted 1");
		testService.methodArgs(123,"intercepted 2");
		testService.methodArgs("intercepted 3",4333);
		testService.methodArgs("not intercept",new Date());

		testInterface.testMethod();
		return "done";
	}

	@RequestMapping(value="test/with/validate", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public String testWitValidate(@RequestParam("null") boolean isnull){
		bizService.bizCheck(isnull ? null : new Object());
		return "done";
	}

	@RequestMapping(value="test/with/bizModel", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public String testWitValidateAgain(@RequestParam("name") String name,
									   @RequestParam("birth") String dat,
									   @RequestParam("i") String i) throws Exception{
		BizModel bizModel = new BizModel();
		bizModel.setName(name);
		bizModel.setBirth(DateFormat.getDateInstance().parse(dat));
		bizModel.setI(Integer.valueOf(i));
		bizService.bizCheckAgain(bizModel);
		return "done";
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public void handleConstraintViolationException(ConstraintViolationException e){
		e.getConstraintViolations().stream().forEach(System.out::println);
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
}
