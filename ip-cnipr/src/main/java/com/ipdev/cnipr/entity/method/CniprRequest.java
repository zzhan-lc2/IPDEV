package com.ipdev.cnipr.entity.method;

import java.util.List;

import org.apache.http.NameValuePair;

import com.ipdev.common.entity.method.Request;
import com.ipdev.common.utility.json.JsonSkip;

@SuppressWarnings("rawtypes")
public abstract class CniprRequest extends Request {
	private static final long serialVersionUID = 1L;

	public static enum CallType {
		GET,
		POST
	};

	@JsonSkip
	final String method;
	@JsonSkip
	final CallType callType;
	@JsonSkip
	final Class<? extends CniprResponse> responseType;
	@JsonSkip
	String methodDescription;
	
	public CniprRequest(String method, CallType callType, Class<? extends CniprResponse> responseType) {
		this.method = method;
		this.callType = callType;
		this.responseType = responseType;
	}
	
	public void setMethodDescription(String methodDescription) {
		this.methodDescription = methodDescription;
	}
	
	public String getMethodDescription() {
		return methodDescription;
	}

	public String getMethod() {
		return method;
	}

	public CallType getCallType() {
		return callType;
	}
	
	public Class<? extends CniprResponse> getResponseType() {
		return this.responseType;
	}
	
	public abstract void populateNameValuePairs(List<NameValuePair> nvps);
}
