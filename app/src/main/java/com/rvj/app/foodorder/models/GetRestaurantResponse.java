package com.rvj.app.foodorder.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rvj.app.foodorder.entity.Food;
import com.rvj.app.foodorder.entity.enums.PartOfDay;

import lombok.Data;

@Data
public class GetRestaurantResponse extends BaseResponse {
	
	List<RestaurantModel> restaurants = new ArrayList<RestaurantModel>();
	
	private List<FoodModel> foods = new ArrayList<FoodModel>();
	
	private Map<LocalDate, Map<PartOfDay, TableAvailModel>> availability = new HashMap<LocalDate, Map<PartOfDay,TableAvailModel>>();
	
}
