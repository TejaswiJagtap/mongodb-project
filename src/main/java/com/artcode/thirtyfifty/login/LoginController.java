package com.artcode.thirtyfifty.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.artcode.thirtyfifty.constants.RequestMappingConstant;
import com.artcode.thirtyfifty.user.UserRequest;
import com.artcode.thirtyfifty.utils.JsonUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(RequestMappingConstant.USER)
@Slf4j
public class LoginController {

	@Autowired
	private LoginService service;

	@Autowired
	private JsonUtils utils;

	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody UserRequest request) throws Exception {
		log.info("signup request : {}", request.toString());
		return new ResponseEntity<String>(service.signup(request), HttpStatus.CREATED);

	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginRequest request) throws Exception {
		log.info("login request : {}", request.toString());
		return new ResponseEntity<String>(this.service.login(request), HttpStatus.OK);

	}

	@PostMapping("/forgot-password")
	public ResponseEntity<String> forgotPassword(@RequestParam String email, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Users forgot password : {}", email);
		service.forgotPassword(email);
		return new ResponseEntity<String>(utils.objectMapperSuccess("Otp sent"), HttpStatus.OK);
	}

	@PostMapping("/vertify-otp")
	public ResponseEntity<String> checkOtp(@RequestParam String email, @RequestParam String otp)
			throws Exception {
		service.verifyOtp(email, otp);
		return new ResponseEntity<String>(utils.objectMapperSuccess("Otp Verified Successfully"), HttpStatus.OK);
	}

	@PostMapping("/set-new-password")
	public ResponseEntity<String> setNewPassword(String email, String password) throws Exception {
		log.info("set new password email : {}, password : {}", email, password);
		return new ResponseEntity<String>(service.setNewPassword(email, password), HttpStatus.OK);

	}

	@PostMapping("/change-password")
	public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request) {
		log.info("change  password : {}", request);
		return new ResponseEntity<String>(service.changePassword(request), HttpStatus.OK);

	}
}
