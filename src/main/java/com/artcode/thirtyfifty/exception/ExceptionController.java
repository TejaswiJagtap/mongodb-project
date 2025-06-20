package com.artcode.thirtyfifty.exception;

import java.io.EOFException;
import java.io.IOException;

import org.apache.catalina.connector.ClientAbortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.artcode.thirtyfifty.utils.JsonUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class ExceptionController {

	public static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

	@Autowired
	private JsonUtils utils;

	@Autowired
	private DefaultErrorAttributes defaultExceptionResolver;

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<CustomErrorResponse> customNotFoundAdvice(final CustomException customNotFoundException,
			HttpServletRequest request) {
		CustomErrorResponse errorResponse = new CustomErrorResponse();
		errorResponse.setHttpStatus(customNotFoundException.getHttpStatus());
		errorResponse.setMessage(customNotFoundException.getMessage());
		logger.error(customNotFoundException.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<CustomErrorResponse> exceptionAdvice(final Exception exception, HttpServletRequest request) {
		CustomErrorResponse errorResponse = new CustomErrorResponse();
		errorResponse.setMessage(exception.getMessage());
		logger.error("Request url : {}", request != null ? request.getRequestURL() : "");
		logger.error("Exception", exception);
//		exception.printStackTrace();
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(CustomMessageApp.class)
	public final ResponseEntity<String> handleUserMessages(CustomMessageApp ex, WebRequest request) {
		return new ResponseEntity<String>(utils.objectMapperError(ex.getMessage()), HttpStatus.OK);

	}

	@ExceptionHandler(ClientAbortException.class)
	public ResponseEntity<CustomErrorResponse> checkForAbort(HttpServletRequest request, HttpServletResponse response,
			RuntimeException exception) {
		if (exception.getCause() instanceof EOFException) {
			ClientAbortException abortException = new ClientAbortException(exception.getCause());
			abortException.addSuppressed(exception);
			try {
				response.getOutputStream().close();
			} catch (IOException ex) {
				abortException.addSuppressed(ex);
			}

			defaultExceptionResolver.resolveException(request, response, null, abortException);
		}
//	    else {
//	        throw exception;
//	    }
		CustomErrorResponse errorResponse = new CustomErrorResponse();
		errorResponse.setMessage("Request Cancelled");
		logger.error(exception.getMessage());
		exception.printStackTrace();
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
