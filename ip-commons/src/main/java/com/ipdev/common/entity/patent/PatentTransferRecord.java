package com.ipdev.common.entity.patent;

import java.util.Date;

public class PatentTransferRecord extends PatentRecordBase {
	private static final long serialVersionUID = 1L;

	private String beforeTransAp; // 变更前权利人
	private String afterTransAp; // 变更后权利人
	private String currentAp; // 当前权利人
	private String beforeTransAddr; // 变更前地址
	private String afterTransAddr; // 变更后地址
	private String currentAddr; // 当前地址
	private String areaCode; // 区域代码
	private Date effectiveDate; // 生效日

	public String getBeforeTransAp() {
		return beforeTransAp;
	}

	public void setBeforeTransAp(String beforeTransAp) {
		this.beforeTransAp = beforeTransAp;
	}

	public String getAfterTransAp() {
		return afterTransAp;
	}

	public void setAfterTransAp(String afterTransAp) {
		this.afterTransAp = afterTransAp;
	}

	public String getCurrentAp() {
		return currentAp;
	}

	public void setCurrentAp(String currentAp) {
		this.currentAp = currentAp;
	}

	public String getBeforeTransAddr() {
		return beforeTransAddr;
	}

	public void setBeforeTransAddr(String beforeTransAddr) {
		this.beforeTransAddr = beforeTransAddr;
	}

	public String getAfterTransAddr() {
		return afterTransAddr;
	}

	public void setAfterTransAddr(String afterTransAddr) {
		this.afterTransAddr = afterTransAddr;
	}

	public String getCurrentAddr() {
		return currentAddr;
	}

	public void setCurrentAddr(String currentAddr) {
		this.currentAddr = currentAddr;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

}
