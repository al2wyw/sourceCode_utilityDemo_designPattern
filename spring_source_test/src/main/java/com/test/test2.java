package com.test;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class test2 implements InitializingBean{
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(test3, "test3 must not be null");
		
	}
	private test3 test3;
	
	public test2(){
		
		System.out.println("test2");
	}

	public test3 getTest3() {
		return test3;
	}

	public void setTest3(test3 test3) {
		this.test3 = test3;
	}
}
