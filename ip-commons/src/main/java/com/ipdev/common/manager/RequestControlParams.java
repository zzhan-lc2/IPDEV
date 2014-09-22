package com.ipdev.common.manager;

import java.util.Set;

import com.google.common.collect.Sets;
import com.ipdev.common.entity.method.Request;
import com.ipdev.common.query.OrderExp;

public class RequestControlParams extends Request {
    private static final long serialVersionUID = 1L;

    Set<String> sourceDbs = Sets.newHashSet();
    int maxResults; // 0 means no limit; if time & resource is precious, set reasonable value to limit the usage
    boolean forceUpdate; // if set to true, we will update the patents no matter if the status change or not
    OrderExp orderExp; // search return "order by" expression
    String fileBaseDir; // root directory for file-based storage
    boolean enableFileStorage; // flag to control if we want to turn on the file storage

    public RequestControlParams() {
        maxResults = 0;
        forceUpdate = false;
        enableFileStorage = false;
    }

    public RequestControlParams addSourceDb(String sourceDb) {
        this.sourceDbs.add(sourceDb);
        return this;
    }

    public RequestControlParams withMaxResults(int maxResults) {
        this.maxResults = maxResults;
        return this;
    }

    public RequestControlParams withForceUpdate(boolean forceUpdate) {
        this.forceUpdate = forceUpdate;
        return this;
    }

    public RequestControlParams withEnableFileStorage(boolean enableFileStorage) {
        this.enableFileStorage = enableFileStorage;
        return this;
    }

    public RequestControlParams withFileBaseDir(String fileBaseDir) {
        this.fileBaseDir = fileBaseDir;
        return this;
    }

    public RequestControlParams withOrderExp(OrderExp orderExp) {
        this.orderExp = orderExp;
        return this;
    }

    public Set<String> getSourceDbs() {
        return this.sourceDbs;
    }

    public int getMaxResults() {
        return this.maxResults;
    }

    public boolean getForceUpdate() {
        return this.forceUpdate;
    }

    public boolean getEnableFileStorage() {
        return this.enableFileStorage;
    }

    public String getFileBaseDir() {
        return this.fileBaseDir;
    }

    public OrderExp getOrderExp() {
        return this.orderExp;
    }
}
