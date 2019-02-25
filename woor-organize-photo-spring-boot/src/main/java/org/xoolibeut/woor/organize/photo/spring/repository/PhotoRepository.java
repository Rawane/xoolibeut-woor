package org.xoolibeut.woor.organize.photo.spring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.xoolibeut.woor.organize.photo.spring.TagInfoPhoto;

public interface PhotoRepository extends ElasticsearchRepository<TagInfoPhoto, String> {
	 Page<TagInfoPhoto> findBymarqueAppareil(String name, Pageable pageable);
}
