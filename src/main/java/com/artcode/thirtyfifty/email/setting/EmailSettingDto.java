package com.artcode.thirtyfifty.email.setting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailSettingDto {

	private String id;

	private String email;

	private String password;
	
	private String smtpPort;
	
	private String smtpHost;
	
	
}
