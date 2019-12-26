package com.rvj.app.foodorder.entity.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.rvj.app.foodorder.entity.enums.PartOfDay;

@Converter
public class PartOfDayConverter implements AttributeConverter<PartOfDay, Character>{

	@Override
	public Character convertToDatabaseColumn(PartOfDay attribute) {
		if ( attribute == null ) {
			return null;
		}
		return attribute.getCode();
	}

	@Override
	public PartOfDay convertToEntityAttribute(Character dbData) {
		if ( dbData == null ) {
			return null;
		}
		return PartOfDay.fromCode(dbData);
	}

}
