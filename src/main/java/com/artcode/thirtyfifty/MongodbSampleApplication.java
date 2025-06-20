package com.artcode.thirtyfifty;

import java.util.Date;
import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@OpenAPIDefinition
@EnableScheduling
@Slf4j
@ComponentScan(basePackages = "com.artcode.thirtyfifty")
public class MongodbSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(MongodbSampleApplication.class, args);
	}

	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata")); // It will set IST Time-Zone Asia/Kolkata
		log.info("Spring boot application running in Asia/Kolkata timezone :" + new Date()); // It will print
																										// UTC
	}

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		return mapper;
	}
}
