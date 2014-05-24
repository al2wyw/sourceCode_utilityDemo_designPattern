package com.myws.serviceImpl;
import com.myws.dao.savePerson;
import com.myws.model.Person;
import com.myws.service.saveService;
public class savePersonService implements saveService{
	private savePerson save;
	private savePerson saveDB;
	private boolean flag;
	public boolean isFlag() {
		return flag;
	}

	public savePersonService() {
		super();
		// TODO Auto-generated constructor stub
		System.out.println("Service save person initialized");
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public savePerson getSaveDB() {
		return saveDB;
	}

	public void setSaveDB(savePerson saveDB) {
		this.saveDB = saveDB;
	}

	public savePerson getSave() {
		return save;
	}

	public void setSave(savePerson save) {
		this.save = save;
	}
	public void saveAction(Person p) {
		System.out.println(this.getClass().getCanonicalName());
		try{
			if(flag)
				save.save(p);
			else
				saveDB.save(p);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
