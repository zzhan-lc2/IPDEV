package com.ipdev.cnipr.entity.patent;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class IpcInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String parentIpc;
	private String ipc;
	private String note;
	private boolean hasChild;

	public String getParentIpc() {
		return parentIpc;
	}

	public void setParentIpc(String parentIpc) {
		this.parentIpc = parentIpc;
	}
	
	public String getIpc() {
		return ipc;
	}

	public void setIpc(String ipc) {
		this.ipc = ipc;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public boolean isHasChild() {
		return hasChild;
	}

	public void setHasChild(boolean hasChild) {
		this.hasChild = hasChild;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	
}
