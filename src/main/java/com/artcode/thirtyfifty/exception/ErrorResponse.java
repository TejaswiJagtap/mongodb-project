package com.artcode.thirtyfifty.exception;

public class ErrorResponse {

	private String requestApiName;

	private String msg;
	
	private String cause;

	public String getRequestApiName() {
		return requestApiName;
	}

	public void setRequestApiName(String requestApiName) {
		this.requestApiName = requestApiName;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	
}
