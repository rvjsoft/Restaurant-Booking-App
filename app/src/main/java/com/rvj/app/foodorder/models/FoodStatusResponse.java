package com.rvj.app.foodorder.models;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class FoodStatusResponse extends BaseResponse{
	
	List<Long> errorFoodIds;
	
}
