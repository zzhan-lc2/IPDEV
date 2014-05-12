package com.ipdev.cnipr.entity.method;

import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.google.common.base.Preconditions;

public class If2Request extends CniprRequest {
	private static final long serialVersionUID = 1L;

	private String key;

	public If2Request() {
		super("ipc/if2", CallType.GET, IfxResponse.class);
		this.setMethodDescription("根据选择的分类号，查询下级分类号信息");
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public void populateNameValuePairs(List<NameValuePair> nvps) {
		Preconditions.checkNotNull(nvps, "nvps cannot be null");

		nvps.add(new BasicNameValuePair("key", this.getKey()));
	}

}
