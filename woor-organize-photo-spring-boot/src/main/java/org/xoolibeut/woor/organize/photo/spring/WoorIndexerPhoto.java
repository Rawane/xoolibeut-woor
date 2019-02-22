package org.xoolibeut.woor.organize.photo.spring;

import java.io.IOException;
import java.nio.file.Path;

import org.apache.sanselan.ImageReadException;
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
	@Autowired
	private PhotoService photoService;
	@Autowired
	private WoorImageExtractInfo woorImageExtractInfo;

	public void indexerPhoto(Path path) {
		try {
			TagInfoPhoto photo = woorImageExtractInfo.readAndDisplayMetadata(path.toFile());
			photoService.savePhoto(photo);
		} catch (ImageReadException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
