package com.ipdev.common.recorder;

import com.ipdev.common.IpdException;

public class DataRecordException extends IpdException {

	private static final long serialVersionUID = 1L;

	public DataRecordException(String message) {
		super(message);
	}
	
	public DataRecordException(String message, Throwable e) {
		super(message, e);
	}

}
