package com.rvj.app.foodorder.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.rvj.app.foodorder.entity.enums.FoodType;
import com.rvj.app.foodorder.entity.enums.Status;
import com.rvj.app.foodorder.entity.enums.deserializers.FoodTypeDeserial;
import com.rvj.app.foodorder.entity.enums.deserializers.StatusDeserial;
import com.rvj.app.foodorder.validators.EnumConstraint;

import lombok.Data;

@Data
public class GetRestaurantsRequest extends BaseRequest {
	
	private Long resId;
	
	private String resName;
	
	@EnumConstraint(enumType = FoodType.class)
	@JsonDeserialize(using = FoodTypeDeserial.class)
	private FoodType type;
	
	@EnumConstraint(enumType = Status.class)
	@JsonDeserialize(using = StatusDeserial.class)
	private Status status;
	
	@JsonIgnore
	private String action;
	
	private Integer page;
	
	private Integer size;
}
