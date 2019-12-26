package com.rvj.app.foodorder.models;

import com.rvj.app.foodorder.entity.enums.FoodType;

import lombok.Data;

@Data
public class RestaurantModel {
	private String name;
	private FoodType type;
	private AddressModel address;
}
