package com.rvj.app.foodorder.services;

import java.util.ArrayList;
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
import com.rvj.app.foodorder.models.FoodStatusRequest;
import com.rvj.app.foodorder.models.FoodStatusResponse;
import com.rvj.app.foodorder.models.RestaurantStatusReqeust;
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

	public boolean changeStatus(RestaurantStatusReqeust request) {
		Restaurant restaurant= getRestaurant(request.getUserName());
		restaurant.setStatus(request.getStatus());
		try {
			restaurantRepository.save(restaurant);
		} catch (Exception e) {
			log.info("Caught exception while changing restaurant status, Exception:" + e.getMessage());
			return false;
		}
		return true;
	}

	public boolean updateFoodStatus(FoodStatusRequest request, FoodStatusResponse response) {
		Restaurant restaurant= getRestaurant(request.getUserName());
		List<Food> foods = restaurant.getFoods();
		List<Long> errorFoodIds = new ArrayList<Long>();
		List<Long> availableFoods = request.getAvailable();
		List<Long> unAvailableFoods = request.getUnavailable();
		/*
		 * for(Long foodId : availableFoods) { Food food = null; for(Food foodData :
		 * foods) { if(foodData.getId().equals(foodId)) { food = foodData; break; } }
		 * if(food != null) { food.setStatus(Status.AVAILABLE); } else {
		 * errorFoodIds.add(foodId); } }
		 * 
		 * for(Long foodId : unAvailableFoods) { Food food = null; for(Food foodData :
		 * foods) { if(foodData.getId().equals(foodId)) { food = foodData; break; } }
		 * if(food != null) { food.setStatus(Status.UNAVAILABLE); } else {
		 * errorFoodIds.add(foodId); } }
		 */
		
		for(Food foodData : foods) {
			Long tempId = null;
			log.info("updating available foods");
			for(Long id : availableFoods) {
				if(foodData.getId().equals(id)) {
					foodData.setStatus(Status.AVAILABLE);
					tempId = id;
					break;
				}
			}
			if(tempId != null) {
				availableFoods.remove(tempId);
				tempId = null;
			}
			log.info("updating Unavailable foods");
			for(Long id : unAvailableFoods) {
				if(foodData.getId().equals(id)) {
					foodData.setStatus(Status.UNAVAILABLE);
					tempId = id;
					break;
				}
			}
			if(tempId != null) {
				unAvailableFoods.remove(tempId);
				tempId = null;
			}
		}
		
		try {
			restaurantRepository.save(restaurant);
		} catch (Exception e) {
			log.info("Caught exception while changing food status, Exception:" + e.getMessage());
			return false;
		}
		
		errorFoodIds.addAll(availableFoods);
		errorFoodIds.addAll(unAvailableFoods);
		response.setErrorFoodIds(errorFoodIds);
		return true;
	}

}
