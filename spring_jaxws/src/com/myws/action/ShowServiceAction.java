package com.myws.action;
import com.opensymphony.xwork2.*;
import com.myws.service.ShowService;
import com.myws.model.ws;
import java.util.List;
import java.util.ArrayList;
public class ShowServiceAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ShowService action;
	private List<ws> urls ;
	public ShowService getAction() {
		return action;
	}
	public void setAction(ShowService action) {
		this.action = action;
	}
	public List<ws> getUrls() {
		return urls;
	}
	public void setUrls(List<ws> urls) {
		this.urls = urls;
	}
	
	public String execute(){
		urls = new ArrayList<ws>(10);
		if(action.action(urls))
			return SUCCESS;
		else
			return INPUT;
	}
	public void init(){
		System.out.println("init method called");
	}
}
