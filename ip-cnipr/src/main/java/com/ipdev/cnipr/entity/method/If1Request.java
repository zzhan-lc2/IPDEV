package com.ipdev.cnipr.entity.method;

import java.util.List;

import org.apache.http.NameValuePair;

public class If1Request extends CniprRequest {
	private static final long serialVersionUID = 1L;

	public If1Request() {
		super("ipc/if1", CallType.GET, IfxResponse.class);
		this.setMethodDescription("查询所有的根分类号");
	}
	
	@Override
	public void populateNameValuePairs(List<NameValuePair> nvps) {
		// Do nothing
	}

}
