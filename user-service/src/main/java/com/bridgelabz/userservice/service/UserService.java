/**
 * @author Nayan Kumar G
 * purpose : Interface for users Service
 * 
 */
package com.bridgelabz.userservice.service;
import java.util.List;
import com.bridgelabz.userservice.dto.UpdatePasswordDto;
import com.bridgelabz.userservice.dto.UserDto;
import com.bridgelabz.userservice.dto.UserLoginDto;
import com.bridgelabz.userservice.entity.User;


public interface UserService {
	
	User addUser(UserDto userDto);
	
	boolean updateMailVerification(String token);
	
	User verifyLogin(UserLoginDto userLoginDto);
	
	boolean isMailExist(String email);
	
	User confirmMail(String emailId);
	
	boolean updatePassword(UpdatePasswordDto updatePasswordDto, String token);
	
	List<User> getUsers();
	
	User getUser(String token);
	

	
}
