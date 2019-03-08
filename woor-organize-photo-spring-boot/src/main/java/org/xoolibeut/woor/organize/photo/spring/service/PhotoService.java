package org.xoolibeut.woor.organize.photo.spring.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
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

	public Page<TagInfoPhoto> findByMarqueAppareil(String marque, int pageStart, int pageEnd) {
		Page<TagInfoPhoto> pagePhoto = photoRepository.findByMarqueAppareil(marque, PageRequest.of(pageStart, pageEnd));
		return pagePhoto;
	}

	public Page<TagInfoPhoto> findAll(int pageStart, int pageEnd) {
		Page<TagInfoPhoto> pagePhoto = photoRepository.findAll(PageRequest.of(pageStart, pageEnd));
		return pagePhoto;
	}

	public Page<TagInfoPhoto> findByDatePriseBetween(Date dateStart, Date dateEnd, int pageStart, int pageEnd) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
		QueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery("datePrise")
				.gte(dateFormat.format(dateStart)).lte(dateFormat.format(dateEnd)));
		Page<TagInfoPhoto> pagePhoto = photoRepository.search(queryBuilder, PageRequest.of(pageStart, pageEnd));
		return pagePhoto;
	}

	public Page<TagInfoPhoto> findByDatePrise(Date datePrise, int pageStart, int pageEnd) {
		Page<TagInfoPhoto> pagePhoto = photoRepository.findByDatePrise(datePrise, PageRequest.of(pageStart, pageEnd));
		return pagePhoto;
	}

	public Page<TagInfoPhoto> findByPathContaining(String path, int pageStart, int pageEnd) {
		Page<TagInfoPhoto> pagePhoto = photoRepository.findByPathContaining(path, PageRequest.of(pageStart, pageEnd));
		return pagePhoto;
	}

	public Page<TagInfoPhoto> searchMultiple(String search, int pageStart, int pageEnd) {
		QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(search).field("path").field("marqueAppareil")
				.field("model").type(MultiMatchQueryBuilder.Type.BEST_FIELDS);
		Page<TagInfoPhoto> pagePhoto = photoRepository.search(queryBuilder, PageRequest.of(pageStart, pageEnd));
		return pagePhoto;
	}

}
