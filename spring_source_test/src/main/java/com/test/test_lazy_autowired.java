package com.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class test_lazy_autowired {
	//test for no default consturctor for cglib proxy, work! it is not like ConfigurationClass
	@Autowired
	public test_lazy_autowired(@Qualifier("config")Config config){
		String test = config.callWithValue("testfornodefaultconsturtor");
		System.out.println(test);
		System.out.println("test for lazy autowired instantiation");
	}
	public void lazyAutowiredIntercept(){
		System.out.println("test for lazy autowired Intercept");
	}
}
