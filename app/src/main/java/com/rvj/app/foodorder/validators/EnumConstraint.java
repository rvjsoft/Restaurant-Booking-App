package com.rvj.app.foodorder.validators;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValidator.class)
public @interface EnumConstraint {
	Class<? extends Enum<?>> enumType();
	boolean canBeNull() default false;
	String message() default "Not a valid Value";
	Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
