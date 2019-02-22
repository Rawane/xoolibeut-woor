package org.xoolibeut.woor.organize.photo.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xoolibeut.woor.organize.photo.spring.TagInfoPhoto;
import org.xoolibeut.woor.organize.photo.spring.repository.PhotoRepository;

@Service
public class PhotoService {
	@Autowired
	private PhotoRepository photoRepository;

	public void savePhoto(TagInfoPhoto photo) {
		photoRepository.save(photo);
	}
}
