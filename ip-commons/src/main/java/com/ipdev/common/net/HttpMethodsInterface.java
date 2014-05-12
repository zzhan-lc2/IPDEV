package com.ipdev.common.net;

import java.util.List;

import org.apache.http.NameValuePair;

public interface HttpMethodsInterface {

	String post(String url, List<NameValuePair> nvps);
	
	String get(String url, List<NameValuePair> nvps);
}
