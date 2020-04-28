/**
 * @author Nayan Kumar G
 * purpose :Dto to update password
 * 
 */
package com.bridgelabz.userservice.dto;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class UpdatePasswordDto {

	private String password;
	private String confirmPassword;
	
}
