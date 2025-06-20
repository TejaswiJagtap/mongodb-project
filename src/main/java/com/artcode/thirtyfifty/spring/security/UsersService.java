package com.artcode.thirtyfifty.spring.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.artcode.thirtyfifty.user.User;
import com.artcode.thirtyfifty.user.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsersService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// fetching user from db

		User user = userRepository.findByEmailAndIsDeleted(username, false);
		// getAuthorities(user);
		if (user == null) {
			throw new UsernameNotFoundException("username not found");
		}
//		CustomUserDetails customUserDetails=new CustomUserDetails(user);
//		return customUserDetails;

		return new org.springframework.security.core.userdetails.User(username, user.getPassword(),
				getAuthorities(user));
	}

	private Set<GrantedAuthority> getAuthorities(User user) {
		Set<GrantedAuthority> authorities = new HashSet<>();
		authorities.add(new SimpleGrantedAuthority(user.getRole()));

		return authorities;
	}

}
