package org.xoolibeut.woor.organize.photo.spring;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author AC75094508
 *
 */
@Document(createIndex = true, indexName = "photo", type = "tagInfo")
public class TagInfoPhoto {
	private String datePrise;
	private String marqueAppareil;
	private String model;
	private Double latitude;
	private Double longitude;
	@Id
	private String path;

	public String getDatePrise() {
		return datePrise;
	}

	@Override
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException exception) {
			exception.printStackTrace();
		}
		return this.toString();
	}

	public void setDatePrise(String datePrise) {
		this.datePrise = datePrise;
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

}
