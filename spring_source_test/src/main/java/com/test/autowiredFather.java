package com.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


public class autowiredFather {
	@Autowired
	@Qualifier("config")
	private Config config;
	
	public void test(){
		String test = config.callWithValue("autowired father");
		System.out.println(test);
	}
}
