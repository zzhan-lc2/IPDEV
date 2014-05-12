package com.ipdev.cnipr.entity.method;

public enum Status {
	S_0(0, "SUCCESS"),
	S_10(10, "The result is empty"),
	S_401(401,"Forbidden,No data_area"),
    S_402(402, "参数错误"),
    S_403(403, "Forbidden"),
    S_405(405, "from和to参数错误"),
    S_406(406, "翻译参数错误"),
    S_500(500, "内部错误，请联系管理员"),
    S_501(501, "处理翻译数据异常"),
    // TODO
    S_99999(99999, "其他错误")
	;
	
	private final int value;
	private final String message;
	private Status(int value, String message) {
		this.value = value;
		this.message = message;
	}
	
	public int getValue() {
		return value;
	}
	
	public String getMessage() {
		return message;
	}
}
