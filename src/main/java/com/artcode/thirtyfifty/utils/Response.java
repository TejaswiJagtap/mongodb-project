package com.artcode.thirtyfifty.utils;

import java.io.Serializable;

public class Response implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8879399365559700786L;
	private String message;
	
	
	public Response() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Response(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
