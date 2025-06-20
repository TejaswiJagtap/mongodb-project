package com.artcode.thirtyfifty.email.setting;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("email-setting")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailSetting {

	@Id
	private String  id;
	
	private String email;

	private String password;

	private String host;

	private String port;
}
