package com.artcode.thirtyfifty;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

	@Bean
	public CorsFilter corsFilter() {

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(false);

		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		config.addAllowedOrigin("http://localhost");
		config.addAllowedOrigin("http://localhost:4200");
		config.addAllowedOrigin("http://localhost:4300");
		config.addAllowedOrigin("http://192.168.1.35:3500");
		config.addAllowedOrigin("http://192.168.1.35:3600");
		config.addAllowedOrigin("http://192.168.1.27:4300");
		config.addAllowedOrigin("http://192.168.1.7:8080");
		config.addAllowedOrigin("http://localhost:4200/ws");
		config.addAllowedHeader("Access-Control-Allow-Origin");
		config.addAllowedHeader("Content-Type");
		config.addAllowedHeader("X-Requested-With");

		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}
	
}
