package com.rvj.app.foodorder.models;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class BaseResponse {
	private String messageId;
	private Map<String, String> errors = new HashMap<String, String>();
	private String message;
}
