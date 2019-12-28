package com.rvj.app.foodorder.models;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.rvj.app.foodorder.entity.enums.FoodType;

import lombok.Data;

@Data
public class RestaurantModel {
	
	@NotEmpty(message = "Name should Not be null/empty")
	@Size(min = 5, max = 20, message = "Name length should be from 5 to 20 characters")
	private String name;
	private FoodType type;
	
	@Valid
	@NotEmpty(message = "address is mandatory")
	private AddressModel address;
}
