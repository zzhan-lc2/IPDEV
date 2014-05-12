package com.ipdev.common.entity.method;

import java.io.Serializable;

public class RequestResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	String method;
	Request request;
	Response response;
	Long timeUsageInMs;

	public RequestResponse() {
		this(null, null, null);
	}
	
	public RequestResponse(String method, Request request, Response response) {
		this.method = method;
		this.request = request;
		this.response = response;
		this.getTimeUsageInMs();
	}
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}
	
	public Long getTimeUsageInMs() {
		if (timeUsageInMs==null && null!=response && response.getCreationDate()!=null) {
			timeUsageInMs = response.getCreationDate().getTime() - request.getCreationDate().getTime();
		}
		return timeUsageInMs;
	}
	
	public void setTimeUsageInMs(Long timeUsageInMs) {
		this.timeUsageInMs = timeUsageInMs;
	}

}
