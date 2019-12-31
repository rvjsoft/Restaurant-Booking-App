package com.rvj.app.foodorder.ops;

import org.springframework.beans.factory.annotation.Autowired;

import com.rvj.app.foodorder.models.RestaurantStatusReqeust;
import com.rvj.app.foodorder.models.RestaurantStatusResponse;
import com.rvj.app.foodorder.services.RestaurantService;

public class RestaurantStatusOperation extends Operation<RestaurantStatusReqeust, RestaurantStatusResponse> {

	@Autowired
	RestaurantService restaurantService;
	
	@Override
	protected boolean validate() {

		if(!restaurantService.isRestaurantExist(request.getUserName())) {
			this.getErrors().addError("username", "the restaurant does not exist");
		}
		
		return this.getErrors().hasNoError();
	}

	@Override
	protected void process() {
		boolean status = false;
		status = restaurantService.changeStatus(request);
		if(!status) {
			this.getErrors().addError("operationError", "error changing restaurant status");
		}
	}

}
