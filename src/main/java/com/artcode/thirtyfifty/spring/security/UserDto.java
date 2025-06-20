package com.artcode.thirtyfifty.spring.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {

	private String id;
	
	private String email;
	
	private String mobileNo;
	
	//private String password;
	
	private String firstName;
	
	private String lastName;
	
	private String refreshToken;
	
	private String token;
	
	private String role;
	
	private Long activeSessionId;
	
	private String profilePic;
	
	//private List<ModulesDto> modulesDtos;
	
	
}
