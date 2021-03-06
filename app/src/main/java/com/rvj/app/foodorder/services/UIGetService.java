package com.rvj.app.foodorder.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.rvj.app.dataaccess.OrderRepository;
import com.rvj.app.dataaccess.RestaurantRepository;
import com.rvj.app.dataaccess.TableBookingRepository;
import com.rvj.app.foodorder.entity.Food;
import com.rvj.app.foodorder.entity.Order;
import com.rvj.app.foodorder.entity.OrderItem;
import com.rvj.app.foodorder.entity.Restaurant;
import com.rvj.app.foodorder.entity.TableBooking;
import com.rvj.app.foodorder.models.AddressModel;
import com.rvj.app.foodorder.models.FoodModel;
import com.rvj.app.foodorder.models.GetOrderResponse;
import com.rvj.app.foodorder.models.GetRestaurantResponse;
import com.rvj.app.foodorder.models.GetTableResponse;
import com.rvj.app.foodorder.models.OrderItemModel;
import com.rvj.app.foodorder.models.OrderModel;
import com.rvj.app.foodorder.models.RestaurantModel;
import com.rvj.app.foodorder.models.TableModel;
import com.rvj.app.foodorder.utils.AppConstants;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UIGetService {

	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	TableBookingRepository bookingRepository;
	
	@Autowired
	RestaurantRepository resRepo;
	
	@Autowired
	RestaurantService restaurantService;

	public boolean getOrders(Example<Order> orderExample, GetOrderResponse response, int page, int size) {
		List<Order> orders;
		try {
			Pageable pageRequest = PageRequest.of(page, size , Sort.by("orderedOn").descending());
			orders = orderRepository.findAll(orderExample, pageRequest).toList();
			response.setOrders(getOrderModel(orders));
		} catch (Exception e) {
			log.info("caught exception while processing request, Exception:" + e.getMessage());
			return false;
		}
		return true;
	}
	
	public boolean getTableBookings(Example<TableBooking> bookingExample, GetTableResponse response, int page, int size) {
		List<TableBooking> bookingList;
		try {
			Pageable pageRequest = PageRequest.of(page, size, Sort.by("bookingDate", "partOfDay").descending());
			bookingList = bookingRepository.findAll(bookingExample, pageRequest).toList();
			response.setTableBookings(getBookingModel(bookingList));
		} catch (Exception e) {
			log.info("caught exception while processing request, Exception:" + e.getMessage());
			return false;
		}
		return true;
	}

	private List<TableModel> getBookingModel(List<TableBooking> bookingList) {
		List<TableModel> tableModelList = new ArrayList<TableModel>();
		for(TableBooking booking : bookingList) {
			TableModel model = new TableModel();
			BeanUtils.copyProperties(booking, model);
			model.setCustomer(booking.getCustomer().getFullName());
			model.setRestaurant(booking.getRestaurant().getName());
			tableModelList.add(model);
		}
		return tableModelList;
	}

	private List<OrderModel> getOrderModel(List<Order> orders) {
		List<OrderModel> orderModelList = new ArrayList<OrderModel>();
		for (Order order : orders) {
			OrderModel orderModel = new OrderModel();
			BeanUtils.copyProperties(order, orderModel);
			orderModel.setCustomerName(order.getCustomer().getFullName());
			orderModel.setRestaurantName(order.getRestaurant().getName());
			List<OrderItemModel> itemsModel = new ArrayList<OrderItemModel>();
			for (OrderItem item : order.getItems()) {
				OrderItemModel itemModel = new OrderItemModel();
				itemModel.setId(item.getId());
				itemModel.setFood(item.getFood().getName());
				itemModel.setQuantity(item.getQuantity());
				itemsModel.add(itemModel);
			}
			orderModel.setFoodItems(itemsModel);
			orderModelList.add(orderModel);
		}
		return orderModelList;
	}

	public boolean getRestaurants(Example<Restaurant> resExample, GetRestaurantResponse response, String action, int page, int size) {
		List<Restaurant> restaurants;
		try {
			if(action.equalsIgnoreCase(AppConstants.RES_LIST)) {
				Pageable pageRequest = PageRequest.of(page, size);
				restaurants = resRepo.findAll(resExample, pageRequest).toList();
			} else {
				restaurants = resRepo.findAll(resExample);
			}
			response.setRestaurants(getRestaurantModels(restaurants));
			if(action.equalsIgnoreCase(AppConstants.RES_SINGLE) && !restaurants.isEmpty()) {
				response.setFoods(getFoodModel(restaurants.get(0)));
				response.setAvailability(restaurantService.getTableAvail(restaurants.get(0).getUserName()));
			}
		} catch (Exception e) {
			log.info("caught exception while processing request, Exception:" + e.getMessage());
			return false;
		}
		return true;
	}

	private List<FoodModel> getFoodModel(Restaurant restaurant) {
		List<FoodModel> foods = new ArrayList<FoodModel>();
		if(Objects.isNull(restaurant))
			return foods;
		for(Food food : restaurant.getFoods()) {
			FoodModel model = new FoodModel();
			BeanUtils.copyProperties(food, model);
			foods.add(model);
		}
		return foods;
	}

	private List<RestaurantModel> getRestaurantModels(List<Restaurant> restaurants) {
		List<RestaurantModel> restaurantList = new ArrayList<RestaurantModel>();
		for(Restaurant res : restaurants) {
			RestaurantModel resModel = new RestaurantModel();
			resModel.setAddress(new AddressModel());
			BeanUtils.copyProperties(res, resModel);
			BeanUtils.copyProperties(res.getAddress(), resModel.getAddress());
			restaurantList.add(resModel);
		}
		return restaurantList;
	}

}
