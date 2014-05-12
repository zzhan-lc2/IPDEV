package com.ipdev.cnipr.entity.patent;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;
import com.ipdev.common.utility.json.JsonSkip;

public class IpcInfoNode implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonSkip
    private IpcInfoNode parent;

    private String ipc;
    private String note;
    private List<IpcInfoNode> children = Lists.newArrayList();

    public IpcInfoNode getParent() {
        return parent;
    }

    public void setParent(IpcInfoNode parent) {
        this.parent = parent;
    }

    public String getIpc() {
        return ipc;
    }

    public void setIpc(String ipc) {
        this.ipc = ipc;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<IpcInfoNode> getChildren() {
        return children;
    }

    public void setChildren(List<IpcInfoNode> children) {
        this.children = children;
    }

    public void addChild(IpcInfoNode child) {
        if (child == null)
            return;
        child.setParent(this);
        children.add(child);
    }

}
