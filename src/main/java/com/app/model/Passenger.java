package com.app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Passenger {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@NotNull
	private String passengerName;
	
	@NotNull
	private String gender;
	
	@NotNull
	private Integer age;
	
	@Email
	@NotNull
	private String email;
	
	@Pattern(regexp = "[a-zA-Z0-9]{6,12}" , message = "Mobile number is not proper")
	@NotNull
	private String mobile;
	
	
	

}
