/**
 * 
 */
package com.artcode.thirtyfifty.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;

@Component
public class UploadFile {

	@Autowired
	ServletContext context;

	@Autowired
	private Environment env;

	private static final SecureRandom random = new SecureRandom();

	public String uploadFile(MultipartFile file, String folder) throws IOException {
		String uploadFolder = env.getProperty("upload-path");
		if (file == null || file.isEmpty()) {
			throw new IllegalArgumentException("File must not be null or empty");
		}
		String originalFilename = file.getOriginalFilename();
		if (originalFilename == null || originalFilename.trim().isEmpty()) {
			throw new IllegalArgumentException("Original filename must not be null or empty");
		}
		String fileNumber = UUID.randomUUID().toString(); // Using UUID for generating a random file name
		String fileName = fileNumber + "_" + originalFilename.replaceAll("\\s", "");
		Path folderPath = Paths.get(uploadFolder, folder);
		if (!Files.exists(folderPath)) {
			Files.createDirectories(folderPath);
		}
		Path filePath = folderPath.resolve(fileName);
		Files.write(filePath, file.getBytes());
		String accessUrl = env.getProperty("access.file.url");
		return accessUrl + "/" + folder + "/" + fileName;
	}

	private char[] generateFileRandomNo(int len) {
		String numbers = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		char[] fileNumber = new char[len];
		for (int i = 0; i < len; i++) {
			fileNumber[i] = numbers.charAt(random.nextInt(numbers.length()));

		}
		System.out.print("Your fileNumber is : " + String.valueOf(fileNumber));
		return fileNumber;

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

	public boolean createDirectory(String filePath) {
		boolean flag = false;
		File file = new File(filePath);
		if (!file.exists()) {
			if (file.mkdir()) {
				System.out.println("Directory is created: " + file.getAbsolutePath());
				flag = true;
			} else {
				System.out.println("Failed to create directory: " + file.getAbsolutePath());
			}
		} else {
			System.out.println("Directory already exists: " + file.getAbsolutePath());
			flag = true;
		}

		return flag;
	}

}
