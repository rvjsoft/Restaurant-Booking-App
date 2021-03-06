package com.rvj.app.foodorder.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.rvj.app.dataaccess.FoodRepository;
import com.rvj.app.dataaccess.OrderRepository;
import com.rvj.app.dataaccess.RestaurantRepository;
import com.rvj.app.dataaccess.TableAvailRepository;
import com.rvj.app.dataaccess.UserRepository;
import com.rvj.app.foodorder.entity.Food;
import com.rvj.app.foodorder.entity.Order;
import com.rvj.app.foodorder.entity.Restaurant;
import com.rvj.app.foodorder.entity.Tables;
import com.rvj.app.foodorder.entity.enums.PartOfDay;
import com.rvj.app.foodorder.entity.enums.Status;
import com.rvj.app.foodorder.entity.enums.UserLevel;
import com.rvj.app.foodorder.models.AddFoodRequest;
import com.rvj.app.foodorder.models.AddFoodResponse;
import com.rvj.app.foodorder.models.DeleteFoodRequest;
import com.rvj.app.foodorder.models.FoodModel;
import com.rvj.app.foodorder.models.FoodStatusRequest;
import com.rvj.app.foodorder.models.FoodStatusResponse;
import com.rvj.app.foodorder.models.OrderStatusRequest;
import com.rvj.app.foodorder.models.RestaurantStatusReqeust;
import com.rvj.app.foodorder.models.RestaurantTableRequest;
import com.rvj.app.foodorder.models.TableAvailModel;
import com.rvj.app.foodorder.models.TableAvailRequest;
import com.rvj.app.foodorder.models.TableAvailResponse;
import com.rvj.app.foodorder.models.UpdateFoodRequest;
import com.rvj.app.foodorder.ops.GetTableAvailOperation;
import com.rvj.app.foodorder.utils.AppConstants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RestaurantService {

	@Autowired
	RestaurantRepository restaurantRepository;

	@Autowired
	TableAvailRepository tableAvailRepository;

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	FoodRepository foodRepository;

	public Restaurant getRestaurant(String userName) {
		return restaurantRepository.findByUserName(userName);
	}

	public boolean isRestaurantExist(String userName) {
		return (getRestaurant(userName) != null);
	}

	@Transactional
	public boolean addFoods(AddFoodRequest request, AddFoodResponse response) {
		Restaurant restaurant = getRestaurant(request.getUserName());
		try {
			for (FoodModel food : request.getFoods()) {
				Food foodData = new Food();
				BeanUtils.copyProperties(food, foodData);
				foodData.setStatus(Status.UNAVAILABLE);
				restaurant.addFood(foodData);
				foodData = foodRepository.save(foodData);
				response.getIds().add(foodData.getId());
			}
		} catch (Exception e) {
			log.info("Caught exception while adding food, Exception:" + e.getMessage());
			return false;
		}
		return true;
	}

	@Transactional
	public boolean updateFood(UpdateFoodRequest request) {
		Restaurant restaurant = getRestaurant(request.getUserName());
		List<Food> foods = restaurant.getFoods().stream().filter(food -> food.getId().equals(request.getFoodId()))
				.collect(Collectors.toList());
		if (CollectionUtils.isEmpty(foods)) {
			return false;
		}
		Food food = foods.get(0);
		BeanUtils.copyProperties(request.getFood(), food, "id", "status");
		try {
			restaurantRepository.save(restaurant);
		} catch (Exception e) {
			log.info("Caught exception while updating food, Exception:" + e.getMessage());
			return false;
		}
		return true;
	}

	public boolean deleteFood(DeleteFoodRequest request) {
		Restaurant restaurant = getRestaurant(request.getUserName());
		List<Food> foods = restaurant.getFoods().stream().filter(food -> food.getId().equals(request.getFoodId()))
				.collect(Collectors.toList());
		if (CollectionUtils.isEmpty(foods)) {
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
		Restaurant restaurant = getRestaurant(request.getUserName());
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
		Restaurant restaurant = getRestaurant(request.getUserName());
		List<Food> foods = restaurant.getFoods();
		List<Long> errorFoodIds = new ArrayList<Long>();
		List<Long> availableFoods = request.getAvailable();
		List<Long> unAvailableFoods = request.getUnavailable();
		for (Food foodData : foods) {
			Long tempId = null;
			log.info("updating available foods");
			for (Long id : availableFoods) {
				if (foodData.getId().equals(id)) {
					foodData.setStatus(Status.AVAILABLE);
					tempId = id;
					break;
				}
			}
			if (tempId != null) {
				availableFoods.remove(tempId);
				tempId = null;
			}
			log.info("updating Unavailable foods");
			for (Long id : unAvailableFoods) {
				if (foodData.getId().equals(id)) {
					foodData.setStatus(Status.UNAVAILABLE);
					tempId = id;
					break;
				}
			}
			if (tempId != null) {
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

	public boolean changeTablesCount(RestaurantTableRequest request) {
		Restaurant restaurant = getRestaurant(request.getUserName());
		if (request.getAction().equals(AppConstants.RES_TABLE_ACTION)) {
			restaurant.setTableCount(request.getBaseCount());
		} else if (request.getAction().equals(AppConstants.DAY_TABLE_ACTION)) {
			if (request.getPart() == null) {
				List<Tables> tableList = restaurant.getTables().stream()
						.filter(tableData -> tableData.getBookedOn().equals(request.getDate()))
						.collect(Collectors.toList());
				if (CollectionUtils.isEmpty(tableList)) {
					for (PartOfDay part : PartOfDay.values()) {
						Tables tableData = new Tables();
						tableData.setBookedOn(request.getDate());
						tableData.setBookedTables(0);
						tableData.setTotal(request.getTableCount());
						tableData.setPart(part);
						restaurant.addTables(tableData);
					}
				} else {
					tableList.forEach(data -> data.setTotal(request.getTableCount()));
				}
			} else {

				List<Tables> tableList = restaurant.getTables().stream()
						.filter(tableData -> tableData.getBookedOn().equals(request.getDate()))
						.collect(Collectors.toList());
				if (CollectionUtils.isEmpty(tableList)) {
					for (PartOfDay part : PartOfDay.values()) {
						Tables tableData = new Tables();
						tableData.setBookedOn(request.getDate());
						tableData.setBookedTables(0);
						if (part.equals(request.getPart()))
							tableData.setTotal(request.getTableCount());
						else
							tableData.setTotal(0);
						tableData.setPart(part);
						restaurant.addTables(tableData);
					}
				} else {
					Optional<Tables> tables = restaurant.getTables().stream()
							.filter(data -> (data.getBookedOn().equals(request.getDate())
									&& data.getPart().equals(request.getPart())))
							.findFirst();
					if (tables.isPresent()) {
						tables.get().setTotal(request.getTableCount());
					}
				}
			}

		}
		try {
			restaurantRepository.save(restaurant);
		} catch (Exception e) {
			log.info("Caught exception while changing table count, Exception:" + e.getMessage());
			return false;
		}

		return true;
	}

	public boolean updateOrderStatus(OrderStatusRequest request) {
		Optional<Order> order = orderRepository.findById(request.getOrderId());
		if (order.isPresent()) {
			order.get().setStatus(request.getStatus());
		} else {
			return false;
		}
		try {
			orderRepository.save(order.get());
		} catch (Exception e) {
			log.info("Caught exception while changing order status, Exception:" + e.getMessage());
			return false;
		}
		return true;
	}

	public boolean isOrderExist(Long orderId) {
		return orderRepository.findById(orderId).isPresent();
	}

	public boolean isCustomerExist(Long custId) {
		return userRepository.findByIdAndUserLevel(custId, UserLevel.CUSTOMER).isPresent();
	}

	public boolean getTableAvailability(TableAvailRequest request, TableAvailResponse response) {
		Restaurant restaurant = restaurantRepository.findByUserName(request.getUserName());
		response.setBaseCount(restaurant.getTableCount());
		response.setAvailability(getTableAvail(request.getUserName()));
		return true;
	}
	
	public Map<LocalDate, Map<PartOfDay, TableAvailModel>> getTableAvail(String restaName){
		Restaurant restaurant = restaurantRepository.findByUserName(restaName);
		List<Tables> tables = tableAvailRepository.findByRestaurantAndBookedOnGreaterThan(restaurant, LocalDate.now().minusDays(1l));
		Map<LocalDate, Map<PartOfDay, TableAvailModel>> responseMap = new HashMap<LocalDate, Map<PartOfDay, TableAvailModel>>();
		for(int i=0; i<7;i++) {
			LocalDate date = LocalDate.now().plusDays(i);
			Map<PartOfDay, TableAvailModel> models= new HashMap<PartOfDay, TableAvailModel>();
			tables.stream().filter(data -> data.getBookedOn().equals(date)).forEach(data -> {
				TableAvailModel model = new TableAvailModel();
				model.setBookedOn(date);
				model.setTotal(data.getTotal());
				model.setBookedTables(data.getBookedTables());
				models.put(data.getPart(), model);
			});
			responseMap.put(date, models);
		}
		return responseMap;
	}

	public void updateImage(String userName, String imageId) {
		Restaurant restaurant = restaurantRepository.findByUserName(userName);
		restaurant.setImageId(imageId);
		try {
			restaurantRepository.save(restaurant);
		} catch (Exception e) {
			
		}
	}
	
	public void updateFoodImage(String userName, String foodId, String imageId) {
		Restaurant restaurant = restaurantRepository.findByUserName(userName);
		try {
			Optional<Food> food = restaurant.getFoods().stream().filter(data -> data.getId().toString().equals(foodId)).findFirst();
			if(food.isPresent()) {
				food.get().setImageId(imageId);
			}
			restaurantRepository.save(restaurant);
		} catch (Exception e) {
			
		}
	}

	public boolean isSameFood(FoodModel foodModel, Food food) {
		if (StringUtils.equalsIgnoreCase(foodModel.getName(), food.getName())
				&& foodModel.getPrice().equals(food.getPrice()) && foodModel.getType().equals(food.getType())
				&& foodModel.getCategory().equals(food.getCategory())
				&& StringUtils.equalsIgnoreCase(foodModel.getImageId(), food.getImageId())) {
			return true;
		}
		else
			return false;
	}

}
