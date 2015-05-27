package com.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

import com.test.test1;
import com.test.test_lazy_autowired;
import com.test.test_lazy_autowired_jdk;

import com.model.Person;

public class myAspect {
	
	public void beforeInsertPerson(JoinPoint jp){
		Object[] o = jp.getArgs();
		String name = ((Person)o[0]).getName();
		System.out.println("before insert person "+ name);
	}
	
	public void show(JoinPoint jp){
		Object target = jp.getTarget();
		if(target instanceof TestService){
			System.out.println("this is test service");
			TestService fun = (TestService)target;
			fun.changeValue(999);
		}
		System.out.println("my aspect show");
	}
	
	public Object showAgain(ProceedingJoinPoint pjp){
		System.out.println("around advice before the call");
		Object o = null;
		try {
			o = pjp.proceed(pjp.getArgs());
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("around advice after the call");
		return o;
	}
	
	public void interceptProtected(){
		System.out.println("intercept protected!");
	}
	
	public void showAfter(ProceedingJoinPoint pjp){//ProceedingJoinPoint just can work for around advice
		try {
			pjp.proceed();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void showEarlyReferencedProxy(JoinPoint jp){
		Object o = jp.getTarget();
		if(o instanceof test1){
			System.out.println("test1 is proxied");
		}
	}
	
	public void lazyInterceptor(){
		System.out.println("test_lazy is proxied");
	}
	
	public void lazyAutowiredInterceptor(JoinPoint jp){
		Object o = jp.getTarget();
		if (o instanceof test_lazy_autowired)
			System.out.println("test_lazy_autowired is proxied");
		if(o instanceof test_lazy_autowired_jdk)
			System.out.println("test_lazy_autowired_jdk is proxied");
	}
}
