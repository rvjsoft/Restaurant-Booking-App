package com.rvj.app.foodorder.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.rvj.app.foodorder.entity.enums.OrderStatus;

import lombok.Data;

@Data
public class OrderModel {

	private Long id;
	
	private LocalDateTime orderedOn;
	
	private OrderStatus status;
	
	private String customerName;
	
	private String restaurantName;
	
	private List<OrderItemModel> foodItems = new ArrayList<OrderItemModel>();
}
