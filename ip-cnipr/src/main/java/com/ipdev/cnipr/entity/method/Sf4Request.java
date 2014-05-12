package com.ipdev.cnipr.entity.method;

import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class Sf4Request extends CniprRequest {
	private static final long serialVersionUID = 1L;

	private String appnumber;	// 申请号

	public Sf4Request() {
		super("search/sf4", CallType.GET, Sf4Response.class);
	}
	
	public String getAppnumber() {
		return appnumber;
	}

	public void setAppnumber(String appnumber) {
		this.appnumber = appnumber;
	}
	
	public void populateNameValuePairs(List<NameValuePair> nvps) {
		nvps.add(new BasicNameValuePair("appnumber", this.getAppnumber()));   // such as: "CN2009%" 
	}
}
