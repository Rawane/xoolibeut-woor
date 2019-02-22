package org.xoolibeut.woor.organize.photo;

import java.util.List;

public interface TraceInfoSpec {

	String getDateDebutTrace();

	void setDateDebutTrace(String dateDebutTrace);

	int getSizePhoto();

	void setSizePhoto(int sizePhoto);

	int getSizePhotoSucces();

	void setSizePhotoSucces(int sizePhotoSucces);

	int getSizePhotoFail();

	void setSizePhotoFail(int sizePhotoFail);

	String getDateFinTrace();

	void setDateFinTrace(String dateFinTrace);

	List<String> getSources();

	void setSources(List<String> sources);

}
