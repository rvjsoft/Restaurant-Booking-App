package com.rvj.app.foodorder.ops;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;

import com.rvj.app.foodorder.entity.Customer;
import com.rvj.app.foodorder.entity.Restaurant;
import com.rvj.app.foodorder.entity.enums.Status;
import com.rvj.app.foodorder.models.GetRestaurantResponse;
import com.rvj.app.foodorder.models.GetRestaurantsRequest;
import com.rvj.app.foodorder.services.CustomerService;
import com.rvj.app.foodorder.services.UIGetService;
import com.rvj.app.foodorder.utils.AppConstants;

public class GetRestaurantsOperation extends Operation<GetRestaurantsRequest, GetRestaurantResponse> {

	@Autowired
	CustomerService customerService;

	@Autowired
	UIGetService uiService;

	@Override
	protected boolean validate() {
		Customer customer = customerService.getCustomer(request.getUserName());
		if (customer == null) {
			this.getErrors().addError("customer", "customer does not exist");
		} else if (request.getAction().equals(AppConstants.RES_SINGLE)) {
			if (Objects.isNull(request.getResId())) {
				this.getErrors().addError("customer", "restaurant id is mandatory");
			}
			if (Objects.nonNull(request.getResName())) {
				this.getErrors().addError("customer", "restaurant name is not applicable");
			}
		} else if (request.getAction().equals(AppConstants.RES_LIST)) {
			if (Objects.nonNull(request.getResId())) {
				this.getErrors().addError("customer", "restaurant id is not applicable");
			}
		} else {
			this.getErrors().addError("customer", "request is invalid");
		}
		return this.getErrors().hasNoError();
	}

	@Override
	protected void process() {
		Restaurant restaurant = new Restaurant();
		restaurant.setStatus(Status.AVAILABLE);
		restaurant.setType(request.getType());
		restaurant.setId(request.getResId());
		restaurant.setBookings(null);
		restaurant.setFoods(null);
		restaurant.setOrders(null);
		restaurant.setTables(null);
		ExampleMatcher matcher = ExampleMatcher.matching();
		if (request.getAction().equals(AppConstants.RES_LIST) && Objects.nonNull(request.getResName())) {
			restaurant.setName(request.getResName());
			matcher = matcher.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		}
		Example<Restaurant> resExample = Example.of(restaurant, matcher);
		boolean status = false;
		status = uiService.getRestaurants(resExample, response);
		if(!status) {
			this.getErrors().addError("request", "request processing failed");
		}
	}

}
