package com.rvj.app.foodorder.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CustomerModel {
	
	@NotEmpty(message = "firstName should Not be null/empty")
	@Pattern(regexp = "[a-zA-Z]*", message = "FirstName should be Alphabets")
	@Size(min = 5, max = 20, message = "firstName length should be from 5 to 20 characters")
	private String firstName;
	
	@NotEmpty(message = "lastName should Not be null/empty")
	@Pattern(regexp = "[a-zA-Z]*", message = "LastName should be Alphabets")
	@Size(min = 5, max = 20, message = "lastName length should be from 5 to 20 characters")
	private String lastName;
	
	@Email
	@NotEmpty(message = "email should Not be null/empty")
	@Size(min = 5, max = 30, message = "email length should be from 5 to 20 characters")
	private String email;
	
	@NotEmpty(message = "phone should Not be null/empty")
	@Size(min = 5, max = 15, message = "phone length should be from 5 to 15 characters")
	@Pattern(regexp = "[0-9]*", message = "phone number should be numeric")
	private String phone;
}
