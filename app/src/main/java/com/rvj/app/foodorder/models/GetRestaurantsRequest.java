package com.rvj.app.foodorder.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.rvj.app.foodorder.entity.enums.FoodType;
import com.rvj.app.foodorder.entity.enums.deserializers.FoodTypeDeserial;
import com.rvj.app.foodorder.validators.EnumConstraint;

import lombok.Data;

@Data
public class GetRestaurantsRequest extends BaseRequest {
	
	private Long resId;
	
	private String resName;
	
	@EnumConstraint(enumType = FoodType.class)
	@JsonDeserialize(using = FoodTypeDeserial.class)
	private FoodType type;
	
	@JsonIgnore
	private String action;
}
