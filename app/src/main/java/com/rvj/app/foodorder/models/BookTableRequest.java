package com.rvj.app.foodorder.models;

import java.time.LocalDate;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.rvj.app.foodorder.entity.enums.PartOfDay;
import com.rvj.app.foodorder.entity.enums.deserializers.PartOfDayDeserial;
import com.rvj.app.foodorder.validators.EnumConstraint;

import lombok.Data;

@Data
@Component
public class BookTableRequest extends BaseRequest{
	
	@NotEmpty(message = "UserName should Not be null/empty")
	@Size(min = 5, max = 20, message = "UserName length should be from 5 to 20 characters")
	private String userName;
	
	@NotNull(message = "Restaurant Id should Not be null/empty")
	private Long resId;
	
	@NotNull(message = "date should not be empty")
	private LocalDate date;
	
	@NotNull(message = "part should not be empty")
	@EnumConstraint(enumType = PartOfDay.class)
	@JsonDeserialize(using = PartOfDayDeserial.class)
	private PartOfDay part;
	
	@Max(value = 999, message = "tables count should be between 0 to 999")
	private Integer count;
	
	
}
