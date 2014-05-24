package com.myws.model;

public class ws implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String url;
	private String wsName;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getWsName() {
		return wsName;
	}
	public void setWsName(String wSName) {
		wsName = wSName;
	}
}
