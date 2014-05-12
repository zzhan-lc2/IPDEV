package com.ipdev.cnipr.entity.method;

public class Sf9Request extends ExpRequest {
	private static final long serialVersionUID = 1L;

	public Sf9Request() {
		super("search/sf9", CallType.POST, Sf9Response.class);
		this.setMethodDescription("专利转让检索接口");
	}
}
