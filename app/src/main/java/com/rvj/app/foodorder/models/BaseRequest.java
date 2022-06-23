package com.rvj.app.foodorder.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.rvj.app.foodorder.entity.enums.UserLevel;
import lombok.Data;

@Data
public class BaseRequest {

	@JsonIgnore
	private String userName;
	
	@NotEmpty(message = "messageId should Not be null/empty")
	@Size(min = 5, max = 20, message = "messageId length should be from 5 to 20 characters")
	private String messageId;

	private UserLevel userLevel;
}
