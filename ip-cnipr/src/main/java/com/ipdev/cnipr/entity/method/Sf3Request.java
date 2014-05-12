package com.ipdev.cnipr.entity.method;

public class Sf3Request extends ExpRequest {
	private static final long serialVersionUID = 1L;

	public Sf3Request() {
		super("search/sf3", CallType.POST, Sf3Response.class);
	}

}
