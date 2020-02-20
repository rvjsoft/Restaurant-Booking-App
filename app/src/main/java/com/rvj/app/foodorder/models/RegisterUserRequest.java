package com.rvj.app.foodorder.models;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class RegisterUserRequest extends BaseRequest{

	@NotEmpty(message = "UserName should Not be null/empty")
	@Size(min = 5, max = 20, message = "UserName length should be from 5 to 20 characters")
	private String userName;
	
	@NotEmpty(message = "Password should Not be null/empty")
	@Size(min = 4, max = 10, message = "Password length should be from 4 to 10 characters")
	private String password;
	
	@Valid
	private RestaurantModel restaurant;
	
	@Valid
	private CustomerModel customer;
	
}
