package com.rvj.app.foodorder.models;

import java.time.LocalDate;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.rvj.app.foodorder.entity.enums.PartOfDay;
import com.rvj.app.foodorder.entity.enums.deserializers.PartOfDayDeserial;
import com.rvj.app.foodorder.validators.EnumConstraint;

import lombok.Data;

@Data
public class RestaurantTableRequest extends BaseRequest {

	@NotEmpty(message = "UserName should Not be null/empty")
	@Size(min = 5, max = 20, message = "UserName length should be from 5 to 20 characters")
	private String userName;
	
	@Max(value = 999, message = "tables count should be between 0 to 999")
	private Integer tableCount;
	
	private LocalDate date;
	
	@Max(value = 999, message = "tables count should be between 0 to 999")
	private Integer baseCount;
	
	@EnumConstraint(enumType = PartOfDay.class)
	@JsonDeserialize(using = PartOfDayDeserial.class)
	private PartOfDay part;
	
	@JsonIgnore
	private String action;
	
}
