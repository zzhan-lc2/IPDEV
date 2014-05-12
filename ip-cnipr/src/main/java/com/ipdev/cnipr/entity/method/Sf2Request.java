package com.ipdev.cnipr.entity.method;

import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class Sf2Request extends CniprRequest {
	private static final long serialVersionUID = 1L;

	private String pid;

	public Sf2Request() {
		super("search/sf2", CallType.GET, Sf2Response.class);
	}
	
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	@Override
	public void populateNameValuePairs(List<NameValuePair> nvps) {
		nvps.add(new BasicNameValuePair("pid", this.getPid()));
	}
	
	
}
