package com.rvj.app.foodorder.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class LoginRequest {
	
	@NotEmpty(message = "messageId should Not be null/empty")
	@Size(min = 5, max = 20, message = "messageId length should be from 5 to 20 characters")
	private String messageId;
	
	@NotEmpty(message = "UserName should Not be null/empty")
	@Size(min = 5, max = 20, message = "UserName length should be from 5 to 20 characters")
	private String userName;
	
	@NotEmpty(message = "Password should Not be null/empty")
	@Size(min = 4, max = 10, message = "Password length should be from 4 to 10 characters")
	private String password;
	
}
