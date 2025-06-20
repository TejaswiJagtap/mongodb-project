package com.artcode.thirtyfifty.user;

import lombok.Data;

@Data
public class UserRequest {

	private String firstName;

	private String lastName;

	private String mobileNo;

	private String email;

	private String password;
}
