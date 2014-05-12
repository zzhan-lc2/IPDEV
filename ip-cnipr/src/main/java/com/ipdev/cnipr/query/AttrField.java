package com.ipdev.cnipr.query;

public enum AttrField {
    ADDRESS("地址"),
    APP_NUMBER("申请号"),
    APP_DATE("申请日"),
    APPLICANT("申请（专利权）人"),
    ABSTRACT("摘要"),
    PUB_NUMBER("公开（公告）号"),
    PUB_DATE("公开（公告）日"),
    INVENTOR("发明（设计）人"),
    LATEST_LEGAL("最新法律状态 "),
    IPC("分类号"),
    AGENT("代理人"),
    AGENCY("专利代理机构");

    private String name;

    private AttrField(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
