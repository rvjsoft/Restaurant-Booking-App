package com.rvj.app.foodorder.models;

import java.util.Set;

import lombok.Data;

@Data
public class OrderFoodResponse extends BaseResponse {

	Set<Long> errorFoodIds;
	
}
