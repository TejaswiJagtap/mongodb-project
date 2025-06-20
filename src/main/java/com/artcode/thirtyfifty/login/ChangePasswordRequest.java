package com.artcode.thirtyfifty.login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest {

	private Long id;
	
	private String oldPassword;
	
	private String newPassword;

	@Override
	public String toString() {
		return "ChangePasswordRequest [oldPassword=" + oldPassword + ", id=" + id + ", newPassword=" + newPassword + "]";
	}
}
