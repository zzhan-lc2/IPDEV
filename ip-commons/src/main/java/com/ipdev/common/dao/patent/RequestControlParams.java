package com.ipdev.common.dao.patent;

import java.util.Set;

import com.google.common.collect.Sets;
import com.ipdev.common.entity.method.Request;
import com.ipdev.common.query.OrderExp;

public class RequestControlParams extends Request {
    private static final long serialVersionUID = 1L;

    Set<String> sourceDbs = Sets.newHashSet();
    int fromIndex = 0;
    int toIndex;
    boolean forceUpdate; // if set to true, we will update the patents no matter if the status change or not
    OrderExp orderExp; // search return "order by" expression
    String fileBaseDir; // root directory for file-based storage
    boolean enableFileStorage; // flag to control if we want to turn on the file storage

    public RequestControlParams() {
        forceUpdate = false;
        enableFileStorage = false;
    }

    public RequestControlParams addSourceDb(String sourceDb) {
        this.sourceDbs.add(sourceDb);
        return this;
    }

    public RequestControlParams withFromIndex(int fromIndex) {
        this.fromIndex = fromIndex;
        return this;
    }

    public RequestControlParams withToIndex(int toIndex) {
        this.toIndex = toIndex;
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

    public int getFromIndex() {
        return this.fromIndex;
    }

    public int getToIndex() {
        return this.toIndex;
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
