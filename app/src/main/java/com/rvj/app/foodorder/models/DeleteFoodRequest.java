package com.rvj.app.foodorder.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class DeleteFoodRequest extends BaseRequest {
	
	@NotNull(message = "id of food should not be null/empty")
	private Long foodId;
}
