package com.ipdev.cnipr.entity.method;

public class Sf10Request extends ExpRequest {
	private static final long serialVersionUID = 1L;

	public Sf10Request() {
		super("search/sf10", CallType.POST, Sf10Response.class);
		this.setMethodDescription("专利质押审查检索接口");
	}
}
