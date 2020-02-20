package com.rvj.app.foodorder.models;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
public class AddFoodRequest extends BaseRequest{
	
	@Valid
	@NotNull(message = "food details are mandatory")
	private List<FoodModel> foods;
	
}
