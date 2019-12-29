package com.rvj.app.foodorder.utils;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class AppError {
	private Map<String, String> errors = new HashMap<String, String>();
	
	public void addError(String field, String desc) {
		this.errors.put(field, desc);
	}
	
	public boolean hasNoError() {
		return errors.isEmpty();
	}
}
