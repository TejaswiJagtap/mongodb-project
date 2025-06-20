package com.artcode.thirtyfifty.login;

import java.io.IOException;

import com.artcode.thirtyfifty.user.UserRequest;

public interface LoginService {

	public String signup(UserRequest request) throws IOException;
	
	public String login(LoginRequest request)throws Exception;

	public String forgotPassword(String email) throws Exception;

	public String refreshtoken(LoginRequest loginRequest);

	public String setNewPassword(String email, String password);

	public String changePassword(ChangePasswordRequest request);
	
	public String verifyOtp(String otp, String email) throws Exception;
}
