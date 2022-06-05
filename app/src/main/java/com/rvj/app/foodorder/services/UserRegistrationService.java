package com.rvj.app.foodorder.services;

import com.rvj.app.foodorder.entity.User;
import com.rvj.app.foodorder.models.CustomerModel;
import com.rvj.app.foodorder.models.RestaurantModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.rvj.app.dataaccess.CustomerRepository;
import com.rvj.app.dataaccess.RestaurantRepository;
import com.rvj.app.dataaccess.UserRepository;
import com.rvj.app.foodorder.entity.AddressType;
import com.rvj.app.foodorder.entity.Customer;
import com.rvj.app.foodorder.entity.Restaurant;
import com.rvj.app.foodorder.entity.enums.Status;
import com.rvj.app.foodorder.entity.enums.UserLevel;
import com.rvj.app.foodorder.models.RegisterUserRequest;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import static com.rvj.app.foodorder.utils.AppConstants.CUSTOMER;
import static com.rvj.app.foodorder.utils.AppConstants.RESTAURANT;

@Slf4j
@Service
public class UserRegistrationService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CustomerRepository custormerRepository;
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	public boolean createCustomer(RegisterUserRequest request) {
		Customer user = new Customer();
		BeanUtils.copyProperties(request, user);
		BeanUtils.copyProperties(request.getCustomer(), user);
		user.setUserLevel(UserLevel.CUSTOMER);
		try {
			log.info("inserting user into database.");
			userRepository.save(user);
		} catch (Exception e) {
			log.info("caught exception while inserting the User, Exception:" + e.getMessage());
			return false;
		}
		return true;
	}

	public boolean createRestaurant(RegisterUserRequest request) {
		Restaurant user = new Restaurant();
		BeanUtils.copyProperties(request, user);
		BeanUtils.copyProperties(request.getRestaurant(), user);
		user.setAddress(new AddressType());
		BeanUtils.copyProperties(request.getRestaurant().getAddress(), user.getAddress());
		user.setUserLevel(UserLevel.RESTAURANT);
		user.setStatus(Status.UNAVAILABLE);
		try {
			log.info("inserting user into database.");
			userRepository.save(user);
		} catch (Exception e) {
			log.info("caught exception while inserting the User, Exception:" + e.getMessage());
			return false;
		}
		return true;
	}
	
	public boolean isUserExist(String userName) {
		return !userRepository.findByUserName(userName).isEmpty();
	}
	
	public boolean isCustomerExistWithContact(String email, String phone) {
		return !custormerRepository.findByEmailOrPhone(email, phone).isEmpty();
	}
	
	public boolean isRestaurantExistWithContact(String email, String phone) {
		return !restaurantRepository.findByEmailOrPhone(email, phone).isEmpty();
	}

	public void createUser(UserDetails user) {
		RegisterUserRequest registerUserRequest = new RegisterUserRequest();
		registerUserRequest.setUserName(user.getUsername());
		registerUserRequest.setPassword(user.getPassword());
		Optional<GrantedAuthority> authority = (Optional<GrantedAuthority>) user.getAuthorities().stream().findFirst();
		authority.ifPresent(grantedAuthority -> {
			switch (grantedAuthority.getAuthority()) {
				case CUSTOMER:
					registerUserRequest.setCustomer(new CustomerModel());
					createCustomer(registerUserRequest);
					break;
				case RESTAURANT:
					registerUserRequest.setRestaurant(new RestaurantModel());
					createRestaurant(registerUserRequest);
					break;
				default:
					break;
			}
		});
	}

	public Optional<User> loadUser(String username) {
		return userRepository.findByUserName(username).stream().findFirst();
	}
}
