package com.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class autowireByNameTester {
	private testAutowireByName autowire;
	
	@Autowired(required=false)
	@Qualifier("autoproperty")
	private testAutowireByName autowiret;

	public testAutowireByName getAutowire() {
		return autowire;
	}

	public void setAutowire(testAutowireByName autowire) {
		this.autowire = autowire;
	}
	
	public void tester(){
		autowire.showMe();
		if(autowiret!=null){
			autowiret.showMe();
		}
	}
}
