package com.jun.chatapp.domain.mapper;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMapper {
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	public static String toJson(Object object) {
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public static <T> T fromJson(String object, Class<T> clazz) {
		try {
			return mapper.readValue(object, clazz);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public static <T> List<T> fromJsonList(String json, Class<T> clazz) {
		try {
			return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

}


