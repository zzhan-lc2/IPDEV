package com.ipdev.cnipr.entity.method;

import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.google.common.base.Preconditions;

public class Cf3Request extends CniprRequest {
	private static final long serialVersionUID = 1L;

	private String topap;	// 某公司的根节点代码
	private String code;	// 某公司的代码
	
	public Cf3Request() {
		super("corpCode/cf3", CallType.GET, Cf3Response.class);
		this.setMethodDescription("查询某公司代码的下级公司代码和下级公司信息接口");
	}

	public String getTopap() {
		return topap;
	}

	public void setTopap(String topap) {
		this.topap = topap;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public void populateNameValuePairs(List<NameValuePair> nvps) {
		Preconditions.checkNotNull(nvps, "nvps cannot be null");

		nvps.add(new BasicNameValuePair("topap", this.getTopap()));
		nvps.add(new BasicNameValuePair("code", this.getCode()));
	}

}
