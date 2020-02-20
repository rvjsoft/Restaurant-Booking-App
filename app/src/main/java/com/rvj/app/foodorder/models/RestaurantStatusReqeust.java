package com.rvj.app.foodorder.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.rvj.app.foodorder.entity.enums.Status;
import com.rvj.app.foodorder.entity.enums.deserializers.StatusDeserial;
import com.rvj.app.foodorder.validators.EnumConstraint;

import lombok.Data;

@Data
public class RestaurantStatusReqeust extends BaseRequest {

	@NotNull(message = "status should not be empty")
	@EnumConstraint(enumType = Status.class)
	@JsonDeserialize(using = StatusDeserial.class)
	private Status status;
	
}
