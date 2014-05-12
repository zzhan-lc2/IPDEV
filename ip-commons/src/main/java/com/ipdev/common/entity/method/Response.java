package com.ipdev.common.entity.method;

import java.io.Serializable;
import java.util.Date;

public abstract class Response implements Serializable {
	private static final long serialVersionUID = 1L;
	
	Date creationDate = new Date();

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	
}
