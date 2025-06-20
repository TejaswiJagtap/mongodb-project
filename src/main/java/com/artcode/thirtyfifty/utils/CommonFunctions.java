/**
 * 
 */
package com.artcode.thirtyfifty.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author kamlesh pawar
 *
 */
@Component
public class CommonFunctions {

	@Value("${jwt.header}")
	private String tokenHeader;

//	@Autowired
//	private AuthenticationManager authenticationManager;

//	@Autowired
//	private OpdSmsSubscriptionRepository opdSmsSubscriptionRepository;

//	@Autowired
//	private SendSms sendSms;

	private static final SecureRandom RANDOM = new SecureRandom();

	public String generateOtp() {
		String numbers = "1234567890";
		int len = 6;
		// Using random method
		char[] otp = new char[len];
		for (int i = 0; i < len; i++) {
			otp[i] = numbers.charAt(RANDOM.nextInt(numbers.length()));
		}
		return String.valueOf(otp);
	}

	public String generateOtpTemp() {
		String numbers = "1111111111";
		int len = 4;
		char[] otp = new char[len];
		for (int i = 0; i < len; i++) {
			otp[i] = numbers.charAt(RANDOM.nextInt(numbers.length()));
		}
		return String.valueOf(otp);
	}

	public String generatePassword() {
		String numbers = "1234567890";
//		String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
//		String lower = upper.toLowerCase();
		String alphaNum = numbers;
		int len = 8;
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
//			otp[i] = numbers.charAt(rndm_method.nextInt(alphaNum.length()));
			sb.append(alphaNum.charAt(RANDOM.nextInt(alphaNum.length())));
		}
		String password = sb.toString();
		return password;
	}

	public static boolean createFolder(String FILE) {
		boolean flag = false;
		File file = new File(FILE);
		if (!file.exists()) {
			if (file.mkdir()) {
				System.out.println("Directory is created!");
				flag = true;
			} else {
				System.out.println("Failed to create directory!");
			}
		} else {
			flag = true;
		}
		return flag;
	}

	public static Double roundDouble(Double value) {
		return Double.valueOf(new DecimalFormat("0.00").format(value));
	}

	public static String encodeFileToBase64(File file) {
		try {
			byte[] fileContent = Files.readAllBytes(file.toPath());
			return Base64.getEncoder().encodeToString(fileContent);
		} catch (IOException e) {
			throw new IllegalStateException("could not read file " + file, e);
		}
	}
}
