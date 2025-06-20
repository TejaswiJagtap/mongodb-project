package com.artcode.thirtyfifty.user;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

import com.artcode.thirtyfifty.session.Session;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String firstName;

	private String lastName;

	private String mobileNo;

	private String email;

	private String password;

	@Column(name = "role")
	private String role;

	@Column(name = "otp")
	private String otp;

	private boolean isDeleted;
	
	private Session session;
}
