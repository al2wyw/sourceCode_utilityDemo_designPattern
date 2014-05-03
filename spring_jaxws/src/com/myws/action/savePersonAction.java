package com.myws.action;
import com.opensymphony.xwork2.*;
import com.myws.service.savePerson;
import com.myws.model.Person;
public class savePersonAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	private savePerson action;
	private Person person;
	public String execute(){
		if(person!=null){
			action.save(person);
		
			return SUCCESS;
		}
		return INPUT;
	}
	@Override
	public String input() throws Exception {
		// TODO Auto-generated method stub
		
		return INPUT;
	}
	
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public savePerson getAction() {
		return action;
	}
	public void setAction(savePerson action) {
		this.action = action;
	}
	
}
