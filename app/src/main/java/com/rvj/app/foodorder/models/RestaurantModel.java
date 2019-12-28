package com.rvj.app.foodorder.models;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.rvj.app.foodorder.entity.enums.FoodType;
import com.rvj.app.foodorder.entity.enums.deserializers.FoodTypeDeserial;
import com.rvj.app.foodorder.validators.EnumConstraint;

import lombok.Data;

@Data
public class RestaurantModel {
	
	@NotEmpty(message = "Name should Not be null/empty")
	@Size(min = 5, max = 20, message = "Name length should be from 5 to 20 characters")
	private String name;
	
	@NotNull(message = "food type is mandatory")
	@EnumConstraint(enumType = FoodType.class)
	@JsonDeserialize(using = FoodTypeDeserial.class)
	private FoodType type;
	
	@Valid
	@NotNull(message = "address is mandatory")
	private AddressModel address;
}
