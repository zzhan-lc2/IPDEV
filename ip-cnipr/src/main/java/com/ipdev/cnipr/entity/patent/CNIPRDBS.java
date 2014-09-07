package com.ipdev.cnipr.entity.patent;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public enum CNIPRDBS {
    FMZL("CN", "中国发明专利"),
    FMSQ("CN", "中国发明授权"),
    SYXX("CN", "中国实用新型"),
    WGZL("CN", "中国外观专利"),
    FLZT("CN", "法律状态"),
    TWZL("TW", "中国台湾专利"),
    HKPATENT("HK", "香港特区"),
    USPATENT("US", "美国"),
    JPPATENT("JP", "日本"),
    GBPATENT("UK", "英国"),
    DEPATENT("DE", "德国"),
    FRPATENT("FR", "法国"),
    EPPATENT("EP", "EPO"),
    WOPATENT("WI", "WIPO"),
    CHPATENT("CH", "瑞士"),
    KRPATENT("KR", "韩国"),
    RUPATENT("RU", "俄罗斯"),
    ASPATENT("AS", "东南亚"),
    GCPATENT("GC", "阿拉伯"),
    AUPATENT("AU", "澳大利亚"),
    CAPATENT("CA", "加拿大"),
    ESPATENT("ES", "西班牙"),
    ATPATENT("AT", "奥地利"),
    ITPATENT("IT", "意大利"),
    APPATENT("AP", "非洲地区"),
    SEPATENT("SE", "瑞典"),
    OTHERPATENT("OT", "其他国家和地区");

    String countryCode;
    String description;
    static Set<String> names_ = null;

    private CNIPRDBS(String countryCode, String description) {
        this.countryCode = countryCode;
        this.description = description;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public String getDescription() {
        return this.description;
    }

    public static List<CNIPRDBS> getAllCN() {
        return Lists.newArrayList(FMZL, FMSQ, SYXX, WGZL, FLZT); // what's inside FLZT?
    }

    static List<CNIPRDBS> getDbsByCountry(String countryCode) {
        List<CNIPRDBS> results = Lists.newArrayList();
        for (CNIPRDBS db : CNIPRDBS.values()) {
            if (db.getCountryCode().equalsIgnoreCase(countryCode)) {
                results.add(db);
            }
        }
        return results;
    }

    public static boolean isValid(String dbStr) {
        return getNames().contains(StringUtils.upperCase(dbStr));
    }

    public static Set<String> getNames() {
        if (names_ == null) {
            names_ = Sets.newHashSet();
            for (CNIPRDBS db : CNIPRDBS.values()) {
                names_.add(db.name());
            }
        }
        return names_;
    }
}
