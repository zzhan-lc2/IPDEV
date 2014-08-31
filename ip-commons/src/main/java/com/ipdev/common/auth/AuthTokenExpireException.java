package com.ipdev.common.auth;

import com.ipdev.common.IpdException;

public class AuthTokenExpireException extends IpdException {
	private static final long serialVersionUID = 1L;

	public AuthTokenExpireException(String message) {
		super(message);
	}
	
	public AuthTokenExpireException(String message, Throwable e) {
		super(message, e);
	}

}
