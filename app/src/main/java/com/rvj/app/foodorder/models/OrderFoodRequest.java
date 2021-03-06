package com.rvj.app.foodorder.models;

import java.util.HashMap;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class OrderFoodRequest extends BaseRequest{
	
	@NotNull(message = "Restaurant Id should Not be null/empty")
	private Long resId;
	
	@NotNull(message = "foods should not be empty")
	@Size(min = 1, max = 10, message = "foods count should be in between 1 to 10")
	private HashMap<Long, Integer> foods;
	
}
