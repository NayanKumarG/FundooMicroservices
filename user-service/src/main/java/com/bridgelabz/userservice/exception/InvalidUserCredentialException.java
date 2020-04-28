/**
 * @author Nayan Kumar G
 * purpose :To throw invalid user credential exception
 * 
 */
package com.bridgelabz.userservice.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
@Getter
public class InvalidUserCredentialException extends RuntimeException{


	private static final long serialVersionUID = 1L;
	private String message;
	private HttpStatus statusCode;
	public InvalidUserCredentialException(String message, HttpStatus statusCode) {
		super();
		this.message = message;
		this.statusCode = statusCode;
	}
}
