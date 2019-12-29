package com.rvj.app.foodorder.ops;

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
	private UserRegistrationService userServie;
	
	@Override
	protected boolean validate() {
		if(request.getCustomer() != null && request.getRestaurant() != null)
		{
			this.errors.addError("", "can't create both restaurant and customer");
			log.info("validation error: can't create both restaurant and customer");
		} else {
			if(request.getCustomer() != null) {
				if(userServie.isCustomerExistWithContact(request.getCustomer().getEmail(), request.getCustomer().getPhone())) {
					this.errors.addError("", "userAlready with same contact info email/phone");
					log.info("validation error: userAlready with same contact info email/phone");
				}
			} else if(request.getRestaurant() != null) {
				if(userServie.isRestaurantExistWithContact(request.getRestaurant().getEmail(), request.getRestaurant().getPhone())) {
					this.errors.addError("", "userAlready with same contact info email/phone");
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
			status = userServie.createCustomer(request);
		} else if (request.getRestaurant() != null) {
			status = userServie.createRestaurant(request);
		}
		if(!status) {
			this.errors.addError("", "Error creating the User.");
		}
	}

}
