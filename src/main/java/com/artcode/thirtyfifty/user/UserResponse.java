package com.artcode.thirtyfifty.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

	private String id;

	private String firstName;

	private String lastName;

	private String mobileNo;

	private String email;

	private String password;

	private boolean passwordCreated;

	private boolean isBlocked = false;

	private String role;
}
