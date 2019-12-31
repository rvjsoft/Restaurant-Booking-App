package com.rvj.app.foodorder.entity.enums.deserializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.rvj.app.foodorder.entity.enums.FoodCategory;

public class FoodCategoryDeserial  extends JsonDeserializer<FoodCategory>{

	@Override
	public FoodCategory deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		try {
			return FoodCategory.valueOf(p.getValueAsString().toUpperCase());
		} catch (Exception e) {
			return null;
		}
	}
	
}
