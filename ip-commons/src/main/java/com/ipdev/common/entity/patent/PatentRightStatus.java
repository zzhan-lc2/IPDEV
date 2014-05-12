package com.ipdev.common.entity.patent;

import java.io.Serializable;
import java.util.Date;

public class PatentRightStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	private String prsId; // 法律状态id: 申请号+@+法律状态公告日
	private String appId; // 申请号
	private String codeExpl; // 法律状态信息
	private String prsCode; // 法律状态
	private String prsCodeLevel1; // 第一级: reserved
	private String prsCodeLevel2; // 第二级: reserved
	private String prsCodeLevel3; // 第三级: reserved
	private Date prsDate; // 法律状态公告日
	private String prsNumber; // 法律状态申请号

	public String getPrsId() {
		return prsId;
	}

	public void setPrsId(String prsId) {
		this.prsId = prsId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getCodeExpl() {
		return codeExpl;
	}

	public void setCodeExpl(String codeExpl) {
		this.codeExpl = codeExpl;
	}

	public String getPrsCode() {
		return prsCode;
	}

	public void setPrsCode(String prsCode) {
		this.prsCode = prsCode;
	}

	public String getPrsCodeLevel1() {
		return prsCodeLevel1;
	}

	public void setPrsCodeLevel1(String prsCodeLevel1) {
		this.prsCodeLevel1 = prsCodeLevel1;
	}

	public String getPrsCodeLevel2() {
		return prsCodeLevel2;
	}

	public void setPrsCodeLevel2(String prsCodeLevel2) {
		this.prsCodeLevel2 = prsCodeLevel2;
	}

	public String getPrsCodeLevel3() {
		return prsCodeLevel3;
	}

	public void setPrsCodeLevel3(String prsCodeLevel3) {
		this.prsCodeLevel3 = prsCodeLevel3;
	}

	public Date getPrsDate() {
		return prsDate;
	}

	public void setPrsDate(Date prsDate) {
		this.prsDate = prsDate;
	}

	public String getPrsNumber() {
		return prsNumber;
	}

	public void setPrsNumber(String prsNumber) {
		this.prsNumber = prsNumber;
	}

}
