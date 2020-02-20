package com.rvj.app.foodorder.converters;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

import com.rvj.app.foodorder.entity.enums.OrderStatus;

public class OrderStatusSpringConverter implements Converter<String, OrderStatus>{

	@Override
	public OrderStatus convert(String source) {
		if(StringUtils.isNotBlank(source)) {
			return OrderStatus.valueOf(source);
		}
		return null;
	}
}
