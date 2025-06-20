package com.artcode.thirtyfifty;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.artcode.thirtyfifty.constants.StatusConstant;
import com.artcode.thirtyfifty.spring.security.PasswordUtil;
import com.artcode.thirtyfifty.user.User;
import com.artcode.thirtyfifty.user.UserRepository;

@Component
public class InitialDataSaver implements ApplicationRunner{

	@Override
	public void run(ApplicationArguments args) throws Exception {
		createAdministrator();
		createAdmin();
	}

	@Autowired
	private UserRepository userRepository;

	private void createAdmin() {

		List<User> users = userRepository.findByRoleAndIsDeleted(StatusConstant.USER_TYPE_ADMIN.value(),false);
		if (users == null || users.isEmpty()) {
			User user = new User();
			user.setFirstName("Admin");
			user.setEmail("admin@gmail.com");
			user.setRole(StatusConstant.USER_TYPE_ADMIN.value());
			user.setPassword(PasswordUtil.getPasswordHash("12345"));
			userRepository.save(user);

		}
	}
	
	private void createAdministrator() {
		List<User> users = userRepository.findByRoleAndIsDeleted(StatusConstant.USER_TYPE_ADMINISTRATOR.value(),false);
		if (users == null || users.isEmpty()) {
			User user = new User();
			user.setFirstName("Administrator");
			user.setEmail("administrator@gmail.com");
			user.setRole(StatusConstant.USER_TYPE_ADMINISTRATOR.value());
			user.setPassword(PasswordUtil.getPasswordHash("12345"));

			userRepository.save(user);

		}
	}

}
