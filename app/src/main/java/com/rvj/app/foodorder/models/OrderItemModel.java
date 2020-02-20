package com.rvj.app.foodorder.models;

import lombok.Data;

@Data
public class OrderItemModel {

	private Long id;
	
	private Integer quantity;
	
	private String food;
}
