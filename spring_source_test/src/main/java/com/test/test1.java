package com.test;

public class test1{


	private test2 test2;
	


	public test1(){
		
		System.out.println("test1");
	}



	public test2 getTest2() {
		return test2;
	}



	public void setTest2(test2 test2) {
		this.test2 = test2;
	}

	public void testForEarlyReferencedWithProxied(){
		System.out.println("testForEarlyReferenced");
	}
}
