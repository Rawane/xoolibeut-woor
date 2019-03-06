package org.xoolibeut.woor.organize.photo.spring.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	@Autowired
	private ObjectMapper mapper;

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
		TagInfoPhoto tagInfoPhoto = new TagInfoPhoto();
		tagInfoPhoto.setDatePrise(new Date());
		try {
			System.out.println(mapper.writeValueAsString(tagInfoPhoto));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LOGGER.info("CRON", dateFormat.format(new Date()));
		taskArrangePhoto.arrangePhoto((path) -> {
			LOGGER.info(path.toString());
			woorIndexerPhoto.indexerPhoto(path);
		});
		return "index";
	}

	@RequestMapping("/search/marque/{marque}/{pageStart}/{pageEnd}")
	public Page<TagInfoPhoto> search(@PathVariable String marque, @PathVariable Integer pageStart,
			@PathVariable Integer pageEnd) {
		LOGGER.info("search");
		return photoService.findByMarqueAppareil(marque, pageStart, pageEnd);

	}

	@RequestMapping("/search/all/{pageStart}/{pageEnd}")
	public Page<TagInfoPhoto> searchAll(@PathVariable int pageStart, @PathVariable int pageEnd) {
		LOGGER.info("search");
		return photoService.findAll(pageStart, pageEnd);

	}

	@RequestMapping("/search/{dateStart}/{dateEnd}/{pageStart}/{pageEnd}")
	public Page<TagInfoPhoto> searchByDatePriseB(@PathVariable @DateTimeFormat(pattern = "yyyy:MM:dd HH:mm:ss") Date dateStart, @PathVariable @DateTimeFormat(pattern = "yyyy:MM:dd HH:mm:ss") Date dateEnd,
			@PathVariable int pageStart, @PathVariable int pageEnd) {
		LOGGER.info("search");
		return photoService.findByDatePriseBetween(dateStart, dateEnd, pageStart, pageEnd);
	}

	@RequestMapping("/search/b/{dateStart}/{dateEnd}/{pageStart}/{pageEnd}")
	public Page<TagInfoPhoto> searchByDatePriseB2(
			@PathVariable @DateTimeFormat(pattern = "yyyy:MM:dd HH:mm:ss") Date dateStart,
			@PathVariable @DateTimeFormat(pattern = "yyyy:MM:dd HH:mm:ss") Date dateEnd, @PathVariable int pageStart,
			@PathVariable int pageEnd) {
		LOGGER.info("search");
		return photoService.findByDatePriseBetween2(dateStart, dateEnd, pageStart, pageEnd);
	}

	@RequestMapping("/search/date/{date}")
	public Page<TagInfoPhoto> searchByDatePriseF(
			@PathVariable @DateTimeFormat(pattern = "yyyy:MM:dd HH:mm:ss") Date date) {

		LOGGER.info("searchByDatePriseF ");
		return photoService.findByDatePrise(date, 0, 10);

	}

	@RequestMapping("/search/path/{path}/{pageStart}/{pageEnd}")
	public Page<TagInfoPhoto> searchByPathContaining(@PathVariable String path, @PathVariable Integer pageStart,
			@PathVariable Integer pageEnd) {

		LOGGER.info("searchByPath " + path);
		return photoService.findByPathContaining(path, pageStart, pageEnd);

	}

	@RequestMapping("/search/date/cont/{datePrise}/{pageStart}/{pageEnd}")
	public Page<TagInfoPhoto> searchByDatePriseContaining(
			@PathVariable @DateTimeFormat(pattern = "yyyy:MM:dd HH:mm:ss") Date datePrise,
			@PathVariable Integer pageStart, @PathVariable Integer pageEnd) {

		LOGGER.info("searchByDatePriseContaining " + datePrise);

		return photoService.findByDatePriseContaining(datePrise, pageStart, pageEnd);

	}

}
