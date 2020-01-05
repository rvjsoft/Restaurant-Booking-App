package com.rvj.app.foodorder.ops;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

import com.rvj.app.foodorder.entity.Customer;
import com.rvj.app.foodorder.entity.Restaurant;
import com.rvj.app.foodorder.entity.TableBooking;
import com.rvj.app.foodorder.entity.enums.UserLevel;
import com.rvj.app.foodorder.models.GetTableRequest;
import com.rvj.app.foodorder.models.GetTableResponse;
import com.rvj.app.foodorder.services.CustomerService;
import com.rvj.app.foodorder.services.RestaurantService;
import com.rvj.app.foodorder.services.UIGetService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class GetTableOperation extends Operation<GetTableRequest, GetTableResponse>{

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
		boolean status = false;
		TableBooking booking = new TableBooking();
		booking.setCustomer(customer);
		booking.setRestaurant(restaurant);
		ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues();
		Example<TableBooking> bookingExample = Example.of(booking, matcher);
		status = uiService.getTableBookings(bookingExample, response);
		if(!status) {
			log.info("error while getting table booking list");
			this.getErrors().addError("request", "error getting table booking list");
		}
	}

}
