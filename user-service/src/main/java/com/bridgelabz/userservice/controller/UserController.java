/**
 * @author Nayan Kumar G
 * purpose : Controller for Users
 * 
 */
package com.bridgelabz.userservice.controller;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.bridgelabz.userservice.dto.ForgotPasswordDto;
import com.bridgelabz.userservice.dto.UpdatePasswordDto;
import com.bridgelabz.userservice.dto.UserDto;
import com.bridgelabz.userservice.dto.UserLoginDto;
import com.bridgelabz.userservice.entity.User;
import com.bridgelabz.userservice.response.Response;
import com.bridgelabz.userservice.service.UserService;
import com.bridgelabz.userservice.utility.JwtUtil;

import lombok.extern.slf4j.Slf4j;

import org.springframework.cache.annotation.Cacheable;

@Slf4j
@RestController
@CrossOrigin("*")
public class UserController {

	@Autowired
	private UserService userService;

	
	@Autowired
	private JwtUtil jwtUtil;
	/**
	 * Api for user registration
	 * @param userDto creates object request by user
	 * @return response for user registration
	 */
	@PostMapping("/users/register")
	public ResponseEntity<Response> userRegistration(@Valid @RequestBody UserDto userDto)
	{

		User user = userService.addUser(userDto);
		if(user!=null)
			return ResponseEntity.status(HttpStatus.OK).body(new Response("Registration success" ,userDto));
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Registration not success" ));

	}

	/**
	 * Api to user login
	 * @param userLogindto creates object request by user
	 * @return response of user login
	 */
	@PostMapping("/users/login")
	public ResponseEntity<Response> userLogin(@RequestBody UserLoginDto userLoginDto)
	{

		User user = userService.verifyLogin(userLoginDto);
		
		String token = jwtUtil.generateToken(user.getId());
		log.info("token:"+token);

		return ResponseEntity.status(HttpStatus.OK).body(new Response("Login Success" , token , user));

		
	}
	/**
	 * Api to verify mail
	 * @param token taken by the url to verify mail
	 * @return response of verification
	 */
	@PutMapping("/users/verifyMail/{token}")
	public ResponseEntity<Response> userVerification(@PathVariable String token)
	{
		if(userService.updateMailVerification(token)) 

			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("verification success"));

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Verification failed:"));

	}


	/**
	 * Api for forgot password
	 * @param email taken from url
	 * @return response of user verification
	 */
	@PostMapping("/users/forgotPassword")
	public ResponseEntity<Response> forgotPassword(@RequestBody ForgotPasswordDto forgotPasswordDto)
	{
		User user = userService.confirmMail(forgotPasswordDto.getEmail());


		return ResponseEntity.status(HttpStatus.OK).body(new Response("Check mail to verify"));


	}

	/**
	 * Api for update password
	 * @param updatePasswordDto creates object for updatePasswordDto
	 * @param token taken from url
	 * @return response for password updation
	 */
	@PutMapping("/users/updatePassword/{token}")
	public ResponseEntity<Response> updatePassword( @RequestBody UpdatePasswordDto updatePasswordDto , @PathVariable("token") String token){

		if(userService.updatePassword(updatePasswordDto , token))

			return ResponseEntity.status(HttpStatus.OK).body(new Response("Password updated"));


		return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(new Response("Confirm mail verification"));	

	}


	/**
	 * Api to get perticular user
	 * @param userId given by the user to get the detail
	 * @return returns user object
	 */
	//@Cacheable(value = "user" , key = "#token")
	@GetMapping("/users/getUser/{token}")
	public ResponseEntity<Response> getUser(@PathVariable String token)
	{

		User user = userService.getUser(token);


		if(user!=null)

		return ResponseEntity.status(HttpStatus.OK).body(new Response("User found" , user));
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("User Not found"));
	}

	/**
	 * Api to get all users
	 * @return list of users
	 */
	@GetMapping("/users/getUsers")
	public ResponseEntity<Response> getUsers()
	{
		List<User> user = userService.getUsers();
		return ResponseEntity.status(HttpStatus.OK).body(new Response("users found" , user));
	}

	
}

