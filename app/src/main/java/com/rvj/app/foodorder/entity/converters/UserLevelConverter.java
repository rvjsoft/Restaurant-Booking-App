package com.rvj.app.foodorder.entity.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.rvj.app.foodorder.entity.enums.UserLevel;

@Converter
public class UserLevelConverter implements AttributeConverter<UserLevel, Character>{

	@Override
	public Character convertToDatabaseColumn(UserLevel attribute) {
		if ( attribute == null ) {
			return null;
		}
		return attribute.getCode();
	}

	@Override
	public UserLevel convertToEntityAttribute(Character dbData) {
		if ( dbData == null ) {
			return null;
		}
		return UserLevel.fromCode(dbData);
	}

}
