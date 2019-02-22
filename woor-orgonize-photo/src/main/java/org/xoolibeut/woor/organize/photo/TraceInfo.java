package org.xoolibeut.woor.organize.photo;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TraceInfo implements TraceInfoSpec {
	private String dateDebutTrace;
	private int sizePhoto;
	private int sizePhotoSucces;
	private int sizePhotoFail;
	private String dateFinTrace;
	private List<String> sources = new ArrayList<>();

	public String getDateDebutTrace() {
		return dateDebutTrace;
	}

	public void setDateDebutTrace(String dateDebutTrace) {
		this.dateDebutTrace = dateDebutTrace;
	}

	public int getSizePhoto() {
		return sizePhoto;
	}

	public void setSizePhoto(int sizePhoto) {
		this.sizePhoto = sizePhoto;
	}

	public int getSizePhotoSucces() {
		return sizePhotoSucces;
	}

	public void setSizePhotoSucces(int sizePhotoSucces) {
		this.sizePhotoSucces = sizePhotoSucces;
	}

	public int getSizePhotoFail() {
		return sizePhotoFail;
	}

	public void setSizePhotoFail(int sizePhotoFail) {
		this.sizePhotoFail = sizePhotoFail;
	}

	public String getDateFinTrace() {
		return dateFinTrace;
	}

	public void setDateFinTrace(String dateFinTrace) {
		this.dateFinTrace = dateFinTrace;
	}

	public List<String> getSources() {
		return sources;
	}

	public void setSources(List<String> sources) {
		this.sources = sources;
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

}
