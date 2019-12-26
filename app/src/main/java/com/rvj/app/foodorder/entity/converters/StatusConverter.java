package com.rvj.app.foodorder.entity.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.rvj.app.foodorder.entity.enums.Status;

@Converter
public class StatusConverter implements AttributeConverter<Status, Character>{

	@Override
	public Character convertToDatabaseColumn(Status attribute) {
		if ( attribute == null ) {
			return null;
		}
		return attribute.getCode();
	}

	@Override
	public Status convertToEntityAttribute(Character dbData) {
		if ( dbData == null ) {
			return null;
		}
		return Status.fromCode(dbData);
	}

}
