package com.artcode.thirtyfifty.login;

import lombok.Data;

@Data
public class LoginRequest {
	
	private String email;
	
	private String password;
	
	private String ipAddress;

	private String os;

	private String version;
	
	private String accessType;
	
	private String browser;
}
