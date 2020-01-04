package com.rvj.app.foodorder.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.rvj.app.foodorder.entity.enums.OrderStatus;
import com.rvj.app.foodorder.entity.enums.UserLevel;
import com.rvj.app.foodorder.entity.enums.deserializers.OrderStatusDeserial;
import com.rvj.app.foodorder.validators.EnumConstraint;

import lombok.Data;

@Data
public class GetOrderRequest extends BaseRequest {
	
	@NotEmpty(message = "UserName should Not be null/empty")
	@Size(min = 5, max = 20, message = "UserName length should be from 5 to 20 characters")
	private String userName;
	
	private Long resId;
	
	private Long custId;
	
	@EnumConstraint(enumType = OrderStatus.class)
	@JsonDeserialize(using = OrderStatusDeserial.class)
	private OrderStatus status;
	
	@JsonIgnore
	private UserLevel userLevel;
}
