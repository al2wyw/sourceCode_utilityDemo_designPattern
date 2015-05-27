package com.aop;

public class TestService {

	private testProtected test;
	
	public testProtected getTest() {
		return test;
	}

	public void setTest(testProtected test) {
		this.test = test;
	}

	public String changeValue(int value){
		System.out.println("change value is called");
		test.testProtect(value);
		return String.valueOf(value);
	}
}
