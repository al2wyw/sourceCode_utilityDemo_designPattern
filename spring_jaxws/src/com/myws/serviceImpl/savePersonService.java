package com.myws.serviceImpl;
import com.myws.dao.savePerson;
import com.myws.model.Person;
public class savePersonService {
	private savePerson save;

	public savePerson getSave() {
		return save;
	}

	public void setSave(savePerson save) {
		this.save = save;
	}
	public void saveAction(Person p){
		save.save(p);
	}
}
