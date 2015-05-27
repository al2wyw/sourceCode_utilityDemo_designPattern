package com.test;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class test3 implements InitializingBean{
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(test1, "test1 must not be null");
		
	}
	private test1 test1;
public test1 getTest1() {
		return test1;
	}
	public void setTest1(test1 test1) {
		this.test1 = test1;
	}
public test3(){
		
		System.out.println("test3");
	}
}
