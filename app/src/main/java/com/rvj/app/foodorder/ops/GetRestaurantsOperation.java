package com.rvj.app.foodorder.ops;

import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;

import com.rvj.app.foodorder.entity.Customer;
import com.rvj.app.foodorder.entity.Restaurant;
import com.rvj.app.foodorder.entity.User;
import com.rvj.app.foodorder.entity.enums.Status;
import com.rvj.app.foodorder.entity.enums.UserLevel;
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
	
	@Autowired
	HttpSession session;

	@Override
	protected boolean validate() {
		/*
		 * Customer customer = customerService.getCustomer(request.getUserName()); if
		 * (customer == null) { this.getErrors().addError("customer",
		 * "customer does not exist"); } else
		 */
		if(request.getUserLevel().equals(UserLevel.RESTAURANT))
		{
//			request.setUserName((String) session.getAttribute(AppConstants.APP_USER));
			User user = customerService.getUser(request.getUserName());
			if(Objects.nonNull(user))
				request.setResId(user.getId());
		}
		if (request.getAction().equals(AppConstants.RES_SINGLE)) {
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
		restaurant.setStatus(request.getStatus());
		restaurant.setType(request.getType());
		restaurant.setId(request.getResId());
		restaurant.setBookings(null);
		restaurant.setFoods(null);
		restaurant.setOrders(null);
		restaurant.setTables(null);
		ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues();
		if (request.getAction().equals(AppConstants.RES_LIST) && Objects.nonNull(request.getResName())) {
			restaurant.setName(request.getResName());
			matcher = matcher.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		}
		Example<Restaurant> resExample = Example.of(restaurant, matcher);
		boolean status = false;
		if(request.getPage() == null || request.getSize() == null) {
			request.setPage(0);
			request.setSize(20);
		}
		status = uiService.getRestaurants(resExample, response, request.getAction(), request.getPage(), request.getSize());
		if (!status) {
			this.getErrors().addError("request", "request processing failed");
		}
	}

}
