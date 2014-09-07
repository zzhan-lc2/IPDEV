package com.ipdev.architect;

public final class AppConfig {
    public static enum Domain {
        PROD,
        TEST
    }

    static boolean initialized_;
    static Domain domain_;

    public static void setDomain(Domain domain) {
        domain_ = domain;
    }

    public static Domain getDomain() {
        return domain_;
    }

    public static boolean isInitialized() {
        return initialized_;
    }

    public static void initialize(String appName, String appGroupName, String[] args) {
        if (initialized_)
            return;
        // TODO
        initialized_ = true;
    }
}
