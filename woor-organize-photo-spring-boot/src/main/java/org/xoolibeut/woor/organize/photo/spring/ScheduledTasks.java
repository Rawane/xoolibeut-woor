package org.xoolibeut.woor.organize.photo.spring;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.xoolibeut.woor.organize.photo.ApplicationInfo;
import org.xoolibeut.woor.organize.photo.ConsoleLogger;
import org.xoolibeut.woor.organize.photo.TaskArrangePhoto;
import org.xoolibeut.woor.organize.photo.TraceInfo;
import org.xoolibeut.woor.organize.photo.TraceLog;

@Component
public class ScheduledTasks {
	private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTasks.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	private TaskArrangePhoto taskArrangePhoto;
	@Autowired
	private ModelArrangePhoto modelArrangePhoto;
	@Autowired
	private WoorIndexerPhoto woorIndexerPhoto;
	private ApplicationInfo applicationInfo;
	private ConsoleLogger console;
	private TraceInfo traceInfo;
	@Value("${woor.photo.organization.applicatif.log}")
	private String fileTrace;

	@PostConstruct
	private void init() {
		applicationInfo = new ApplicationInfo();
		applicationInfo.setViewLogConsole(false);
		applicationInfo.setApplicationLog(fileTrace);
		console = ConsoleLogger.getInstance(applicationInfo);
		traceInfo = new TraceInfo();
		taskArrangePhoto = new TaskArrangePhoto(console, modelArrangePhoto, traceInfo);

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
			TraceLog.getInstance(applicationInfo, console).trace(traceInfo);
		});
	}
}
