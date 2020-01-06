package com.rvj.app.foodorder.ops;

import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.rvj.app.dataaccess.UserRepository;
import com.rvj.app.foodorder.entity.Customer;
import com.rvj.app.foodorder.entity.Restaurant;
import com.rvj.app.foodorder.entity.User;
import com.rvj.app.foodorder.entity.enums.UserLevel;
import com.rvj.app.foodorder.models.RegisterUserRequest;
import com.rvj.app.foodorder.models.RegisterUserResponse;
import com.rvj.app.foodorder.services.UserRegistrationService;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserRegistrationOperation extends Operation<RegisterUserRequest, RegisterUserResponse> {

	@Autowired
	private UserRegistrationService userService;
	
	@Override
	protected boolean validate() {
		if(userService.isUserExist(request.getUserName())) {
			this.errors.addError("userName", "user already exist.");
			log.info("user already exist.");
		}else if(request.getCustomer() != null && request.getRestaurant() != null)
		{
			this.errors.addError("customerAndRestaurant", "can't create both restaurant and customer");
			log.info("validation error: can't create both restaurant and customer");
		} else {
			if(request.getCustomer() != null) {
				if(userService.isCustomerExistWithContact(request.getCustomer().getEmail(), request.getCustomer().getPhone())) {
					this.errors.addError("email_phone", "userAlready with same contact info email/phone");
					log.info("validation error: userAlready with same contact info email/phone");
				}
			} else if(request.getRestaurant() != null) {
				if(userService.isRestaurantExistWithContact(request.getRestaurant().getEmail(), request.getRestaurant().getPhone())) {
					this.errors.addError("userExists", "userAlready with same contact info email/phone");
					log.info("validation error: userAlready with same contact info email/phone");
				}
			}
		}
		return this.errors.hasNoError();
	}

	@Override
	@Transactional
	protected void process() {
		boolean status = false;
		if(request.getCustomer() != null) {
			status = userService.createCustomer(request);
		} else if (request.getRestaurant() != null) {
			status = userService.createRestaurant(request);
		}
		if(!status) {
			this.errors.addError("operationError", "Error creating the User.");
		}
	}

}
