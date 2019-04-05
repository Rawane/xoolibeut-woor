package org.xoolibeut.woor.organize.photo.spring.model;

/**
 * 
 * @author AC75094508
 *
 */
public class GeoCode {
	private Double lat;
	private Double lon;
	private String uuid;
	private String ligneAdresse;

	public String getLigneAdresse() {
		return ligneAdresse;
	}

	public void setLigneAdresse(String ligneAdresse) {
		this.ligneAdresse = ligneAdresse;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

}
