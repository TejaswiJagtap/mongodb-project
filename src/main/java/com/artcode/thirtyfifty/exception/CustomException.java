package com.artcode.thirtyfifty.exception;

import org.springframework.stereotype.Component;

@Component
public class CustomException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String httpStatus;

	private String message;
	
	public CustomException() {
		super();
	}

	public CustomException(String message) {
		super();
		this.message = message;
	}

	public CustomException( String message,String httpStatus) {
		super();
		this.httpStatus = httpStatus;
		this.message = message;
	}

	public String getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(String httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
	
	

}
