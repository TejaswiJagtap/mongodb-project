/**
 * 
 */
package com.artcode.thirtyfifty.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomErrorResponse {

	private String httpStatus;
	
	private String message;
	
	
}
