/**
 * 
 */
package com.artcode.thirtyfifty.spring.security;

import org.springframework.security.core.GrantedAuthority;

//import org.springframework.security.core.GrantedAuthority;

/**
 * @author atulg
 *
 */
public enum Roles implements GrantedAuthority {
	ROLE_ADMIN, ROLE_USER,ROLE_STAFF;

	  public String getAuthority() {
	    return name();
	  }
}
