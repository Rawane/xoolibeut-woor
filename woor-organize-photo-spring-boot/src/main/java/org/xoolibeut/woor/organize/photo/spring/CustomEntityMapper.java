package org.xoolibeut.woor.organize.photo.spring;

import java.io.IOException;

import org.elasticsearch.common.inject.Inject;
import org.springframework.data.elasticsearch.core.EntityMapper;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class CustomEntityMapper implements EntityMapper {
	private ObjectMapper objectMapper;
	@Inject
	private Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder;

	public CustomEntityMapper() {
		objectMapper = new ObjectMapper();
		JavaTimeModule timeModule = new JavaTimeModule();
		objectMapper.registerModule(timeModule);
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		objectMapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);		
	}

	@Override
	public String mapToString(Object object) throws IOException {
		return objectMapper.writeValueAsString(object);
	}

	@Override
	public <T> T mapToObject(String source, Class<T> clazz) throws IOException {
		return objectMapper.readValue(source, clazz);
	}
}
