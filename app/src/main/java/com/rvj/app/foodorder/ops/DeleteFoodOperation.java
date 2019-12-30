package com.rvj.app.foodorder.ops;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.rvj.app.foodorder.entity.Food;
import com.rvj.app.foodorder.entity.Restaurant;
import com.rvj.app.foodorder.models.DeleteFoodRequest;
import com.rvj.app.foodorder.models.DeleteFoodResponse;
import com.rvj.app.foodorder.services.RestaurantService;

public class DeleteFoodOperation extends Operation<DeleteFoodRequest, DeleteFoodResponse>{

	@Autowired
	RestaurantService restaurantService;
	
	@Override
	protected boolean validate() {
		Restaurant restaurant = restaurantService.getRestaurant(request.getUserName());
		if(restaurant == null) {
			this.getErrors().addError("userName", "the restaurant does not exist");
		} else {
			List<Food> foods = restaurant.getFoods().stream().filter(food -> food.getId().equals(request.getFoodId())).collect(Collectors.toList());
			if(CollectionUtils.isEmpty(foods)) {
				this.getErrors().addError("foodId", "the restaurant does not have the mentioned foodItem");
			}
		}
		return this.getErrors().hasNoError();
	}

	@Override
	protected void process() {
		boolean status = false;
		status = restaurantService.deleteFood(request);
		if(!status) {
			this.getErrors().addError("operationError", "error deleting food");
		}
	}

}
