package com.rvj.app.foodorder.ops;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rvj.app.foodorder.entity.Customer;
import com.rvj.app.foodorder.models.OrderFoodRequest;
import com.rvj.app.foodorder.models.OrderFoodResponse;
import com.rvj.app.foodorder.services.CustomerService;

@Component
public class OrderFoodOperation extends Operation<OrderFoodRequest, OrderFoodResponse>{

	@Autowired
	CustomerService customerService;
	
	@Override
	protected boolean validate() {
		
		Customer customer = customerService.getCustomer(request.getUserName());
		if(customer == null) {
			this.getErrors().addError("customer", "customer does not exist");
		} 
		if(!customerService.isRestaurantExist(request.getResId())) {
			this.getErrors().addError("resId", "Restaurant does not exist");
		} else if(!customerService.isValidRestaurantFoods(request, response)) {
			this.getErrors().addError("foods", "foods are not available in restaurant");
		} else {
			Optional<Integer> invalidQuantity = request.getFoods().values().stream().filter(val -> (val<1 || val>10)).findAny();
			if(invalidQuantity.isPresent()) {
				this.getErrors().addError("quantity", "quantity should be between 1 to 10");
			}
		}
		return this.getErrors().hasNoError();
	}

	@Override
	protected void process() {
		boolean status = false;
		status = customerService.orderFood(request);
		if(!status) {
			this.getErrors().addError("operationError", "error ordering food");
		}
	}

}
