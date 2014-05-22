package com.myws.serviceImpl;

import com.myws.model.Person;
import com.myws.service.logAdvisor;

public class logBeforeAgainAdvisor implements logAdvisor {

	@Override
	public void log(Person person) {
		// TODO Auto-generated method stub
		System.out.println("test before again save service: person name is "+person.getName());
	}


}
