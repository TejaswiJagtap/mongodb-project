package com.artcode.thirtyfifty;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.artcode.thirtyfifty.user.UserService;

@SpringBootTest
class MongodbSampleApplicationTests {

	@Autowired
	private UserService userService; // Replace with any existing service or component in your application

	@Test
	void contextLoads() {
		// Assert that the application context loads and the bean is not null
		assertNotNull(userService, "The service should have been auto-wired and not be null");
	}

}
