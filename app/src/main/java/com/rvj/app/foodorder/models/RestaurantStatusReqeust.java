package com.rvj.app.foodorder.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.rvj.app.foodorder.entity.enums.Status;
import com.rvj.app.foodorder.entity.enums.deserializers.StatusDeserial;
import com.rvj.app.foodorder.validators.EnumConstraint;

import lombok.Data;

@Data
@Component
public class RestaurantStatusReqeust extends BaseRequest {

	@NotEmpty(message = "UserName should Not be null/empty")
	@Size(min = 5, max = 20, message = "UserName length should be from 5 to 20 characters")
	private String userName;
	
	@NotNull(message = "status should not be empty")
	@EnumConstraint(enumType = Status.class)
	@JsonDeserialize(using = StatusDeserial.class)
	private Status status;
	
}
