package com.rvj.app.foodorder.ops;

import org.springframework.beans.factory.annotation.Autowired;

import com.rvj.app.foodorder.models.FoodStatusRequest;
import com.rvj.app.foodorder.models.FoodStatusResponse;
import com.rvj.app.foodorder.services.RestaurantService;

public class FoodStatusOperation extends Operation<FoodStatusRequest, FoodStatusResponse> {

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
		status = restaurantService.updateFoodStatus(request, response);
		if(!status) {
			this.getErrors().addError("operationError", "error updating food");
		}
	}

}
