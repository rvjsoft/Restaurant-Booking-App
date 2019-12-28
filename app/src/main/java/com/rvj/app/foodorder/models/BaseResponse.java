package com.rvj.app.foodorder.models;

import java.util.Map;

import lombok.Data;

@Data
public class BaseResponse {
	private String messageId;
	private Map<String, String> errors;
	private String message;
}
