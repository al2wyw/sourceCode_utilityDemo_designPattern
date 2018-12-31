package com.aop;

public class TestService{

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

	public String methodName(){
		System.out.println("methodName is called");
		return "method name";
	}

	public void methodArgs(Object arg){
		System.out.println("methodArgs 1 is called: arg1 "+arg.toString());
	}

	public void methodArgs(Object arg,Object arg2){
		System.out.println("methodArgs 2 is called: arg1 "+ arg.toString() + " arg2 " + arg2.toString());
	}

}
