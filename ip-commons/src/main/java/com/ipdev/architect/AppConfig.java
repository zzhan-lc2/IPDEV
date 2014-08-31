package com.ipdev.architect;

public final class AppConfig {
    public static enum Domain {
        PROD,
        TEST
    }

    static Domain domain_;

    public static void setDomain(Domain domain) {
        domain_ = domain;
    }

    public static Domain getDomain() {
        return domain_;
    }
}
