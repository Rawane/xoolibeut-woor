package org.xoolibeut.woor.organize.photo.spring.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.xoolibeut.woor.organize.photo.spring.TagInfoPhoto;

public interface PhotoRepository extends ElasticsearchRepository<TagInfoPhoto, String> {

}
