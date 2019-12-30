package com.rvj.app.foodorder.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.rvj.app.dataaccess.RestaurantRepository;
import com.rvj.app.foodorder.entity.Address;
import com.rvj.app.foodorder.entity.Food;
import com.rvj.app.foodorder.entity.Restaurant;
import com.rvj.app.foodorder.entity.enums.Status;
import com.rvj.app.foodorder.models.AddFoodRequest;
import com.rvj.app.foodorder.models.DeleteFoodRequest;
import com.rvj.app.foodorder.models.FoodModel;
import com.rvj.app.foodorder.models.UpdateFoodRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RestaurantService {

	@Autowired
	RestaurantRepository restaurantRepository;

	public Restaurant getRestaurant(String userName) {
		return restaurantRepository.findByUserName(userName);
	}

	public boolean isRestaurantExist(String userName) {
		return (getRestaurant(userName) != null);
	}

	@Transactional
	public boolean addFoods(AddFoodRequest request) {
		Restaurant restaurant= getRestaurant(request.getUserName());
		for(FoodModel food : request.getFoods()) {
			Food foodData = new Food();
			BeanUtils.copyProperties(food, foodData);
			foodData.setStatus(Status.UNAVAILABLE);
			restaurant.addFood(foodData);
		}
		try {
			restaurantRepository.save(restaurant);
		} catch (Exception e) {
			log.info("Caught exception while adding food, Exception:" + e.getMessage());
			return false;
		}
		return true;
	}

	@Transactional
	public boolean updateFood(UpdateFoodRequest request) {
		Restaurant restaurant= getRestaurant(request.getUserName());
		List<Food> foods = restaurant.getFoods().stream().filter(food -> food.getId().equals(request.getFoodId())).collect(Collectors.toList());
		if(CollectionUtils.isEmpty(foods)) {
			return false;
		}
		Food food = foods.get(0);
		BeanUtils.copyProperties(request.getFood(), food);
		try {
			restaurantRepository.save(restaurant);
		} catch (Exception e) {
			log.info("Caught exception while updating food, Exception:" + e.getMessage());
			return false;
		}
		return true;
	}

	public boolean deleteFood(DeleteFoodRequest request) {
		Restaurant restaurant= getRestaurant(request.getUserName());
		List<Food> foods = restaurant.getFoods().stream().filter(food -> food.getId().equals(request.getFoodId())).collect(Collectors.toList());
		if(CollectionUtils.isEmpty(foods)) {
			return false;
		}
		Food food = foods.get(0);
		restaurant.removeFood(food);
		try {
			restaurantRepository.save(restaurant);
		} catch (Exception e) {
			log.info("Caught exception while updating food, Exception:" + e.getMessage());
			return false;
		}
		return true;
	}

}
