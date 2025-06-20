/**
 * 
 */
package com.artcode.thirtyfifty.users.otp;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.artcode.thirtyfifty.constants.RequestMappingConstant;

import lombok.extern.slf4j.Slf4j;

/**
 * @author atulg
 *
 */
@RestController
@RequestMapping(RequestMappingConstant.USER_OTP)
@Slf4j
public class UsersOtpController {

//	@Autowired
//	private UsersOtpService service;
//	
//	@PostMapping("/send")
//	public ResponseEntity<String> sendOtp(@RequestParam("email") String email) throws Exception {
//		log.info(" Send otp to email : {}",email);
//		return new ResponseEntity<>(service.save(email), HttpStatus.OK);
//	}
//	
//	@PostMapping("/verify")
//	public ResponseEntity<String> verifyOtp(@RequestParam("email") String email,@RequestParam("otp") String otp) throws Exception {
//		log.info(" verify otp  email : {} otp : {}",email,otp);
//		return new ResponseEntity<>(service.verifyOtp(email, otp), HttpStatus.OK);
//	}
}
