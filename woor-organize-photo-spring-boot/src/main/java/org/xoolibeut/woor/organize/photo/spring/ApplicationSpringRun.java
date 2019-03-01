package org.xoolibeut.woor.organize.photo.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootApplication
@EnableScheduling
public class ApplicationSpringRun {

	public static void main(String[] args) {
		  SpringApplication.run(ApplicationSpringRun.class);

	}
	@Bean
	@Primary
	public ObjectMapper objectMapper() {
	    ObjectMapper mapper = new ObjectMapper();

	    JavaTimeModule timeModule = new JavaTimeModule();
	    mapper.registerModule(timeModule);

	    mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
	    mapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);

	    return mapper;
	}

}
