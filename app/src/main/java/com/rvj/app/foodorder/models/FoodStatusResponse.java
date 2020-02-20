package com.rvj.app.foodorder.models;

import java.util.List;

import lombok.Data;

@Data
public class FoodStatusResponse extends BaseResponse{
	
	List<Long> errorFoodIds;
	
}
