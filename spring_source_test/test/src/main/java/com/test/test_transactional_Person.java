package com.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.model.Person;
import com.transaction.PersonDao;

@Transactional(propagation=Propagation.SUPPORTS,rollbackForClassName="myCheckedException",noRollbackForClassName="UnexpectedRollbackException")
@Service
public class test_transactional_Person {
	
	private PersonDao personDao;
	
	@Autowired
	private test_transactional_Course courseService;
	
	@Autowired
	public test_transactional_Person(PersonDao personDao){
		this.personDao = personDao;
		System.out.println("create the table person first");
		personDao.dropTable();
		personDao.createTable();
	}
	
	public Person getPersonById(String id){
		return personDao.getPersonById(id);
	}
	
	public void insertPerson(Person p){
		personDao.insertPerson(p);
		courseService.insertCourse(p);
	}
	
	public void clearPerson(){
		personDao.clearTable();
		courseService.clearCourse();
	}
}
