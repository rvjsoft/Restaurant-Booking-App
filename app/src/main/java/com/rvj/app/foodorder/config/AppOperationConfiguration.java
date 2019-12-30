package com.rvj.app.foodorder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.rvj.app.foodorder.models.AddAddressRequest;
import com.rvj.app.foodorder.models.AddAddressResponse;
import com.rvj.app.foodorder.models.AddFoodRequest;
import com.rvj.app.foodorder.models.AddFoodResponse;
import com.rvj.app.foodorder.models.DeleteAddressRequest;
import com.rvj.app.foodorder.models.DeleteAddressResponse;
import com.rvj.app.foodorder.models.DeleteFoodRequest;
import com.rvj.app.foodorder.models.DeleteFoodResponse;
import com.rvj.app.foodorder.models.RegisterUserRequest;
import com.rvj.app.foodorder.models.RegisterUserResponse;
import com.rvj.app.foodorder.models.UpdateAddressRequest;
import com.rvj.app.foodorder.models.UpdateAddressResponse;
import com.rvj.app.foodorder.models.UpdateFoodRequest;
import com.rvj.app.foodorder.models.UpdateFoodResponse;
import com.rvj.app.foodorder.ops.AddFoodOperation;
import com.rvj.app.foodorder.ops.AddressOperation;
import com.rvj.app.foodorder.ops.DeleteAddressOperation;
import com.rvj.app.foodorder.ops.DeleteFoodOperation;
import com.rvj.app.foodorder.ops.UpdateAddressOperaion;
import com.rvj.app.foodorder.ops.UpdateFoodOperation;
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
	
	@Bean
	@Scope("prototype")
	public AddressOperation getCustomerOperation(AddAddressRequest request) {
		AddressOperation operation = new AddressOperation();
		operation.setRequest(request);
		operation.setResponse(new AddAddressResponse());
		return operation;
	}
	
	@Bean
	@Scope("prototype")
	public UpdateAddressOperaion getUpdateAddressOperation(UpdateAddressRequest request) {
		UpdateAddressOperaion operation = new UpdateAddressOperaion();
		operation.setRequest(request);
		operation.setResponse(new UpdateAddressResponse());
		return operation;
	}
	
	@Bean
	@Scope("prototype")
	public DeleteAddressOperation getDeleteAddressOperation(DeleteAddressRequest request) {
		DeleteAddressOperation operation = new DeleteAddressOperation();
		operation.setRequest(request);
		operation.setResponse(new DeleteAddressResponse());
		return operation;
	}
	
	@Bean
	@Scope("prototype")
	public AddFoodOperation getAddFoodOperation(AddFoodRequest request) {
		AddFoodOperation operation = new AddFoodOperation();
		operation.setRequest(request);
		operation.setResponse(new AddFoodResponse());
		return operation;
	}
	
	@Bean
	@Scope("prototype")
	public UpdateFoodOperation getUpdateFoodOperation(UpdateFoodRequest request) {
		UpdateFoodOperation operation = new UpdateFoodOperation();
		operation.setRequest(request);
		operation.setResponse(new UpdateFoodResponse());
		return operation;
	}
	
	@Bean
	@Scope("prototype")
	public DeleteFoodOperation getDeleteFoodOperation(DeleteFoodRequest request) {
		DeleteFoodOperation operation = new DeleteFoodOperation();
		operation.setRequest(request);
		operation.setResponse(new DeleteFoodResponse());
		return operation;
	}
	
}
