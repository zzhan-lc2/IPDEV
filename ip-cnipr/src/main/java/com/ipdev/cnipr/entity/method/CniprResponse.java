package com.ipdev.cnipr.entity.method;

import java.util.List;

import com.ipdev.cnipr.entity.patent.SectionInfo;
import com.ipdev.common.entity.method.Response;

public class CniprResponse<T> extends Response {
	private static final long serialVersionUID = 1L;

	private int status;
	private String message;
	private int total;
	private int from;
	private int to;
	private List<T> results;
	private List<SectionInfo> sectionInfos; // 命中记录分布的数据库信息:
											// 两个字段：sectionName数据库名称和recordNum命中数量

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}

	public List<T> getResults() {
		return results;
	}

	public void setResults(List<T> results) {
		this.results = results;
	}

	public List<SectionInfo> getSectionInfos() {
		return sectionInfos;
	}

	public void setSectionInfos(List<SectionInfo> sectionInfos) {
		this.sectionInfos = sectionInfos;
	}

}
