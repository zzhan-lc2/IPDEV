package com.ipdev.cnipr.entity.method;

public class Sf11Request extends ExpRequest {
	private static final long serialVersionUID = 1L;

	public Sf11Request() {
		super("search/sf11", CallType.POST, Sf11Response.class);
		this.setMethodDescription("专利实施许可合同备案检索");
	}
	
	
}
