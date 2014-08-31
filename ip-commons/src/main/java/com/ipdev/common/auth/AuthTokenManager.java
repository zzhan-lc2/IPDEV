package com.ipdev.common.auth;

public interface AuthTokenManager {

	String getAccessCode();
	
	AuthToken getAuthToken();
	
	AuthToken getRefreshToken(AuthToken origAccessToken);
}
