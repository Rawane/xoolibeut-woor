package org.xoolibeut.woor.organize.photo.spring;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.xoolibeut.woor.organize.photo.ApplicationInfo;
import org.xoolibeut.woor.organize.photo.ConsoleLogger;
import org.xoolibeut.woor.organize.photo.TaskArrangePhoto;
import org.xoolibeut.woor.organize.photo.TraceInfo;

@Component
public class ScheduledTasks {
	private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTasks.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	private TaskArrangePhoto taskArrangePhoto;
	@Autowired
	private ModelArrangePhoto modelArrangePhoto;
	@Autowired
	private WoorIndexerPhoto woorIndexerPhoto;

	@PostConstruct
	private void init() {
		ApplicationInfo applicationInfo = new ApplicationInfo();
		applicationInfo.setViewLogConsole(false);
		ConsoleLogger console = ConsoleLogger.getInstance(applicationInfo);
		TraceInfo info = new TraceInfo();
		taskArrangePhoto = new TaskArrangePhoto(console, modelArrangePhoto, info);
		System.out.println(modelArrangePhoto.getArrangeType());
	}

	/*
	 * @Scheduled(fixedRate = 1000) public void reportCurrentTime() {
	 * LOGGER.info("The time is now {}", dateFormat.format(new Date())); }
	 */

	@Scheduled(cron = "${woor.photo.organization.applicatif.startDate}")
	public void movePhoto() {
		LOGGER.info("CRON", dateFormat.format(new Date()));
		taskArrangePhoto.arrangePhoto((path) -> {
			LOGGER.info(path.toString());
			woorIndexerPhoto.indexerPhoto(path);
		});
	}
}
