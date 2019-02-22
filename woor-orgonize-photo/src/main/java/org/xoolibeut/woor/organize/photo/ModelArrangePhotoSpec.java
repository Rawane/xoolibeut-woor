package org.xoolibeut.woor.organize.photo;

import java.util.List;

public interface ModelArrangePhotoSpec {

	String getSource();

	String getDest();

	ArrangementType getArrangeType();

	List<String> getExtension();

}
