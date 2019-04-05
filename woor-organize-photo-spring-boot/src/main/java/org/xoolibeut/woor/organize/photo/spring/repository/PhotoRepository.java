package org.xoolibeut.woor.organize.photo.spring.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.xoolibeut.woor.organize.photo.spring.TagInfoPhoto;

public interface PhotoRepository extends ElasticsearchRepository<TagInfoPhoto, String> {
	 Page<TagInfoPhoto> findByMarqueAppareil(String name, Pageable pageable);
	 Page<TagInfoPhoto> findByDatePriseBetween(Date dateStart,Date DateEnd, Pageable pageable);
	 Page<TagInfoPhoto> findByDatePrise(Date datePrise, Pageable pageable);
	 Page<TagInfoPhoto> findByPathContaining(String path, Pageable pageable);
	 Page<TagInfoPhoto> findByDatePriseContaining(Date datePrise, Pageable pageable);	 
	 Page<TagInfoPhoto> findByLigneAdresseContaining(String ligneAdresse, Pageable pageable);	
	 Page<TagInfoPhoto> findByFromContaining(String from, Pageable pageable);	 
	 
}
