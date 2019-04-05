package org.xoolibeut.woor.organize.photo.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.xoolibeut.woor.organize.photo.ApplicationInfo;
import org.xoolibeut.woor.organize.photo.ConsoleLogger;
import org.xoolibeut.woor.organize.photo.TaskArrangePhoto;
import org.xoolibeut.woor.organize.photo.TraceInfo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootApplication
@EnableScheduling
public class ApplicationSpringRun {
	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationSpringRun.class);
	@Autowired
	private ModelArrangePhoto modelArrangePhoto;

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

	@Bean
	public TaskArrangePhoto getTaskArrangePhoto() {
		ApplicationInfo applicationInfo = new ApplicationInfo();
		applicationInfo.setViewLogConsole(false);
		ConsoleLogger console = ConsoleLogger.getInstance(applicationInfo);
		TraceInfo info = new TraceInfo();
		TaskArrangePhoto taskArrangePhoto = new TaskArrangePhoto(console, modelArrangePhoto, info);
		LOGGER.info("" + modelArrangePhoto.getArrangeType());
		return taskArrangePhoto;
	}

}
