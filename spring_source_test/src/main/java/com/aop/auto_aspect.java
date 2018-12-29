package com.aop;

import com.annotation.FunProvider;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
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

	@Before(value = "execution(void com.aop.TestService.methodArgs(*)) && args(arg)")
	public void testArgs(JoinPoint joinPoint, String arg){
		System.out.println(joinPoint.toLongString());
		System.out.println("testArgs :" + arg);
	}
}
