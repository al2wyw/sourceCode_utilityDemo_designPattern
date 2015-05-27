package com.model;

import java.io.Serializable;

public class Course implements Serializable {
	private String cid;
	private String cname;
	private String id;
	private final static long serialVersionUID = 1L;
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
