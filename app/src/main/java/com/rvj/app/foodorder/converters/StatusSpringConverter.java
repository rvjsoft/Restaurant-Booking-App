package com.rvj.app.foodorder.converters;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

import com.rvj.app.foodorder.entity.enums.Status;

public class StatusSpringConverter implements Converter<String, Status> {

	@Override
	public Status convert(String source) {
		if(StringUtils.isNotBlank(source)) {
			return Status.valueOf(source);
		}
		return null;
	}
}
