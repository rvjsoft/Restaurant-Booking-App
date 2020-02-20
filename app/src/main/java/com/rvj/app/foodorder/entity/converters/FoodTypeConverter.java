package com.rvj.app.foodorder.entity.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.rvj.app.foodorder.entity.enums.FoodType;

@Converter
public class FoodTypeConverter implements AttributeConverter<FoodType, Character>{

	@Override
	public Character convertToDatabaseColumn(FoodType attribute) {
		if ( attribute == null ) {
			return null;
		}
		return attribute.getCode();
	}

	@Override
	public FoodType convertToEntityAttribute(Character dbData) {
		if ( dbData == null ) {
			return null;
		}
		return FoodType.fromCode(dbData);
	}

}
