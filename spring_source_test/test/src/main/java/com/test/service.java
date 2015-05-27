package com.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Lazy;

import com.qualifier.customQ;

//used to test AnnotationMetadataReadingVisitor
@Configuration
@ComponentScan(basePackages="com.componentScan")
@ImportResource("classpath:properties-config.xml")
public class service {
	//@Autowired
	private Config config;

	//this will be called twice, it is a little tricky, as @Autowired is on service class(injected into testController) and 
	//@Autowired is on the super class of enhanced service class(Autowired bp will check super class), so twice!!!
	@Autowired
	public void testCGLIBoverried(@Qualifier("config")Config config){
		this.config = config;
		String test = config.callWithValue("99999"+" "+this);
		System.out.println(test);
	}

	public service(){
		System.out.println("@Configuration default constructor called");
	}
	//this will not be called because service bean is an CGLIB proxy with no @Autowired on constructor
	@Autowired
	public service(@Qualifier("config")Config config){
		System.out.println("@Configuration constructor with parameters called");
		this.config = config;
		String test = config.callWithValue("50099"+" "+this);
		System.out.println(test);
	}
	
	@Bean(name="service.dot")
	@customQ("dot")//did not add qualifier to bd, but checkQualifier will check this annotation
	public service dot(@Value("${test}") String test){
		System.out.println("born of the dot");
		System.out.println(test);
		test = config.callWithValue("10099"+" "+this);
		System.out.println(test);
		return this;
	}
	
	@Bean(name="service.test")
	@Lazy
	public service test(){
		return this;
	}
	
	public void doCall(){
		System.out.println("call the service"+" "+this);
	}
}
