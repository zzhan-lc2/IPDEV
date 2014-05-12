package com.ipdev.cnipr.entity.patent;

import java.io.Serializable;
import java.util.List;

public class CorpInfosAggr implements Serializable {
	private static final long serialVersionUID = 1L;

	private String corpCode;
	private int corpTotal;
	private int corpCodeTotal;
	private List<CorpInfo> corpList;
	private List<CorpCodeInfo> corpCodeList;

	public String getCorpCode() {
		return corpCode;
	}

	public void setCorpCode(String corpCode) {
		this.corpCode = corpCode;
	}

	public int getCorpTotal() {
		return corpTotal;
	}

	public void setCorpTotal(int corpTotal) {
		this.corpTotal = corpTotal;
	}

	public int getCorpCodeTotal() {
		return corpCodeTotal;
	}

	public void setCorpCodeTotal(int corpCodeTotal) {
		this.corpCodeTotal = corpCodeTotal;
	}

	public List<CorpInfo> getCorpList() {
		return corpList;
	}

	public void setCorpList(List<CorpInfo> corpList) {
		this.corpList = corpList;
	}

	public List<CorpCodeInfo> getCorpCodeList() {
		return corpCodeList;
	}

	public void setCorpCodeList(List<CorpCodeInfo> corpCodeList) {
		this.corpCodeList = corpCodeList;
	}

}
