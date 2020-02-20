package com.rvj.app.foodorder.ops;

import org.springframework.beans.factory.annotation.Autowired;

import com.rvj.app.foodorder.models.OrderStatusRequest;
import com.rvj.app.foodorder.models.OrderStatusResponse;
import com.rvj.app.foodorder.services.RestaurantService;

public class OrderStatusOperation extends Operation<OrderStatusRequest, OrderStatusResponse> {

	@Autowired
	RestaurantService restaurantService;
	
	@Override
	protected boolean validate() {
		
		if(!restaurantService.isRestaurantExist(request.getUserName())) {
			this.getErrors().addError("username", "the restaurant does not exist");
		} else if(!restaurantService.isOrderExist(request.getOrderId())) {
			this.getErrors().addError("orderId", "the order does not exist");
		}
		
		return this.getErrors().hasNoError();
	}

	@Override
	protected void process() {
		boolean status = false;
		status = restaurantService.updateOrderStatus(request);
		if(!status) {
			this.getErrors().addError("operationError", "error updating order status");
		}
	}

}
