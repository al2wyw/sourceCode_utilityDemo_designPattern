package com.qualifier;

import org.springframework.beans.factory.annotation.Autowired;

public class tester {
	@Autowired
	@customQ("test_custom_qualifier")
	private testee ssss;
	
	@Autowired
	@anotherCustomQ("test_custom_qualifier")
	private testee tttt;
	
	public void call(){
		ssss.test();
		tttt.test();
	}
	
	
}
