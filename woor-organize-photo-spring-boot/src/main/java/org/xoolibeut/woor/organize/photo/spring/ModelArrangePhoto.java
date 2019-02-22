package org.xoolibeut.woor.organize.photo.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.xoolibeut.woor.organize.photo.ArrangementType;
import org.xoolibeut.woor.organize.photo.ModelArrangePhotoSpec;
@Component
public class ModelArrangePhoto implements ModelArrangePhotoSpec {
	@Value("${woor.photo.organization.source}")
	private String source;
	@Value("${woor.photo.organization.dest}")
	private String dest;
	@Value("${woor.photo.organization.type}")
	private ArrangementType arrangeType;
	@Value("#{'${woor.photo.organization.extension}'.split(',')}")
	private List<String> extension = new ArrayList<>();

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDest() {
		return dest;
	}

	public void setDest(String dest) {
		this.dest = dest;
	}

	public ArrangementType getArrangeType() {
		return arrangeType;
	}

	public void setArrangeType(ArrangementType arrangeType) {
		this.arrangeType = arrangeType;
	}

	public List<String> getExtension() {
		return extension;
	}

	public void setExtension(List<String> extension) {
		this.extension = extension;
	}

}
