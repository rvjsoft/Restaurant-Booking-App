package com.rvj.app.foodorder.converters;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

import com.rvj.app.foodorder.entity.enums.FoodType;

public class FoodTypeSpringConverter implements Converter<String, FoodType>{

	@Override
	public FoodType convert(String source) {
		try {
			if(StringUtils.isNotBlank(source)) {
				return FoodType.valueOf(source);
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

}
