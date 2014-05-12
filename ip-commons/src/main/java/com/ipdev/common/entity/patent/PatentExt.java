package com.ipdev.common.entity.patent;

import java.util.Date;

public class PatentExt extends Patent {
	private static final long serialVersionUID = 1L;

    private String cl; // 主权项
    private String patentWords; // 关键词
    private String autoAbs; // 自动摘要
    private String claimsPath; // 权利要求书
    private String instrPath; // 说明书
    private String instrTif; // 说明书附图
    private String censor; // 审查员
    private String refDoc; // 参考文献
    private Date issueDate; // 颁证日
    private String initMainIpc; // 本国主分类号
    private String initIpc; // 本国分类号
    private String divideInitAppNo; // 分案原申请号

	public String getCl() {
		return cl;
	}

	public void setCl(String cl) {
		this.cl = cl;
	}

	public String getPatentWords() {
		return patentWords;
	}

	public void setPatentWords(String patentWords) {
		this.patentWords = patentWords;
	}

	public String getAutoAbs() {
		return autoAbs;
	}

	public void setAutoAbs(String autoAbs) {
		this.autoAbs = autoAbs;
	}

	public String getClaimsPath() {
		return claimsPath;
	}

	public void setClaimsPath(String claimsPath) {
		this.claimsPath = claimsPath;
	}

	public String getInstrPath() {
		return instrPath;
	}

	public void setInstrPath(String instrPath) {
		this.instrPath = instrPath;
	}

	public String getInstrTif() {
		return instrTif;
	}

	public void setInstrTif(String instrTif) {
		this.instrTif = instrTif;
	}

	public String getCensor() {
		return censor;
	}

	public void setCensor(String censor) {
		this.censor = censor;
	}

	public String getRefDoc() {
		return refDoc;
	}

	public void setRefDoc(String refDoc) {
		this.refDoc = refDoc;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public String getInitMainIpc() {
		return initMainIpc;
	}

	public void setInitMainIpc(String initMainIpc) {
		this.initMainIpc = initMainIpc;
	}

	public String getInitIpc() {
		return initIpc;
	}

	public void setInitIpc(String initIpc) {
		this.initIpc = initIpc;
	}

	public String getDivideInitAppNo() {
		return divideInitAppNo;
	}

	public void setDivideInitAppNo(String divideInitAppNo) {
		this.divideInitAppNo = divideInitAppNo;
	}

	
}
