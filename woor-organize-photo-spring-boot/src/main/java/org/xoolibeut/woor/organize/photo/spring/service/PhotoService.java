package org.xoolibeut.woor.organize.photo.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.xoolibeut.woor.organize.photo.spring.TagInfoPhoto;
import org.xoolibeut.woor.organize.photo.spring.repository.PhotoRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PhotoService {
	@Autowired
	private PhotoRepository photoRepository;
	@Autowired
	private ObjectMapper mapper;
	public void savePhoto(TagInfoPhoto photo) {
		try {
			System.out.println(mapper.writeValueAsString(photo));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
