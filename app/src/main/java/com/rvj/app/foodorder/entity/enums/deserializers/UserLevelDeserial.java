package com.rvj.app.foodorder.entity.enums.deserializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.rvj.app.foodorder.entity.enums.UserLevel;

public class UserLevelDeserial extends JsonDeserializer<UserLevel>{

	@Override
	public UserLevel deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		try {
			return UserLevel.valueOf(p.getValueAsString());
		} catch (Exception e) {
			return null;
		}
	}
	
}
