package com.artcode.thirtyfifty.spring.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter{

	 	@Autowired
	    private JwtService jwtService; 
	  
	    @Autowired
	    private UsersService usersService; 
	  
	    @Override
		protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
				FilterChain filterChain) throws ServletException, IOException {
			String authHeader = request.getHeader("Authorization"); 
	        String token = null; 
	        String username = null; 
	        String role = null; 
	        if (authHeader != null && authHeader.startsWith("Bearer ")) { 
	            token = authHeader.substring(7); 
	            username = jwtService.extractUsername(token); 
	            role = jwtService.extractClaim(token, claims -> claims.get("roles", String.class));
	        
	        } 
	  
	        if (role !=null && username != null && SecurityContextHolder.getContext().getAuthentication() == null) { 
	            UserDetails userDetails = usersService.loadUserByUsername(username); 
	            if (jwtService.validateToken(token, userDetails)) { 
	                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); 
	                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); 
	                SecurityContextHolder.getContext().setAuthentication(authToken); 
	            } 
	        } 
	        filterChain.doFilter(request, response); 
			
		} 
	    
}
