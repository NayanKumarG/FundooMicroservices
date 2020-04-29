package com.bridgelabz.userservice.response;

import lombok.Data;

@Data
public class UserResponse {

	private String message;
	private Object object;
	
	public UserResponse(String message, Object object) {
		super();
		this.message = message;
		this.object = object;
	}
}
