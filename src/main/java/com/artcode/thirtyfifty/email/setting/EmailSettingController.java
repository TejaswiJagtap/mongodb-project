package com.artcode.thirtyfifty.email.setting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.artcode.thirtyfifty.constants.RequestMappingConstant;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(RequestMappingConstant.EMAIL_SETTING)
@Slf4j
public class EmailSettingController {

	@Autowired
	private EmailSettingService service;
	
	@PostMapping
	public ResponseEntity<String> saveUpdate(@RequestBody EmailSettingDto request) throws Exception {
		log.info("save email request : {}", request.toString());
		return new ResponseEntity<String>(service.saveUpdate(request), HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<String> getEmailSettings() throws Exception {
		log.info("get email details : {}");
		return new ResponseEntity<String>(service.getEmailSettings(), HttpStatus.OK);
	}
	
}
