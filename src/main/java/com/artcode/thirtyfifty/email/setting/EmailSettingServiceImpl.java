package com.artcode.thirtyfifty.email.setting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artcode.thirtyfifty.utils.JsonUtils;

import jakarta.transaction.Transactional;

@Service
public class EmailSettingServiceImpl implements EmailSettingService {

	@Autowired
	private EmailSettingRepository repository;

	@Autowired
	private JsonUtils utils;

	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public String saveUpdate(EmailSettingDto dto) throws Exception {
		EmailSetting settings = repository.findTopByOrderByIdAsc().orElseGet(EmailSetting::new);
		settings.setEmail(dto.getEmail());
		settings.setPassword(dto.getPassword());
		settings.setHost(dto.getSmtpHost());
		settings.setPort(dto.getSmtpPort());

		repository.save(settings);
		return utils.objectMapperSuccess("Saved successfully");
	}

	@Override
	public String getEmailSettings() throws Exception {
		EmailSetting emailSetting = repository.findTopByOrderByIdAsc()
				.orElseThrow(() -> new Exception(utils.objectMapperError("email not present")));
		EmailSettingDto dto =new EmailSettingDto(emailSetting.getId(),emailSetting.getEmail(),emailSetting.getPassword(),emailSetting.getHost(),emailSetting.getPort());
		return utils.objectMapperSuccess(dto ,"email setting details");
	}
}
