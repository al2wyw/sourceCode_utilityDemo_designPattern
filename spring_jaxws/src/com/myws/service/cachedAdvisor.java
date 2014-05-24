package com.myws.service;
import org.aspectj.lang.ProceedingJoinPoint;
public interface cachedAdvisor {
	Object cachedHandler(ProceedingJoinPoint point,String key) throws Throwable;
}
