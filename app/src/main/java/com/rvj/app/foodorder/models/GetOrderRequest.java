package com.rvj.app.foodorder.models;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
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
	
	private Long resId;
	
	private Long custId;
	
	@EnumConstraint(enumType = OrderStatus.class)
	@JsonDeserialize(using = OrderStatusDeserial.class)
	private OrderStatus status;
	
	@JsonIgnore
	private UserLevel userLevel;
}
