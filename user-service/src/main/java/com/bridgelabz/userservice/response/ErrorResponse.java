/**
 * @author Nayan Kumar G
 * purpose :Error response to exception
 * 
 */
package com.bridgelabz.userservice.response;


import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ErrorResponse {



	public ErrorResponse(String message, HttpStatus statusCode) {
		super();
		this.message = message;
		this.statusCode = statusCode;
	}

	public ErrorResponse() {
		// TODO Auto-generated constructor stub
	}

	private String message;
	   
	private HttpStatus statusCode;
	
	
}
