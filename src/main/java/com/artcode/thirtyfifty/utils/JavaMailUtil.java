package com.artcode.thirtyfifty.utils;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

/**
 * The method prepareMessage() is not being used in the sendMail() method.
 * Instead, MimeMessageHelper is being used to set the email's content, subject,
 * and attachments.
 */

@Component
public class JavaMailUtil {

	private static final Logger log = LoggerFactory.getLogger(JavaMailUtil.class);

	public static void sendMail(String subject, String recipient, String body, MultipartFile[] attachments,
			String myAccountEmail, String password) throws Exception {

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		props.put("mail.smtp.ssl.checkserveridentity", "false");
		props.put("mail.smtp.ssl.protocols", "TLSv1.2");

		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(myAccountEmail, password);
			}
		});

		System.out.println("Email: " + myAccountEmail);
		System.out.println("Password: " + password);

		MimeMessage message = new MimeMessage(session);
		MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
		messageHelper.setFrom(myAccountEmail);
		messageHelper.setTo(recipient);
		messageHelper.setSubject(subject);

		if (attachments == null || attachments.length == 0) {
			messageHelper.setText(body, true);
		} else {
			messageHelper.setText(body, true);
			for (MultipartFile multipartFile : attachments) {
				if (multipartFile != null) {
					messageHelper.addAttachment(multipartFile.getOriginalFilename(), multipartFile);
				}
			}
		}
		Transport.send(message);
		log.info("Message Sent successfully");
	}

	public static void sendMail(String subject, String recepient, String body, String myAccountEmail, String password)
			throws Exception {

		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		properties.put("mail.smtp.ssl.checkserveridentity", "false");
		properties.put("mail.smtp.ssl.protocols", "TLSv1.2");

		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(myAccountEmail, password);
			}
		});

		MimeMessage message = prepareMessage(session, myAccountEmail, recepient, subject, body);
		MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
		messageHelper.setFrom(myAccountEmail);
		messageHelper.setTo(recepient);
		messageHelper.setSubject(subject);
		messageHelper.setText(body, true);

		Transport.send(message);
		log.info("Message Sent successfully");

	}

//	public static void sendMail(String subject, String recepient, String body, FileSystemResource fileSystemResource,
//			String myAccountEmail, String password) throws Exception {
//		Properties properties = new Properties();
//		properties.put("mail.smtp.auth", "true");
//		properties.put("mail.smtp.starttls.enable", "true");
//		properties.put("mail.smtp.host", "smtp.gmail.com");
//		properties.put("mail.smtp.port", "587");
//
//		Session session = Session.getInstance(properties, new Authenticator() {
//			@Override
//			protected PasswordAuthentication getPasswordAuthentication() {
//				return new PasswordAuthentication(myAccountEmail, password);
//			}
//		});
//
//		MimeMessage message = prepareMessage(session, myAccountEmail, recepient, subject, body);
//		MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
//		messageHelper.setFrom(myAccountEmail);
//		messageHelper.setTo(recepient);
//		messageHelper.setSubject(subject);
//
//		if (fileSystemResource == null) {
//			messageHelper.setText(body, true);
//		} else {
//
//			messageHelper.setText(body, true);
//			messageHelper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);
//
//		}
//		Transport.send(message);
//		log.info("Message Sent successfully");
//
//	}

	private static MimeMessage prepareMessage(Session session, String from, String to, String subject, String body) {
		MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(from));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject);
			message.setText(body);
			return message;
		} catch (AddressException e) {
			log.error("Message not Sent to : {}", to);
			e.printStackTrace();
		} catch (Exception e) {
			log.error("Message not Sent to : {}", to);
			e.printStackTrace();
		}
		return null;
	}

}
