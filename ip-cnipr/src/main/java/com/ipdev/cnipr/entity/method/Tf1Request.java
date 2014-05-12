package com.ipdev.cnipr.entity.method;

import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.google.common.base.Preconditions;

public class Tf1Request extends CniprRequest {
	private static final long serialVersionUID = 1L;

	private String type; // 翻译参数类型; i.e: cn2en
	private String content; // 要翻译的内容

	public Tf1Request() {
		super("translate/tf1", CallType.POST, Tf1Response.class);
		this.setMethodDescription("文本翻译接口");
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public void populateNameValuePairs(List<NameValuePair> nvps) {
		Preconditions.checkNotNull(nvps, "nvps cannot be null");

		nvps.add(new BasicNameValuePair("type", this.getType()));
		nvps.add(new BasicNameValuePair("content", this.getContent()));
	}

}
