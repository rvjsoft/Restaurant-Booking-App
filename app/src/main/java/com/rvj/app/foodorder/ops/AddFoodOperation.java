package com.rvj.app.foodorder.ops;

import org.springframework.beans.factory.annotation.Autowired;

import com.rvj.app.foodorder.models.AddFoodRequest;
import com.rvj.app.foodorder.models.AddFoodResponse;
import com.rvj.app.foodorder.services.RestaurantService;

public class AddFoodOperation extends Operation<AddFoodRequest, AddFoodResponse> {

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
		status = restaurantService.addFoods(request, response);
		if(!status) {
			this.getErrors().addError("operationError", "error adding food");
		}
	}

}
