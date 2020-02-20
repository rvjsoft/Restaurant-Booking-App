package com.rvj.app.foodorder.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.rvj.app.foodorder.entity.enums.OrderStatus;
import com.rvj.app.foodorder.entity.enums.deserializers.OrderStatusDeserial;
import com.rvj.app.foodorder.validators.EnumConstraint;

import lombok.Data;

@Data
public class OrderStatusRequest extends BaseRequest {
	
	@NotNull(message = "orderId Should not be Null")
	private Long orderId;
	
	@NotNull(message = "status should not be null")
	@EnumConstraint(enumType = OrderStatus.class)
	@JsonDeserialize(using = OrderStatusDeserial.class)
	private OrderStatus status;
	
}
