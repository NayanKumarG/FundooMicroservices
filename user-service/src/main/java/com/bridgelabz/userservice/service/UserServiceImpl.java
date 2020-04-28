/**
 * @author Nayan Kumar G
 * purpose : Provide services for User
 * 
 */

package com.bridgelabz.userservice.service;
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.bridgelabz.userservice.dto.UpdatePasswordDto;
import com.bridgelabz.userservice.dto.UserDto;
import com.bridgelabz.userservice.dto.UserLoginDto;
import com.bridgelabz.userservice.entity.User;
import com.bridgelabz.userservice.exception.InvalidUserCredentialException;
import com.bridgelabz.userservice.exception.MailNotFoundException;
import com.bridgelabz.userservice.exception.UserAlreadyExistException;
import com.bridgelabz.userservice.exception.UserNotFoundException;
import com.bridgelabz.userservice.repository.RedisRepository;
import com.bridgelabz.userservice.repository.UserRepository;
import com.bridgelabz.userservice.response.EmailModel;
import com.bridgelabz.userservice.utility.EmailUtil;
import com.bridgelabz.userservice.utility.JwtUtil;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class UserServiceImpl implements UserService {


	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;   

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private EmailModel emailModel;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RedisRepository redisRepository;
	
	
	private User user = new User();

	/**
	 * method to register for user
	 */
	@Transactional
	@Override
	public User addUser(UserDto userDto) {


		if(!isMailExist(userDto.getEmail()))
		{
			BeanUtils.copyProperties(userDto , user);
			user.setDateTime(LocalDateTime.now());
			
			user.setPassword(bcryptPasswordEncoder.encode((userDto.getPassword())));
			user.setVerified(false);
			userRepository.save(user);
			//redisRepository.save(user);
			emailModel.setMessage(EmailUtil.createLink("http://localhost:8081/api/user-service/users/verifyMail/", jwtUtil.generateToken(user.getId())));
			emailModel.setEmail(userDto.getEmail());
			emailModel.setSubject("Click link to Verify ");
			EmailUtil.sendAttachmentEmail(emailModel.getEmail(), emailModel.getSubject(), emailModel.getMessage());
			//rabbitMQSender.send(emailModel);
			return user;
		}
		else

			throw new UserAlreadyExistException("User already exist with given mail:"+userDto.getEmail() , HttpStatus.NOT_FOUND );


	}

	/**
	 * @param userLoginDto object for login
	 * @return true if password match
	 */
	@Override
	public User verifyLogin(UserLoginDto userLoginDto) {
		String mail = userLoginDto.getEmail();
		User user = userRepository.findByMail(mail);
		if(user!=null)
		{
			if(bcryptPasswordEncoder.matches(userLoginDto.getPassword(), user.getPassword()))
			{
		
			if(user.isVerified()==true)
			{
				return user;
			}
			else
			{
				throw new UserNotFoundException("user not verified",HttpStatus.NOT_FOUND);
			}
			}
			else
			{
				throw new InvalidUserCredentialException("Incorrect Password" , HttpStatus.BAD_REQUEST);
		}
		}
		throw new UserNotFoundException("user not found with given email:"+userLoginDto.getEmail(),HttpStatus.NOT_FOUND);
	}

	/**
	 * @param token to get the userId
	 * @return true if update success
	 */
	@Transactional
	@Override
	public boolean updatePassword(UpdatePasswordDto updatePasswordDto, String token) {
		if(updatePasswordDto.getPassword().equals(updatePasswordDto.getConfirmPassword()))
		{
				long userId = jwtUtil.parseToken(token);
				log.info("my id:"+userId);
				User user = userRepository.findById(userId);
				log.info("user:"+user);
				if(user!=null)
				{
					log.info("update password:"+updatePasswordDto.getConfirmPassword());
					String password = bcryptPasswordEncoder.encode(updatePasswordDto.getConfirmPassword());
					return userRepository.updatePassword(password , userId);
		
				}
				else
				{
				throw new UserNotFoundException("User Not found!!!",HttpStatus.NOT_FOUND);
				}
			}
		
		else
			throw new InvalidUserCredentialException("Invalid credential!!" , HttpStatus.BAD_REQUEST);
		
	

	}
	/**
	 * 
	 * @param email_id to check user
	 * @return true if user exist
	 */
	@Transactional
	@Override
	public boolean isMailExist(String email_id)
	{
		
		
		return userRepository.isMailExist(email_id);


	}

	/**
	 * @param emailId to check user is present or not
	 * @return true if user present
	 */
	@Override
	public User confirmMail(String emailId) {

		User user = userRepository.findByMail(emailId);
		if(user!=null)
		{
			emailModel.setMessage("http://localhost:8081/user-service/updatePassword/"+jwtUtil.generateToken(user.getId()));
			emailModel.setEmail(emailId);
			emailModel.setSubject("click link to get new password");
			EmailUtil.sendAttachmentEmail(emailModel.getEmail(), emailModel.getSubject(), emailModel.getMessage());
			return user;
		}
		else
		{
		throw new UserNotFoundException("user not found with given email:"+emailId,HttpStatus.NOT_FOUND);
		}
	}
	/**
	 * @param token to find user
	 * @return true if user present
	 */
	@Transactional
	@Override
	public boolean updateMailVerification(String token) {
		long id = jwtUtil.parseToken(token); 
		User user = userRepository.findById(id);
		if(user!=null)
		{
		
			return userRepository.updateMailVerification(token, id);

		}
		throw new UserNotFoundException("user not found",HttpStatus.NOT_FOUND);

	}
	/**
	 * Get the list of users
	 */
	@Transactional
	@Override
	public List<User> getUsers() {
		
		List<User> users = userRepository.getUsers();
		users.sort((User user1 , User user2)->user1.getName().compareTo(user2.getName()));
		return users;
	}   

	/**
	 * get the user detail by userId.
	 */
	@Transactional
	@Override
	//@Cacheable(value = "user" , key = "#token")
	public User getUser(String token) {

		long userId = jwtUtil.parseToken(token);
		log.info("My id:"+userId);

		User user = userRepository.findById(userId);//redisRepository.findById(userId);

		if(user!=null)
		{
		return user;
		}
		else
		throw new UserNotFoundException("user Not found",HttpStatus.NOT_FOUND);
	}
	
	
}
