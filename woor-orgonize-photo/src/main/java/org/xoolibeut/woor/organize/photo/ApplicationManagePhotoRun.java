package org.xoolibeut.woor.organize.photo;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
		TaskArrangePhoto taskArrangePhoto = new TaskArrangePhoto(Console, modelArrangePhoto);
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
			taskArrangePhoto.arrangePhoto();
		}, initalDelay, 20, TimeUnit.SECONDS);

	}

	private static void buildInfoFromProperties() {
		Properties prop = new Properties();
		try {

			prop.load(ApplicationManagePhotoRun.class.getClassLoader().getResourceAsStream("application.properties"));
			modelArrangePhoto.setSource(prop.getProperty("woor.photo.source"));
			modelArrangePhoto.setDest(prop.getProperty("woor.photo.dest"));
			modelArrangePhoto.setArrangeType(ArrangementType.valueOf(prop.getProperty("woor.photo.organization.type")));
			applicationInfo.setViewLogConsole(
					Boolean.parseBoolean(prop.getProperty("woor.photo.organization.applicatif.viewConsole")));
			applicationInfo.setStartDate(prop.getProperty("woor.photo.organization.applicatif.startDate"));

		} catch (IOException e) {
			throw new RuntimeException("Erreur de chargement fichier", e);
		}
	}

}
