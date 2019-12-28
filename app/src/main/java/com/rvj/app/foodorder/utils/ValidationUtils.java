package com.rvj.app.foodorder.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ValidationUtils {
	
	private ValidationUtils() {}

	public static Map<String, String> getErrorMap(BindingResult bindingResult) {
		HashMap<String, String> errors = new HashMap<String, String>();

		if (bindingResult == null) {
			return null;
		} 
		log.info("processing constraint errors");
		for (FieldError error : bindingResult.getFieldErrors()) {
			errors.put(error.getField(), error.getDefaultMessage());
			log.info("Error: " + error.getField() + "-" + error.getDefaultMessage());
		}
		return errors;
	}

}
