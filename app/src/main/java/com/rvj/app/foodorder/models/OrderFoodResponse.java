package com.rvj.app.foodorder.models;

import java.util.Set;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class OrderFoodResponse extends BaseResponse {

	Set<Long> errorFoodIds;
	
}
