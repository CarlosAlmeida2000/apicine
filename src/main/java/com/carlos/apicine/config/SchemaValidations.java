package com.carlos.apicine.config;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class SchemaValidations {
	
	public <T> Boolean validateJson(String path, T object) {
		try {
			JSONObject jsonBase = new JSONObject(new JSONTokener(SchemaValidations.class.getResourceAsStream(path)));
			Schema schema = SchemaLoader.load(jsonBase);
			String json = new ObjectMapper().writeValueAsString(object);
			schema.validate(new JSONObject(json));
		}catch(ValidationException e) {
			return false;
		} catch (JsonProcessingException e) {
			return false;
		}
		return true;
	}
}