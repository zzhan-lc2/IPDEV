package com.ipdev.common.entity.geo.cn;

public enum CnProvince {
    BJC("北京市"),
    TJC("天津市"),
    CQC("重庆市"),
    SHC("上海市"),
    HES("河北省"),
    SGS("山西省"),
    LNS("辽宁省"),
    JLS("吉林省"),
    HLJS("黑龙江省"),
    JSS("江苏省"),
    ZJS("浙江省"),
    AHS("安徽省"),
    FJS("福建省"),
    JXS("江西省"),
    SDS("山东省"),
    HNS("河南省"),
    HBS("湖北省"),
    HUS("湖南省"),
    GDS("广东省"),
    HAS("海南省"),
    SCS("四川省"),
    GZS("贵州省"),
    YNS("云南省"),
    SXS("陕西省"),
    GSS("甘肃省"),
    QHS("青海省"),
    NMZ("内蒙古自治区"),
    GXZ("广西壮族自治区"),
    NXZ("宁夏回族自治区"),
    XJZ("新疆维吾尔自治区"),
    XZZ("西藏自治区"),
    TWS("台湾省");

    String name;

    private CnProvince(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
