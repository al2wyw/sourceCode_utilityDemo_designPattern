package com.myws.serviceImpl;

import com.myws.model.Person;
import com.myws.service.saveService;

public class savePersonModService implements saveService {

	@Override
	public void saveAction(Person p) {
		// TODO Auto-generated method stub
		System.out.println(this.getClass().getCanonicalName());
	}
	
}
