package com.artcode.thirtyfifty.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.artcode.thirtyfifty.constants.RequestMappingConstant;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(RequestMappingConstant.USER)
@Slf4j
public class UserController {

	@Autowired
	private UserService service;

	@PutMapping("/{id}")
	public ResponseEntity<String> updateStudent(@RequestBody UserRequest request, String id) throws Exception {
		log.info("update user details by id : {} request:{}  ");
		return new ResponseEntity<String>(service.update(id, request), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<String> getById(@PathVariable("id") String id) {
		log.info("get user by id : {} ", id);
		return new ResponseEntity<>(this.service.getById(id), HttpStatus.OK);

	}

	@GetMapping
	public ResponseEntity<Page<UserResponse>> getAll(@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "10") int pageSize) {

		return new ResponseEntity<Page<UserResponse>>(service.getAll(pageNo, pageSize), HttpStatus.OK);
	}

}
