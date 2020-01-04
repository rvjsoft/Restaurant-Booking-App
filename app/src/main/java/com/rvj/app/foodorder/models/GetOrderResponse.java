package com.rvj.app.foodorder.models;

import java.util.List;

import lombok.Data;

@Data
public class GetOrderResponse extends BaseResponse {
	
	private List<OrderModel> orders;
	
}
