package com.artcode.thirtyfifty.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;
import org.springframework.stereotype.Component;

@Component
public class CaptchaUtil {

	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	private static final int WIDTH = 150;
	private static final int HEIGHT = 50;
	private static final Font FONT = new Font("Arial", Font.PLAIN, 24);
	private static final SecureRandom random = new SecureRandom();

	public String generateCaptchaText(int captchaLength) {
		StringBuilder captchaText = new StringBuilder(captchaLength);

		for (int i = 0; i < captchaLength; i++) {
			captchaText.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
		}

		return captchaText.toString();
	}

	public BufferedImage generateCaptchaImage(String captchaText) {
		BufferedImage captchaImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = captchaImage.createGraphics();

		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, WIDTH, HEIGHT);
		g2d.setFont(FONT);
		g2d.setColor(Color.BLACK);
		g2d.drawString(captchaText, 20, 35);

		g2d.dispose();
		return captchaImage;
	}
}
