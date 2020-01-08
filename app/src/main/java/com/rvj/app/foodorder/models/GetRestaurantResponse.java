package com.rvj.app.foodorder.models;

import java.util.ArrayList;
import java.util.List;

import com.rvj.app.foodorder.entity.Food;

import lombok.Data;

@Data
public class GetRestaurantResponse extends BaseResponse {
	List<RestaurantModel> restaurants = new ArrayList<RestaurantModel>();
	
	private List<FoodModel> foods = new ArrayList<FoodModel>();
}
