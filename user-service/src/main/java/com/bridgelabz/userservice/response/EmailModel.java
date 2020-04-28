/**
 * @author Nayan Kumar G
 * purpose :Model of the mail
 * 
 */
package com.bridgelabz.userservice.response;
import java.io.Serializable;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class EmailModel implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private String email;
	private String subject;
	private String message;
	
}
