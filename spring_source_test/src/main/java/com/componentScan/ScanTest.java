package com.componentScan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ScanTest {
	public ScanTest(){
		System.out.println("component scan for the new package");
	}

	@Autowired
	private MyFunctionProvider provider;

	@PostConstruct
	public void init(){
		provider.action();
	}
}
