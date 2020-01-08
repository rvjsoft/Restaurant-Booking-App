package com.rvj.app.foodorder.models;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.rvj.app.foodorder.entity.Food;
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
	
	@Email
	@NotEmpty(message = "email should Not be null/empty")
	@Size(min = 5, max = 30, message = "email length should be from 5 to 20 characters")
	private String email;
	
	@NotEmpty(message = "phone should Not be null/empty")
	@Size(min = 5, max = 15, message = "phone length should be from 5 to 15 characters")
	@Pattern(regexp = "[0-9]*", message = "phone number should be numeric")
	private String phone;
	
	@NotNull(message = "Table count should not be empty")
	@Max(value = 999, message = "tables count should be between 0 to 999")
	private Integer tableCount;
	
	@Size(max = 20)
	private String imageId;
	
}
