package com.artcode.thirtyfifty.email.setting;

public interface EmailSettingService {

	public String saveUpdate(EmailSettingDto request) throws Exception;

	public String getEmailSettings() throws Exception;
	
}
