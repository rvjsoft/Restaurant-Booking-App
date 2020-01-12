package com.rvj.app.foodorder.models;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class FoodStatusRequest extends BaseRequest {
	
	private List<Long> available;
	
	private List<Long> unavailable;
	
}
