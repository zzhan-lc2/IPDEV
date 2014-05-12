package com.ipdev.common.entity.patent;

import java.io.Serializable;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class PatentRecordBase implements Serializable {
	private static final long serialVersionUID = 1L;

	@SerializedName("an")
	private String appNumber; // 申请号
	private String appliantInfo; // 申请信息
	private String strStatusInfo; // 法律状态信息
	private String strLegalStatus; // 法律状态
	@SerializedName("strLegalStatusDay")
	private Date legalStatusDay; // 法律状态公告日
	private String eventCode; // 事务代码
	@SerializedName("ti")
	private String title; // 名称
	private String ab; // 摘要
	private String cl; // 主权项
	private String ipc; // 分类号
	private String type; // 转移类型

	public String getAppNumber() {
		return appNumber;
	}

	public void setAppNumber(String appNumber) {
		this.appNumber = appNumber;
	}

	public String getAppliantInfo() {
		return appliantInfo;
	}

	public void setAppliantInfo(String appliantInfo) {
		this.appliantInfo = appliantInfo;
	}

	public String getStrStatusInfo() {
		return strStatusInfo;
	}

	public void setStrStatusInfo(String strStatusInfo) {
		this.strStatusInfo = strStatusInfo;
	}

	public String getStrLegalStatus() {
		return strLegalStatus;
	}

	public void setStrLegalStatus(String strLegalStatus) {
		this.strLegalStatus = strLegalStatus;
	}

	public Date getLegalStatusDay() {
		return legalStatusDay;
	}

	public void setLegalStatusDay(Date legalStatusDay) {
		this.legalStatusDay = legalStatusDay;
	}

	public String getEventCode() {
		return eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAb() {
		return ab;
	}

	public void setAb(String ab) {
		this.ab = ab;
	}

	public String getCl() {
		return cl;
	}

	public void setCl(String cl) {
		this.cl = cl;
	}

	public String getIpc() {
		return ipc;
	}

	public void setIpc(String ipc) {
		this.ipc = ipc;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	

}
