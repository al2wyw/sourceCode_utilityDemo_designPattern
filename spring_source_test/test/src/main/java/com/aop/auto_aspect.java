package com.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class auto_aspect {
	
	public auto_aspect(){
		System.out.println("auto_aspect born");
	}
	@Pointcut("execution(java.lang.String changeValue(int))")
	private void pointcut(){}
	
	@Before("pointcut()")
	public void test(){
		System.out.println("@Aspect before advisor test is called");
	}
}
