package com.rvj.app.foodorder.entity.enums.deserializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.rvj.app.foodorder.entity.enums.FoodType;

public class FoodTypeDeserial<T> extends JsonDeserializer<FoodType>{

	@Override
	public FoodType deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		try {
			return FoodType.valueOf(p.getValueAsString());
		} catch (Exception e) {
			return null;
		}
	}
	
}
