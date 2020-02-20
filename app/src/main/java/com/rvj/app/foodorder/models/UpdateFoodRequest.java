package com.rvj.app.foodorder.models;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UpdateFoodRequest extends BaseRequest {
	
	@Valid
	@NotNull(message = "food details are mandatory")
	private FoodModel food;
	
	@NotNull(message = "foodId should Not be null/empty")
	private Long foodId;
	
}
