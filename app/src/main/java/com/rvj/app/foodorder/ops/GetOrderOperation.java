package com.rvj.app.foodorder.ops;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import com.rvj.app.foodorder.entity.Customer;
import com.rvj.app.foodorder.entity.Order;
import com.rvj.app.foodorder.entity.Restaurant;
import com.rvj.app.foodorder.entity.enums.UserLevel;
import com.rvj.app.foodorder.models.GetOrderRequest;
import com.rvj.app.foodorder.models.GetOrderResponse;
import com.rvj.app.foodorder.services.CustomerService;
import com.rvj.app.foodorder.services.RestaurantService;
import com.rvj.app.foodorder.services.UIGetService;

public class GetOrderOperation extends Operation<GetOrderRequest, GetOrderResponse> {

	@Autowired
	CustomerService customerService;

	@Autowired
	RestaurantService restaurantService;
	
	@Autowired
	UIGetService uiService;

	private Customer customer;
	private Restaurant restaurant;

	@Override
	protected boolean validate() {
		if (request.getUserLevel() == null) {
			this.getErrors().addError("userLevel", "processing error");
		} else if (request.getUserLevel().equals(UserLevel.CUSTOMER)) {
			customer = customerService.getCustomer(request.getUserName());
			if (Objects.isNull(customer)) {
				this.getErrors().addError("userName", "User doesn't exists");
			} else if (Objects.nonNull(request.getResId()) && !customerService.isRestaurantExist(request.getResId())) {
				this.getErrors().addError("userName", "Retaurant doesn't exists");
			}
		} else if (request.getUserLevel().equals(UserLevel.RESTAURANT)) {
			restaurant = restaurantService.getRestaurant(request.getUserName());
			if (Objects.isNull(restaurant)) {
				this.getErrors().addError("userName", "User doesn't exists");
			} else if (Objects.nonNull(request.getCustId())
					&& !restaurantService.isCustomerExist(request.getCustId())) {
				this.getErrors().addError("userName", "Retaurant doesn't exists");
			}
		}
		return this.getErrors().hasNoError();
	}

	@Override
	protected void process() {
		Order order = new Order();
		order.setCustomer(customer);
		order.setRestaurant(restaurant);
		order.setStatus(request.getStatus());
		order.setItems(null);
		ExampleMatcher orderMatcher = ExampleMatcher.matching().withIgnoreNullValues();
		Example<Order> orderExample = Example.of(order, orderMatcher);
		
		boolean status = false;
		status = uiService.getOrders(orderExample, response);
		if(!status) {
			this.getErrors().addError("request", "error while getting orders list");
		}
	}

}
