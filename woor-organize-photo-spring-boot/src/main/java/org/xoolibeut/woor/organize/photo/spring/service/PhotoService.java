package org.xoolibeut.woor.organize.photo.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

	public Page<TagInfoPhoto> findBymarqueAppareil(String marque) {
		Page<TagInfoPhoto> pagePhoto = photoRepository.findBymarqueAppareil(marque, PageRequest.of(0, 10));
		return pagePhoto;
	}

	public Page<TagInfoPhoto> findAll() {
		Page<TagInfoPhoto> pagePhoto = photoRepository.findAll(PageRequest.of(0, 10));
		return pagePhoto;
	}
}
