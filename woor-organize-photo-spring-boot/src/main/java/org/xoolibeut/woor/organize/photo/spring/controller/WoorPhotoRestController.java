package org.xoolibeut.woor.organize.photo.spring.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xoolibeut.woor.organize.photo.TaskArrangePhoto;
import org.xoolibeut.woor.organize.photo.spring.TagInfoPhoto;
import org.xoolibeut.woor.organize.photo.spring.WoorIndexerPhoto;
import org.xoolibeut.woor.organize.photo.spring.service.PhotoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/v1/woorphoto")
@Api("Api pour la recherche de photo par date,theme et lieu")
public class WoorPhotoRestController {
	private static final Logger LOGGER = LoggerFactory.getLogger(WoorPhotoRestController.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	@Autowired
	private TaskArrangePhoto taskArrangePhoto;
	@Autowired
	private WoorIndexerPhoto woorIndexerPhoto;
	@Autowired
	private PhotoService photoService;

	@PostConstruct
	private void init() {

	}
	@ApiOperation(value = "Permet de lancer l'indexation des photos", response = String.class)
	@GetMapping("/start")
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Successfully")		  
		})
	@ApiIgnore
	public String demarreIndexation() {
		LOGGER.info("demarreIndexation ", dateFormat.format(new Date()));
		taskArrangePhoto.arrangePhoto((path) -> {
			LOGGER.info(path.toString());
			woorIndexerPhoto.indexerPhoto(path);
		});
		return "index";
	}
	@ApiOperation(value = "Rechercher par marque")
	@GetMapping("/search/marque/{marque}/{pageStart}/{pageEnd}")
	public Page<TagInfoPhoto> searchMarque(@PathVariable String marque, @PathVariable Integer pageStart,
			@PathVariable Integer pageEnd) {
		LOGGER.info("search");
		return photoService.findByMarqueAppareil(marque, pageStart, pageEnd);

	}
	@ApiOperation(value = "Afficher toutes les photos")
	@GetMapping("/search/all/{pageStart}/{pageEnd}")
	public Page<TagInfoPhoto> searchAll(@PathVariable int pageStart, @PathVariable int pageEnd) {
		LOGGER.info("search");
		return photoService.findAll(pageStart, pageEnd);

	}
	@ApiOperation(value = "Rechercher les photos entre deux dates")
	@GetMapping("/search/date/{dateStart}/{dateEnd}/{pageStart}/{pageEnd}")
	public Page<TagInfoPhoto> searchByDatePrise(
			@PathVariable @DateTimeFormat(pattern = "yyyy:MM:dd HH:mm:ss") Date dateStart,
			@PathVariable @DateTimeFormat(pattern = "yyyy:MM:dd HH:mm:ss") Date dateEnd, @PathVariable int pageStart,
			@PathVariable int pageEnd) {
		LOGGER.info("searchByDatePrise date debut  " + dateStart + " date fin " + dateEnd);
		return photoService.findByDatePriseBetween(dateStart, dateEnd, pageStart, pageEnd);

	}
	@ApiOperation(value = "Rechercher par date")
	@GetMapping("/search/date/{date}")
	public Page<TagInfoPhoto> searchByDatePriseF(
			@PathVariable @DateTimeFormat(pattern = "yyyy:MM:dd HH:mm:ss") Date date) {
		LOGGER.info("searchByDatePriseF  date " + date);
		return photoService.findByDatePrise(date, 0, 10);

	}
	@ApiOperation(value = "Rechercher par chemin")
	@GetMapping("/search/path/{path}/{pageStart}/{pageEnd}")
	public Page<TagInfoPhoto> searchByPathContaining(@PathVariable String path, @PathVariable Integer pageStart,
			@PathVariable Integer pageEnd) {
		LOGGER.info("searchByPath " + path);
		return photoService.findByPathContaining(path, pageStart, pageEnd);

	}
	@ApiOperation(value = "Rechercher par adresse lieu de prise de la photo")
	@GetMapping("/search/adress/{adress}/{pageStart}/{pageEnd}")
	public Page<TagInfoPhoto> searchByAdressContaining(@PathVariable String adress, @PathVariable Integer pageStart,
			@PathVariable Integer pageEnd) {
		LOGGER.info("searchByPath " + adress);
		return photoService.findByLieuPrisePhoto(adress, pageStart, pageEnd);

	}
	@ApiOperation(value = "Rechercher général par mot clé")
	@GetMapping("/search/{search}/{pageStart}/{pageEnd}")
	public Page<TagInfoPhoto> search(@PathVariable String search, @PathVariable Integer pageStart,
			@PathVariable Integer pageEnd) {
		LOGGER.info("searchByPath " + search);
		return photoService.searchMultiple(search, pageStart, pageEnd);

	}
}
