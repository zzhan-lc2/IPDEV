package com.ipdev.cnipr.entity.patent;

import java.util.List;

import com.google.common.collect.Lists;

public enum CNIPRDBS {
    FMZL("中国发明专利"),
    FMSQ("中国发明授权"),
    SYXX("中国实用新型"),
    WGZL("中国外观专利"),
    FLZT("法律状态"),
    TWZL("中国台湾专利"),
    HKPATENT("香港特区"),
    USPATENT("美国"),
    JPPATENT("日本"),
    GBPATENT("英国"),
    DEPATENT("德国"),
    FRPATENT("法国"),
    EPPATENT("EPO"),
    WOPATENT("WIPO"),
    CHPATENT("瑞士"),
    KRPATENT("韩国"),
    RUPATENT("俄罗斯"),
    ASPATENT("东南亚"),
    GCPATENT("阿拉伯"),
    AUPATENT("澳大利亚"),
    CAPATENT("加拿大"),
    ESPATENT("西班牙"),
    ATPATENT("奥地利"),
    ITPATENT("意大利"),
    APPATENT("非洲地区"),
    SEPATENT("瑞典"),
    OTHERPATENT("其他国家和地区")
    ;
    
    String description;
    private CNIPRDBS(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return this.description;
    }

    public static List<CNIPRDBS> getAllCN() {
        return Lists.newArrayList(FMZL, FMSQ, SYXX, WGZL); // what's inside FLZT?
    }
}
