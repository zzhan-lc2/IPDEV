package com.ipdev.cnipr.entity.patent;

import java.io.Serializable;

public class SectionInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	String sectionName;
	int recordNum;

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public int getRecordNum() {
		return recordNum;
	}

	public void setRecordNum(int recordNum) {
		this.recordNum = recordNum;
	}

}
