package com.rvj.app.foodorder.entity.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.rvj.app.foodorder.entity.enums.OrderStatus;

@Converter
public class OrderStatusConverter implements AttributeConverter<OrderStatus, Character>{

	@Override
	public Character convertToDatabaseColumn(OrderStatus attribute) {
		if ( attribute == null ) {
			return null;
		}
		return attribute.getCode();
	}

	@Override
	public OrderStatus convertToEntityAttribute(Character dbData) {
		if ( dbData == null ) {
			return null;
		}
		return OrderStatus.fromCode(dbData);
	}

}
