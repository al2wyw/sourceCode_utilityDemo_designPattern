package com.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.aop.framework.Advised;
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

	@Before(value = "execution(void com.aop.TestService.methodArgs(..)) && args(*,arg)", argNames = "joinPoint,arg")//argNames can be emitted
	public void testArgs(JoinPoint joinPoint, String arg){
		System.out.println(joinPoint.toLongString());
		System.out.println("testArgs :" + arg);
	}

	@After(value = "execution(void com.aop.TestService.methodArgs(..)) && args(arg1,arg2)")
	public void testArgs(JoinPoint joinPoint, String arg1, Integer arg2){ //int arg2 will not work
		System.out.println(joinPoint.toLongString());
		System.out.println("test after Args :" + arg1 + " | " + arg2);
	}

	@Around("execution(void com.aop.testInterface.testMethod())") //可以拦截子类的覆盖方法
	public Object testImpl(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
		System.out.println("testImpl " + proceedingJoinPoint.toLongString());
		Object c = proceedingJoinPoint.getThis();
		if(c instanceof Advised){
			Advised a = (Advised)c;
			Object o = a.getTargetSource().getTarget();
			System.out.println("target is " + o);
		}
		return proceedingJoinPoint.proceed();
	}
}
