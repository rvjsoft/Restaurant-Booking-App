package com.rvj.app.foodorder.models;

import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Component
public class RestaurantTableRequest extends BaseRequest {

	@NotEmpty(message = "UserName should Not be null/empty")
	@Size(min = 5, max = 20, message = "UserName length should be from 5 to 20 characters")
	private String userName;
	
	@Max(value = 999, message = "tables count should be between 0 to 999")
	private Integer tableCount;
	
	private LocalDate date;
	
	@Max(value = 999, message = "tables count should be between 0 to 999")
	private Integer baseCount;
	
	@JsonIgnore
	private String action;
	
}
