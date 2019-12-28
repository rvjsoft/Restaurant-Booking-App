package com.rvj.app.foodorder.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class BaseRequest {
	
	@NotEmpty(message = "messageId should Not be null/empty")
	@Size(min = 5, max = 20, message = "messageId length should be from 5 to 20 characters")
	private String messageId;
}
