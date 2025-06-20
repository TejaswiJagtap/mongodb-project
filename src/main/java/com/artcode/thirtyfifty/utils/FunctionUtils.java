package com.artcode.thirtyfifty.utils;

import java.security.SecureRandom;

import org.springframework.stereotype.Component;

@Component
public class FunctionUtils {

	private static final SecureRandom RANDOM = new SecureRandom();

	public static String generateOtp(int len) {
		String numbers = "1234567890";
		char[] otp = new char[len];
		for (int i = 0; i < len; i++) {
			otp[i] = numbers.charAt(RANDOM.nextInt(numbers.length()));
		}
		return String.valueOf(otp);
	}

	public static String emailMsg(String otp) {
		String msg = "<html><div style=\"font-family: Helvetica, Arial, sans-serif; min-width: 1000px; overflow: auto; line-height: 2;\">\r\n"
				+ "  <div style=\"margin: 50px auto; width: 70%; padding: 20px 0;\">\r\n"
				+ "    <div style=\"border-bottom: 1px solid #eee;\">\r\n"
				+ "      <a href=\"\" style=\"font-size: 1.4em; color: #00466a; text-decoration: none; font-weight: 600;\">Marathi School</a>\r\n"
				+ "    </div>\r\n" + "    <p style=\"font-size: 1.1em;\">Hi " + ",</p>\r\n"
				+ "    <p>Thank you for choosing ArtCode.</p>\r\n"
				+ "	   <p>Use the following Otp to complete your Sign Up procedures:</p>\r\n"
				+ "    <h2 style=\"background: #00466a; margin: 0 auto; width: max-content; padding: 0 10px; color: #fff; border-radius: 4px;\">"
				+ otp + "</h2>\r\n" + "    <p style=\"font-size: 0.9em;\">Regards,<br />Marathi School</p>\r\n"
				+ "    <hr style=\"border: none; border-top: 1px solid #eee;\" />\r\n"
				+ "    <div style=\"float: right; padding: 8px 0; color: #aaa; font-size: 0.8em; line-height: 1; font-weight: 300;\">\r\n"
				+ "      <p>ArtCode. Inc</p>\r\n" + "    </div>\r\n" + "  </div>\r\n" + "</div></html>";
		return msg;
	}

	public static String optForEmailVerification(String otp) {
		String msg = "<html><div style=\"font-family: Helvetica, Arial, sans-serif; min-width: 1000px; overflow: auto; line-height: 2;\">\r\n"
				+ "  <div style=\"margin: 50px auto; width: 70%; padding: 20px 0;\">\r\n"
				+ "    <div style=\"border-bottom: 1px solid #eee;\">\r\n"
				+ "      <a href=\"\" style=\"font-size: 1.4em; color: #00466a; text-decoration: none; font-weight: 600;\">Marathi School</a>\r\n"
				+ "    </div>\r\n" + "    <p style=\"font-size: 1.1em;\">Hi " + ",</p>\r\n"
				+ "    <p>Thank you for choosing Marathi School</p>\r\n"
				+ "	   <p>Use the following Otp to complete your Email verification procedures:</p>\r\n"
				+ "    <h2 style=\"background: #00466a; margin: 0 auto; width: max-content; padding: 0 10px; color: #fff; border-radius: 4px;\">"
				+ otp + "</h2>\r\n" + "    <p style=\"font-size: 0.9em;\">Regards,<br />Marathi School</p>\r\n"
				+ "    <hr style=\"border: none; border-top: 1px solid #eee;\" />\r\n"
				+ "    <div style=\"float: right; padding: 8px 0; color: #aaa; font-size: 0.8em; line-height: 1; font-weight: 300;\">\r\n"
				+ "      <p>Marathi School</p>\r\n" + "    </div>\r\n" + "  </div>\r\n" + "</div></html>";
		return msg;
	}

	public String generateUserNameOrPassword(int len) {
		String numbers = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		char[] otp = new char[len];
		for (int i = 0; i < len; i++) {
			otp[i] = numbers.charAt(RANDOM.nextInt(numbers.length()));
		}
		return String.valueOf(otp);
	}

	public static String emailText(String text) {
		String msg = "<html>"
				+ "<div style=\"font-family: Helvetica,Arial,sans-serif;min-width:1000px;overflow:auto;line-height:2\">\r\n"
				+ "  <p style=\"font-size:1.1em\">Hi,</p>\r\n" + "     <p style=\"font-size:1.1em\">" + text
				+ "</p>\r\n" + "    <p style=\"font-size:0.9em;\">Regards,<br/>Artcode School</p>\r\n"
				+ "   <hr style=\"border:none;border-top:1px solid #eee\" />\r\n" + "  </div>\r\n" + "</div></html>";
		return msg;
	}
}
