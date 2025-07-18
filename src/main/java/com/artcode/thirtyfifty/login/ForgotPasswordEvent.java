package com.artcode.thirtyfifty.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPasswordEvent {

	private String email;
	
	private String otp;
}
