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

	private test4 test4;

	public com.test.test4 getTest4() {
		return test4;
	}

	public void setTest4(com.test.test4 test4) {
		this.test4 = test4;
	}
}
