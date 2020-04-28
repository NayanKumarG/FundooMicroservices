/**
 * @author Nayan Kumar G
 * purpose : Dto for user login
 * 
 */
package com.bridgelabz.userservice.dto;
import org.springframework.stereotype.Component;
import lombok.Data;

@Data
@Component
public class UserLoginDto {

	private String email;
	private String password;
	
}
