package com.transaction;

import com.model.Person;

public interface PersonDao extends TableDao {
	public Person getPersonById(String id);
	public void removePersonById(String id);
	public void updatePersonById(Person p);
	public void insertPerson(Person p);
}
