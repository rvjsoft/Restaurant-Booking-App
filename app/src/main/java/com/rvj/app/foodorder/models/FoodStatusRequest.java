package com.rvj.app.foodorder.models;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class FoodStatusRequest extends BaseRequest {
	
	@NotEmpty(message = "UserName should Not be null/empty")
	@Size(min = 5, max = 20, message = "UserName length should be from 5 to 20 characters")
	private String userName;
	
	private List<Long> available;
	
	private List<Long> unavailable;
	
}
