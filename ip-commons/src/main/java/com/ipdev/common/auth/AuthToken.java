package com.ipdev.common.auth;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class AuthToken implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String openId;
	private String openKey;
	private String accessToken;
	private Date tokenAcquireTime;
	private Date tokenExpireTime;
	private String refreshToken;
	private Date refreshTokenAcquireTime;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getOpenKey() {
		return openKey;
	}

	public void setOpenKey(String openKey) {
		this.openKey = openKey;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Date getTokenAcquireTime() {
		return tokenAcquireTime;
	}

	public void setTokenAcquireTime(Date tokenAcquireTime) {
		this.tokenAcquireTime = tokenAcquireTime;
	}

	public Date getTokenExpireTime() {
		return tokenExpireTime;
	}

	public void setTokenExpireTime(Date tokenExpireTime) {
		this.tokenExpireTime = tokenExpireTime;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public Date getRefreshTokenAcquireTime() {
		return refreshTokenAcquireTime;
	}

	public void setRefreshTokenAcquireTime(Date refreshTokenAcquireTime) {
		this.refreshTokenAcquireTime = refreshTokenAcquireTime;
	}
		
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
