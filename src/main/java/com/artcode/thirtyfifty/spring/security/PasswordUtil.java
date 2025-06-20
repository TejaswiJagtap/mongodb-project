package com.artcode.thirtyfifty.spring.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {
	
	static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	public static String getPasswordHash(String password) {
		return encoder.encode(password);
	}
	
	public static boolean matchPassword(String oldPassword, String encodedPassword) {
		return encoder.matches(oldPassword, encodedPassword);
	}

}
