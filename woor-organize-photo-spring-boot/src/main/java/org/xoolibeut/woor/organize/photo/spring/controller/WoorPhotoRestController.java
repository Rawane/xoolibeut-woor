package org.xoolibeut.woor.organize.photo.spring.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.xoolibeut.woor.organize.photo.ApplicationInfo;
import org.xoolibeut.woor.organize.photo.ConsoleLogger;
import org.xoolibeut.woor.organize.photo.TaskArrangePhoto;
import org.xoolibeut.woor.organize.photo.TraceInfo;
import org.xoolibeut.woor.organize.photo.spring.ModelArrangePhoto;
import org.xoolibeut.woor.organize.photo.spring.TagInfoPhoto;
import org.xoolibeut.woor.organize.photo.spring.WoorIndexerPhoto;
import org.xoolibeut.woor.organize.photo.spring.service.PhotoService;

@RestController
public class WoorPhotoRestController {
	private static final Logger LOGGER = LoggerFactory.getLogger(WoorPhotoRestController.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	private TaskArrangePhoto taskArrangePhoto;
	@Autowired
	private ModelArrangePhoto modelArrangePhoto;
	@Autowired
	private WoorIndexerPhoto woorIndexerPhoto;
	@Autowired
	private PhotoService photoService;

	@PostConstruct
	private void init() {
		ApplicationInfo applicationInfo = new ApplicationInfo();
		applicationInfo.setViewLogConsole(false);
		ConsoleLogger console = ConsoleLogger.getInstance(applicationInfo);
		TraceInfo info = new TraceInfo();
		taskArrangePhoto = new TaskArrangePhoto(console, modelArrangePhoto, info);
		LOGGER.info("" + modelArrangePhoto.getArrangeType());
	}

	@RequestMapping(value = "/start", method = RequestMethod.GET)
	public String demarreIndexation() {

		LOGGER.info("CRON", dateFormat.format(new Date()));
		taskArrangePhoto.arrangePhoto((path) -> {
			LOGGER.info(path.toString());
			woorIndexerPhoto.indexerPhoto(path);
		});
		return "index";
	}

	@RequestMapping("/search/{marque}")
	public Page<TagInfoPhoto> search(@PathVariable String marque) {
		LOGGER.info("search");
		return photoService.findBymarqueAppareil(marque);

	}

	@RequestMapping("/search/all")
	public Page<TagInfoPhoto> searchAll() {
		LOGGER.info("search");
		return photoService.findAll();

	}

}
