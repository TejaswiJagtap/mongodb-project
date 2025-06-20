package com.artcode.thirtyfifty.spring.security;

import java.io.IOException;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CsrfHeaderFilter extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		CsrfToken csrfToken=(CsrfToken)request.getAttribute(CsrfToken.class.getName());
		if(csrfToken!=null) {
			Cookie cookie=WebUtils.getCookie(request, "XSRF-TOKEN");
			String token=csrfToken.getToken();
			if(cookie==null || token!=null && !token.equals(cookie.getValue())) {
				cookie=new Cookie("XSRF-TOKEN", token);
				cookie.setPath("/");
				response.addCookie(cookie);
				
			}
		}
		filterChain.doFilter(request, response);
	}

}
