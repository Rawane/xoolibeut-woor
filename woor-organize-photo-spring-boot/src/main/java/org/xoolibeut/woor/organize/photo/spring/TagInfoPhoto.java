package org.xoolibeut.woor.organize.photo.spring;

import java.io.IOException;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * 
 * @author AC75094508
 *
 */
@Document(createIndex = true, indexName = "photo", type = "tagInfo")
public class TagInfoPhoto {
	@JsonFormat(pattern = "yyyy:MM:dd HH:mm:ss")
	@Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy:MM:dd HH:mm:ss")
	private Date datePrise;
	private String marqueAppareil;
	private String model;
	private Double latitude;
	private Double longitude;
	private String from;
	@Id
	private String path;

	@Override
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			JavaTimeModule timeModule = new JavaTimeModule();
			mapper.registerModule(timeModule);
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException exception) {
			exception.printStackTrace();
		}
		return this.toString();
	}

	public String getMarqueAppareil() {
		return marqueAppareil;
	}

	public void setMarqueAppareil(String marqueAppareil) {
		this.marqueAppareil = marqueAppareil;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public static void main(String[] args) {
		ObjectMapper mapper = new ObjectMapper();

		JavaTimeModule timeModule = new JavaTimeModule();
		mapper.registerModule(timeModule);
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		mapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
		TagInfoPhoto tagInfoPhoto = new TagInfoPhoto();
	
		try {
			tagInfoPhoto.setDatePrise(new Date());
			String dateString = mapper.writeValueAsString(tagInfoPhoto);
			System.out.println(mapper.writeValueAsString(tagInfoPhoto));
			TagInfoPhoto p = mapper.readValue(dateString, TagInfoPhoto.class);
			System.out.println(p);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Date getDatePrise() {
		return datePrise;
	}

	public void setDatePrise(Date datePrise) {
		this.datePrise = datePrise;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}
}
