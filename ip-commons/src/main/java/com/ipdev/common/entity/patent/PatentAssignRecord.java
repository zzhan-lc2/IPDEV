package com.ipdev.common.entity.patent;

import java.util.Date;

public class PatentAssignRecord extends PatentRecordBase {
	private static final long serialVersionUID = 1L;

	private String assignor;
	private String assignee;
	private String curAssignee;
	private String licenseType;
	private String contractRecordNo;
	private Date contractRecordDate;
	private Date changeDate;
	private Date cancelDate;

	public String getAssignor() {
		return assignor;
	}

	public void setAssignor(String assignor) {
		this.assignor = assignor;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getCurAssignee() {
		return curAssignee;
	}

	public void setCurAssignee(String curAssignee) {
		this.curAssignee = curAssignee;
	}

	public String getLicenseType() {
		return licenseType;
	}

	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}

	public String getContractRecordNo() {
		return contractRecordNo;
	}

	public void setContractRecordNo(String contractRecordNo) {
		this.contractRecordNo = contractRecordNo;
	}

	public Date getContractRecordDate() {
		return contractRecordDate;
	}

	public void setContractRecordDate(Date contractRecordDate) {
		this.contractRecordDate = contractRecordDate;
	}

	public Date getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}

	public Date getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}

}
