package com.rvj.app.foodorder.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.rvj.app.foodorder.entity.enums.FoodCategory;
import com.rvj.app.foodorder.entity.enums.FoodType;
import com.rvj.app.foodorder.entity.enums.Status;
import com.rvj.app.foodorder.entity.enums.deserializers.FoodCategoryDeserial;
import com.rvj.app.foodorder.entity.enums.deserializers.FoodTypeDeserial;
import com.rvj.app.foodorder.validators.EnumConstraint;

import lombok.Data;

@Data
public class FoodModel {
	
	@Null
	private Long id;
	
	@NotEmpty(message = "food name should Not be null/empty")
	@Size(min = 2, max = 20, message = "food name length should be from 2 to 20 characters")
	private String name;
	
	@NotNull(message = "price should not be empty")
	private Double price;
	
	@EnumConstraint(enumType = FoodType.class)
	@NotNull(message = "foo type should not be empty")
	@JsonDeserialize(using = FoodTypeDeserial.class)
	private FoodType type;
	
	@EnumConstraint(enumType = FoodCategory.class)
	@NotNull(message = "food category should not be empty")
	@JsonDeserialize(using = FoodCategoryDeserial.class)
	private FoodCategory category;
	
	@Size(max = 20)
	private String imageId;
	
	@Null
	private Status status;
}
