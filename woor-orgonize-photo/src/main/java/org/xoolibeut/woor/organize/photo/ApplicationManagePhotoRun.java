package org.xoolibeut.woor.organize.photo;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ApplicationManagePhotoRun {
	private static ModelArrangePhoto modelArrangePhoto = new ModelArrangePhoto();
	private static ApplicationInfo applicationInfo = new ApplicationInfo();
	private static ConsoleLogger Console;

	public static void main(String[] args) {
		System.out.println(" -----START ORGANISE PHOTO------------------");
		Console = ConsoleLogger.getInstance(applicationInfo);
		buildInfoFromProperties();
		TraceInfo traceInfo = new TraceInfo();
		TaskArrangePhoto taskArrangePhoto = new TaskArrangePhoto(Console, modelArrangePhoto, traceInfo);
		LocalDateTime localNow = LocalDateTime.now();
		DateTimeFormatter formatterDDAY = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String dayD = localNow.format(formatterDDAY);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime startTime = LocalDateTime.parse(dayD + " " + applicationInfo.getStartDate(), formatter);
		Console.println("Démarrage " + startTime.format(formatter));
		Duration duration = Duration.between(localNow, startTime);
		long initalDelay = duration.getSeconds();
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleAtFixedRate(() -> {
			traceInfo.setDateDebutTrace(LocalDateTime.now().format(formatter));
			taskArrangePhoto.arrangePhoto();
			traceInfo.setDateFinTrace(LocalDateTime.now().format(formatter));
			TraceLog.getInstance(applicationInfo,Console).trace(traceInfo);
		}, initalDelay, 20, TimeUnit.SECONDS);

	}

	private static void buildInfoFromProperties() {
		Properties prop = new Properties();
		try {

			prop.load(ApplicationManagePhotoRun.class.getClassLoader().getResourceAsStream("application.properties"));
			modelArrangePhoto.setSource(prop.getProperty(KeyApplication.ORGANIZATION_SOURCE));
			modelArrangePhoto.setDest(prop.getProperty(KeyApplication.ORGANIZATION_DEST));
			modelArrangePhoto
					.setArrangeType(ArrangementType.valueOf(prop.getProperty(KeyApplication.ORGANIZATION_TYPE)));
			modelArrangePhoto
					.setExtension(Arrays.asList(prop.getProperty(KeyApplication.ORGANIZATION_EXTENSION).split(";")));
			applicationInfo.setViewLogConsole(
					Boolean.parseBoolean(prop.getProperty(KeyApplication.ORGANIZATION_APPLICATIF_VIEW_CONSOLE)));
			applicationInfo.setStartDate(prop.getProperty(KeyApplication.ORGANIZATION_APPLICATIF_START_DATE));
			applicationInfo.setApplicationLog(prop.getProperty(KeyApplication.ORGANIZATION_APPLICATIF_LOG));

		} catch (IOException e) {
			throw new RuntimeException("Erreur de chargement fichier", e);
		}
	}

}
