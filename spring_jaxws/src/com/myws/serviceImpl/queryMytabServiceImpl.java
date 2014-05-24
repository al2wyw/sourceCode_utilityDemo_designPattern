package com.myws.serviceImpl;

import com.myws.model.mytab;
import com.myws.service.queryMytabService;
import com.myws.dao.queryMytab;
public class queryMytabServiceImpl implements queryMytabService {
	private queryMytab action;
	@Override
	public mytab queryByName(String name) {
		mytab o = null;
		try{
			o=action.query(name);
		}catch(Exception e){
			e.printStackTrace();
		}
		return o;
	}
	public queryMytab getAction() {
		return action;
	}
	public void setAction(queryMytab action) {
		this.action = action;
	}

}
