package com.ipdev.cnipr.entity.method;

import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.google.common.base.Preconditions;
import com.ipdev.cnipr.entity.method.CniprRequest.CallType;

public class If3Request extends CniprRequest {
	private static final long serialVersionUID = 1L;

	private String key;
	private int from = 0;
	private int to = 49;

	public If3Request() {
		super("ipc/if3", CallType.GET, IfxResponse.class);
		this.setMethodDescription("根据分类号关键词，查询分类号");
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}

	@Override
	public void populateNameValuePairs(List<NameValuePair> nvps) {
		Preconditions.checkNotNull(nvps, "nvps cannot be null");

		nvps.add(new BasicNameValuePair("key", this.getKey()));
		nvps.add(new BasicNameValuePair("from",
				Integer.toString(this.getFrom())));
		nvps.add(new BasicNameValuePair("to", Integer.toString(this.getTo())));
	}

}
