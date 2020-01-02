package com.rvj.app.foodorder.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.rvj.app.dataaccess.CustomerRepository;
import com.rvj.app.dataaccess.FoodRepository;
import com.rvj.app.dataaccess.OrderRepository;
import com.rvj.app.dataaccess.RestaurantRepository;
import com.rvj.app.dataaccess.UserRepository;
import com.rvj.app.foodorder.entity.Address;
import com.rvj.app.foodorder.entity.AddressType;
import com.rvj.app.foodorder.entity.Customer;
import com.rvj.app.foodorder.entity.Order;
import com.rvj.app.foodorder.entity.OrderItem;
import com.rvj.app.foodorder.entity.User;
import com.rvj.app.foodorder.entity.enums.OrderStatus;
import com.rvj.app.foodorder.entity.enums.Status;
import com.rvj.app.foodorder.entity.enums.UserLevel;
import com.rvj.app.foodorder.models.AddAddressRequest;
import com.rvj.app.foodorder.models.AddressModel;
import com.rvj.app.foodorder.models.DeleteAddressRequest;
import com.rvj.app.foodorder.models.OrderFoodRequest;
import com.rvj.app.foodorder.models.OrderFoodResponse;
import com.rvj.app.foodorder.models.UpdateAddressRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerService {
	
	@Autowired
	CustomerRepository customerRepository; 
	
	@Autowired
	FoodRepository foodRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RestaurantRepository restaurantRepository;
	
	@Autowired
	OrderRepository orderRepository;
	
	public Customer getCustomer(String userName){
		return customerRepository.findByUserName(userName);
	}
	
	@Transactional
	public boolean addAddresses(AddAddressRequest request) {
		Customer customer= getCustomer(request.getUserName());
		for(AddressModel address : request.getAddresses()) {
			Address userAddress = new Address();
			AddressType  addressType = new AddressType();
			BeanUtils.copyProperties(address, addressType);
			userAddress.setAddress(addressType);
			customer.addAddress(userAddress);
		}
		try {
			customerRepository.save(customer);
		} catch (Exception e) {
			log.info("Caught exception while adding address");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Transactional
	public boolean updateAddresses(UpdateAddressRequest request) {
		Customer customer= getCustomer(request.getUserName());
		List<Address> addresses = customer.getAddresses().stream().filter(address -> address.getId().equals(request.getAddressId())).collect(Collectors.toList());
		if(CollectionUtils.isEmpty(addresses)) {
			return false;
		}
		Address address = addresses.get(0);
		BeanUtils.copyProperties(request.getAddress(), address.getAddress());
		try {
			customerRepository.save(customer);
		} catch (Exception e) {
			log.info("Caught exception while updating address");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Transactional
	public boolean deleteAddresses(DeleteAddressRequest request) {
		Customer customer= getCustomer(request.getUserName());
		List<Address> addresses = customer.getAddresses().stream().filter(address -> address.getId().equals(request.getAddressId())).collect(Collectors.toList());
		if(CollectionUtils.isEmpty(addresses)) {
			return false;
		}
		Address address = addresses.get(0);
		customer.removeAddress(address);
		try {
			customerRepository.save(customer);
		} catch (Exception e) {
			log.info("Caught exception while deleting address");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean isValidRestaurantFoods(OrderFoodRequest request, OrderFoodResponse response) {
		Set<Long> foodList = request.getFoods().keySet();
		List<Long> availableFoods = foodRepository.getFoods(request.getResId(), foodList, Status.AVAILABLE);
		if(availableFoods.size() != (request.getFoods().size())) {
			foodList.removeAll(availableFoods);
			response.setErrorFoodIds(foodList);
			return false;
		}
		else {
			return true;
		}
	}

	public boolean isRestaurantExist(Long resId) {
		Optional<User> user = userRepository.findByIdAndUserLevel(resId, UserLevel.RESTAURANT);
		return user.isPresent();
	}

	public boolean orderFood(OrderFoodRequest request) {
		Order order = new Order();
		List<OrderItem> items = new ArrayList<OrderItem>();
		HashMap<Long, Integer> foods = request.getFoods();
		order.setCustomer(getCustomer(request.getUserName()));
		order.setRestaurant(restaurantRepository.findById(request.getResId()).get());
		order.setOrderedOn(LocalDateTime.now());
		order.setStatus(OrderStatus.ORDERED);
		for(Long foodId : foods.keySet()) {
			OrderItem item = new OrderItem();
			item.setFood(foodRepository.findById(foodId).get());
			item.setQuantity(foods.get(foodId));
			order.addItem(item);
		}
		try {
			orderRepository.save(order);
		} catch(Exception e) {
			log.info("Error ordering food");
			return false;
		}
		return true;
	}
	
}
