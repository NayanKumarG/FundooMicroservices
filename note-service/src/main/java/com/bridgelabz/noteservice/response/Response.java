/**
 * @author Nayan Kumar G
 * purpose :Class to create response
 * date    :28-02-2020
 */
package com.bridgelabz.noteservice.response;
import lombok.Data;


@Data
public class Response {

	private String message;
	private Object object;
	private String token;
	public Response(String message, Object object) {
		super();
		this.message = message;
		this.object = object;
	}

	public Response(String message) {
	
		this.message = message;
	}
	
	public Response(String message , String token) {
		
		this.message = message;
		this.token = token;
	}
	
public Response(String message , String token , Object object ) {
		
		this.message = message;
		this.token = token;
		this.object = object;
	}
}
