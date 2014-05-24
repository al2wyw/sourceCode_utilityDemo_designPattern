package com.myws.action;
import com.opensymphony.xwork2.ActionSupport;
import com.myws.service.queryMytabService;
import com.myws.model.mytab;
public class showMytabAction extends ActionSupport{
	private queryMytabService action;
	
	private static final long serialVersionUID = 1L;
	private String name;
	private mytab object;
	public String execute(){
		if(name!=null && !name.equals("")){
				object=action.queryByName(name);
				if(object!=null)
					return SUCCESS;
		}
		return INPUT;
	}
	public queryMytabService getAction() {
		return action;
	}
	public void setAction(queryMytabService action) {
		this.action = action;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public mytab getObject() {
		return object;
	}
	public void setObject(mytab object) {
		this.object = object;
	}
	
}
