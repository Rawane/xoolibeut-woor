package org.xoolibeut.woor.organize.photo;

import java.util.ArrayList;
import java.util.List;

public class ModelArrangePhoto {
	private String source;
	private String dest;
	private ArrangementType arrangeType;
	private List<String> extension=new ArrayList<>();

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
