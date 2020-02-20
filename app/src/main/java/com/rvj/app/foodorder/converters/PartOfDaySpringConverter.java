package com.rvj.app.foodorder.converters;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

import com.rvj.app.foodorder.entity.enums.PartOfDay;

public class PartOfDaySpringConverter implements Converter<String, PartOfDay> {

	@Override
	public PartOfDay convert(String source) {
		if(StringUtils.isNotBlank(source)) {
			return PartOfDay.valueOf(source);
		}
		return null;
	}
}
