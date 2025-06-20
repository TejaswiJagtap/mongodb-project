package com.artcode.thirtyfifty.user;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

	public UserResponse setUserResponse(User user) {
		UserResponse response = new UserResponse();
		response.setId(user.getId());
		response.setFirstName(user.getFirstName());
		response.setLastName(user.getLastName());
		response.setEmail(user.getEmail());
		response.setRole(user.getRole());
		response.setMobileNo(user.getMobileNo());
		

		return response;
	}

	public static UserResponse setDto(User user) {
		UserResponse response = new UserResponse();
		response.setEmail(user.getEmail());
		response.setId(user.getId());
		response.setFirstName(user.getFirstName());
		response.setLastName(user.getLastName());
		response.setPasswordCreated(StringUtils.isNotEmpty(user.getPassword()));
		return response;
	}

}
