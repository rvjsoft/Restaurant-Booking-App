package com.rvj.app.foodorder.models;

import lombok.Data;

@Data
public class RegisterUserRequest {

	private String messageId;
	private String userName;
	private String password;
	
	private RestaurantModel restaurant;
	private CustomerModel customer;
}
