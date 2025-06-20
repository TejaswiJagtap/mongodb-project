package com.artcode.thirtyfifty.spring.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

	@Autowired
	private UsersService userDetailService;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http,JwtAuthFilter authFilter) throws Exception {
		http.csrf((csrf) -> csrf.disable()).authorizeHttpRequests((authz) -> authz
				.requestMatchers("/user/signup","/user/forgot-password","/user/vertify-otp","/user/login","/email-setting").permitAll()
				.requestMatchers("/user/**").authenticated()
				.requestMatchers("/admin/**").authenticated()
				.requestMatchers("/refresh-token**").permitAll()
				.requestMatchers("/users/**").permitAll()
				.requestMatchers("/swagger-ui.html").permitAll()
				.requestMatchers("/swagger-ui/index.html").permitAll()
				.requestMatchers("/swagger-ui/**").permitAll()
				.requestMatchers("/swagger-config").permitAll()
				.requestMatchers("/user/login").permitAll()
				.requestMatchers("/user/refreshtoken").permitAll()
				.requestMatchers("/swagger-resources/**").permitAll()
				.requestMatchers("/v2/api-docs").permitAll()
				.requestMatchers("/v3/api-docs/**").permitAll()
				.requestMatchers("/webjars/**").permitAll()
				.requestMatchers("/user/vertify-otp**").permitAll()
				.requestMatchers("/public").permitAll()
				// Disallow everything else..
				.anyRequest().authenticated());
//		.formLogin(formLogin -> formLogin
//                .loginPage("/login") // Specify your login page URL
//                .permitAll()
//            );

		// If a user try to access a resource without having enough permissions
		http.exceptionHandling(excp -> excp.accessDeniedPage("/login"));

		http.sessionManagement(session -> {
			try {
				session.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
						.authenticationProvider(authenticationProvider())
						.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		return http.build();
	}

	public WebSecurityCustomizer webSecurityCustomizer() throws Exception {
		// Allow swagger to be accessed without authentication
		return (web) -> web.ignoring()
				// Allow swagger to be accessed without authentication
				.requestMatchers("/v2/api-docs")//
				.requestMatchers("/swagger-resources/**")//
				.requestMatchers("/swagger-ui.html")//
				.requestMatchers("/swagger-ui/**")
				.requestMatchers("/swagger-ui/index.html")
				.requestMatchers("/configuration/**")//
				.requestMatchers("/webjars/**")//
				.requestMatchers("/user/login").requestMatchers("/user/get-user-status")
				.requestMatchers("/user/update-password").requestMatchers("/user/signup")
				.requestMatchers("/actuator/**").requestMatchers("/user/forgot-password")
				.requestMatchers("/user/forgot/set-new-password").requestMatchers("/user/send-otp")
				.requestMatchers("/user//vertify-otp").requestMatchers("/user/refreshtoken")
				.requestMatchers("/user/forgot/verify-otp")
				.requestMatchers("/v3/api-docs/**").requestMatchers("/v3/api-docs/swagger-config")
				.requestMatchers("/by-code")

				// Un-secure H2 Database (for testing purposes, H2 console shouldn't be
				// unprotected in production)
				.and().ignoring().requestMatchers("/h2-console/**/**");
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() throws IOException {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailService);
		authProvider.setPasswordEncoder(passwordEncoder()); // Use your password encoder here
		return authProvider;
	}

	@Bean
	public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

}
