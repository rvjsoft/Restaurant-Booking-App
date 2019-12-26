package com.rvj.app.foodorder.converters;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

import com.rvj.app.foodorder.entity.enums.FoodCategory;

public class FoodCategorySpringConverter implements Converter<String, FoodCategory>{
	
	@Override
	public FoodCategory convert(String source) {
		if(StringUtils.isNotBlank(source)) {
			return FoodCategory.valueOf(source);
		}
		return null;
	}
}
