package org.xoolibeut.woor.organize.photo.spring;

import java.io.IOException;
import java.nio.file.Path;

import org.apache.sanselan.ImageReadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xoolibeut.woor.organize.photo.spring.service.PhotoService;

/**
 * 
 * @author AC75094508
 *
 */
@Component
public class WoorIndexerPhoto {
	private static final Logger LOGGER = LoggerFactory.getLogger(WoorIndexerPhoto.class);
	@Autowired
	private PhotoService photoService;
	@Autowired
	private WoorImageExtractInfo woorImageExtractInfo;
	@Autowired
	private WoorGoogleGeocode woorGoogleGeocode;

	public void indexerPhoto(Path path) {
		try {
			TagInfoPhoto photo = woorImageExtractInfo.readAndDisplayMetadata(path.toFile());
			woorGoogleGeocode.addDataAdressToPhoto(photo);
			photoService.savePhoto(photo);
			
		} catch (ImageReadException | IOException e) {
			LOGGER.error("erreur indexation",e);
		}
	}
}
