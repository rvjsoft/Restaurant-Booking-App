package com.rvj.app.foodorder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.rvj.app.foodorder.models.RegisterUserRequest;
import com.rvj.app.foodorder.models.RegisterUserResponse;
import com.rvj.app.foodorder.ops.UserRegistrationOperation;

@Configuration
public class AppOperationConfiguration {

	@Bean
	@Scope("prototype")
	public UserRegistrationOperation getUserRegistrationOperation(RegisterUserRequest request) {
		UserRegistrationOperation operation = new UserRegistrationOperation();
		operation.setRequest(request);
		operation.setResponse(new RegisterUserResponse());
		return operation;
	}
	
}
