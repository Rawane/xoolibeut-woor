package org.xoolibeut.woor.organize.photo;

public class TraceInfo {
	private String dateDebutTrace;
	private int sizePhoto;
	private int sizePhotoSucces;
	private int sizePhotoFail;
	private String dateFinTrace;

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
}
