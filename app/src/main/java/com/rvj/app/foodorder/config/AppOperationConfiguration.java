package com.rvj.app.foodorder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.rvj.app.foodorder.models.AddAddressRequest;
import com.rvj.app.foodorder.models.AddAddressResponse;
import com.rvj.app.foodorder.models.AddFoodRequest;
import com.rvj.app.foodorder.models.AddFoodResponse;
import com.rvj.app.foodorder.models.BookTableRequest;
import com.rvj.app.foodorder.models.BookTableResponse;
import com.rvj.app.foodorder.models.DeleteAddressRequest;
import com.rvj.app.foodorder.models.DeleteAddressResponse;
import com.rvj.app.foodorder.models.DeleteFoodRequest;
import com.rvj.app.foodorder.models.DeleteFoodResponse;
import com.rvj.app.foodorder.models.FoodStatusRequest;
import com.rvj.app.foodorder.models.FoodStatusResponse;
import com.rvj.app.foodorder.models.GetOrderRequest;
import com.rvj.app.foodorder.models.GetOrderResponse;
import com.rvj.app.foodorder.models.GetRestaurantResponse;
import com.rvj.app.foodorder.models.GetRestaurantsRequest;
import com.rvj.app.foodorder.models.GetTableRequest;
import com.rvj.app.foodorder.models.GetTableResponse;
import com.rvj.app.foodorder.models.OrderFoodRequest;
import com.rvj.app.foodorder.models.OrderFoodResponse;
import com.rvj.app.foodorder.models.OrderStatusRequest;
import com.rvj.app.foodorder.models.OrderStatusResponse;
import com.rvj.app.foodorder.models.RegisterUserRequest;
import com.rvj.app.foodorder.models.RegisterUserResponse;
import com.rvj.app.foodorder.models.RestaurantStatusReqeust;
import com.rvj.app.foodorder.models.RestaurantStatusResponse;
import com.rvj.app.foodorder.models.RestaurantTableRequest;
import com.rvj.app.foodorder.models.RestaurantTableResponse;
import com.rvj.app.foodorder.models.TableAvailRequest;
import com.rvj.app.foodorder.models.TableAvailResponse;
import com.rvj.app.foodorder.models.UpdateAddressRequest;
import com.rvj.app.foodorder.models.UpdateAddressResponse;
import com.rvj.app.foodorder.models.UpdateFoodRequest;
import com.rvj.app.foodorder.models.UpdateFoodResponse;
import com.rvj.app.foodorder.ops.AddFoodOperation;
import com.rvj.app.foodorder.ops.AddressOperation;
import com.rvj.app.foodorder.ops.BookTableOperation;
import com.rvj.app.foodorder.ops.DeleteAddressOperation;
import com.rvj.app.foodorder.ops.DeleteFoodOperation;
import com.rvj.app.foodorder.ops.FoodStatusOperation;
import com.rvj.app.foodorder.ops.GetOrderOperation;
import com.rvj.app.foodorder.ops.GetRestaurantsOperation;
import com.rvj.app.foodorder.ops.GetTableAvailOperation;
import com.rvj.app.foodorder.ops.GetTableOperation;
import com.rvj.app.foodorder.ops.OrderFoodOperation;
import com.rvj.app.foodorder.ops.OrderStatusOperation;
import com.rvj.app.foodorder.ops.RestaurantStatusOperation;
import com.rvj.app.foodorder.ops.RestaurantTableOperation;
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
	
	@Bean
	@Scope("prototype")
	public RestaurantStatusOperation getRestaurantStatusOperation(RestaurantStatusReqeust request) {
		RestaurantStatusOperation operation = new RestaurantStatusOperation();
		operation.setRequest(request);
		operation.setResponse(new RestaurantStatusResponse());
		return operation;
	}
	
	@Bean
	@Scope("prototype")
	public FoodStatusOperation getFoodStatusOperation(FoodStatusRequest request) {
		FoodStatusOperation operation = new FoodStatusOperation();
		operation.setRequest(request);
		operation.setResponse(new FoodStatusResponse());
		return operation;
	}
	
	@Bean
	@Scope("prototype")
	public RestaurantTableOperation getRestaurantTableOperation(RestaurantTableRequest request) {
		RestaurantTableOperation operation = new RestaurantTableOperation();
		operation.setRequest(request);
		operation.setResponse(new RestaurantTableResponse());
		return operation;
	}
	
	@Bean
	@Scope("prototype")
	public OrderFoodOperation getOrderFoodOperation(OrderFoodRequest request) {
		OrderFoodOperation operation = new OrderFoodOperation();
		operation.setRequest(request);
		operation.setResponse(new OrderFoodResponse());
		return operation;
	}
	
	@Bean
	@Scope("prototype")
	public BookTableOperation getBookTableOperation(BookTableRequest request) {
		BookTableOperation operation = new BookTableOperation();
		operation.setRequest(request);
		operation.setResponse(new BookTableResponse());
		return operation;
	}
	
	@Bean
	@Scope("prototype")
	public OrderStatusOperation getOrderStatusOperation(OrderStatusRequest request) {
		OrderStatusOperation operation = new OrderStatusOperation();
		operation.setRequest(request);
		operation.setResponse(new OrderStatusResponse());
		return operation;
	}
	
	@Bean
	@Scope("prototype")
	public GetOrderOperation getGetOrderOperation(GetOrderRequest request) {
		GetOrderOperation operation = new GetOrderOperation();
		operation.setRequest(request);
		operation.setResponse(new GetOrderResponse());
		return operation;
	}
	
	@Bean
	@Scope("prototype")
	public GetTableOperation getGetTableOperation(GetTableRequest request) {
		GetTableOperation operation = new GetTableOperation();
		operation.setRequest(request);
		operation.setResponse(new GetTableResponse());
		return operation;
	}
	
	@Bean
	@Scope("prototype")
	public GetRestaurantsOperation getGetRestaurantsOperation(GetRestaurantsRequest request) {
		GetRestaurantsOperation operation = new GetRestaurantsOperation();
		operation.setRequest(request);
		operation.setResponse(new GetRestaurantResponse());
		return operation;
	}
	
	@Bean
	@Scope("prototype")
	public GetTableAvailOperation getGetTableAvailOperation(TableAvailRequest request) {
		GetTableAvailOperation operation = new GetTableAvailOperation();
		operation.setRequest(request);
		operation.setResponse(new TableAvailResponse());
		return operation;
	}
	
}
