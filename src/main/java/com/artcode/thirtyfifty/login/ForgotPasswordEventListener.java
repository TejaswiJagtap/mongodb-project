package com.artcode.thirtyfifty.login;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.artcode.thirtyfifty.email.setting.EmailSetting;
import com.artcode.thirtyfifty.email.setting.EmailSettingRepository;
import com.artcode.thirtyfifty.utils.JavaMailUtil;

@Service
public class ForgotPasswordEventListener {

	private final EmailSettingRepository emailSettingRepository;

	public ForgotPasswordEventListener(EmailSettingRepository emailSettingRepository) {
		this.emailSettingRepository = emailSettingRepository;
	}

	@KafkaListener(topics = "forgot-password-topic", groupId = "forgot-password-group")
	public void sendForgotPasswordEmail(ForgotPasswordEvent event) {
		try {
			EmailSetting emailSetting = emailSettingRepository.findTopByOrderByIdAsc()
					.orElseThrow(() -> new Exception("Email settings not found"));

			// Send the email
			JavaMailUtil.sendMail("Forgot Password OTP", event.getEmail(), "Your OTP is: " + event.getOtp(), null,
					emailSetting.getEmail(), emailSetting.getPassword());

			System.out.println("Email Sent to: " + event.getEmail());
//			acknowledgment.acknowledge(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
