package com.rvj.app.foodorder.validators;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<EnumConstraint, Enum<?>> {
	private List<? extends Enum<?>> valueList;

	@Override
	public void initialize(EnumConstraint type) {
		valueList = Arrays.asList(type.enumType().getEnumConstants());
	}

	@Override
	public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
		if (value == null)
			return false;
		return valueList.contains(value);
	}

}
