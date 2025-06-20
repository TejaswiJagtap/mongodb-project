/**
 * 
 */
package com.artcode.thirtyfifty.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.OK)
public class CustomMessageApp extends RuntimeException{

private static final long serialVersionUID = 1L;
	
	String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public CustomMessageApp() {
		super();
	}

	public CustomMessageApp(String message) {
		super();
		this.message = message;
	}
	
	
}
