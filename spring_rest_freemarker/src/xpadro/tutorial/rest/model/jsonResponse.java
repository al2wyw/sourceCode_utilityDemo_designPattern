package xpadro.tutorial.rest.model;

import java.io.Serializable;

public class jsonResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	private String errorMsg;
	private String successMsg;
	private String type;
	private String body;
	
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getSuccessMsg() {
		return successMsg;
	}
	public void setSuccessMsg(String successMsg) {
		this.successMsg = successMsg;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
	
}
