package com.ipdev.cnipr.entity.patent;

import java.io.Serializable;

public class CorpInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String corpname; // 公司名称
	private String corpcode; // 公司代码
	private String corpcode_topap; // 根节点公司代码

	public String getCorpname() {
		return corpname;
	}

	public void setCorpname(String corpname) {
		this.corpname = corpname;
	}

	public String getCorpcode() {
		return corpcode;
	}

	public void setCorpcode(String corpcode) {
		this.corpcode = corpcode;
	}

	public String getCorpcode_topap() {
		return corpcode_topap;
	}

	public void setCorpcode_topap(String corpcode_topap) {
		this.corpcode_topap = corpcode_topap;
	}

}
