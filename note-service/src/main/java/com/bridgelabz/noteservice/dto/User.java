/**
 * @author Nayan Kumar G
 * purpose : Entity for User
 * 
 */
package com.bridgelabz.noteservice.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Component
public class User{

	private long id;
	
	private String name;

	private String email;
	
	private String password;
	
	private long phoneNumber;

	private LocalDateTime dateTime;
	
	private LocalDateTime updateDateTime;

	private boolean isVerified;
	
}
