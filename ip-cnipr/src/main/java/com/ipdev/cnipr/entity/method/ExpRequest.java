package com.ipdev.cnipr.entity.method;

import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public abstract class ExpRequest extends CniprRequest {
	private static final long serialVersionUID = 1L;

	private String expression; // 检索表达式, i.e: 申请号=CN2009%
	private int from = 0; // 0（从第一条开始取，包括第一条），不能为负数
	private int to = 49; // from与to的值相差不能大于50，大于50返回错误 (??)

	protected ExpRequest(String method, CallType callType, Class<? extends CniprResponse> responseType) {
		super(method, callType, responseType);
	}
	
	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
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
	
	public void populateNameValuePairs(List<NameValuePair> nvps) {
		nvps.add(new BasicNameValuePair("exp", this.getExpression()));   // such as: "申请号=CN2009%"
		nvps.add(new BasicNameValuePair("from", Integer.toString(this.getFrom())));   
		nvps.add(new BasicNameValuePair("to", Integer.toString(this.getTo())));   
	}
}
