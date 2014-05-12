package com.ipdev.cnipr.manager;

import java.util.List;

import com.ipdev.cnipr.entity.patent.IpcInfoNode;

public interface IPCManager {

    List<IpcInfoNode> getIpcTreeByKey(String ipcKey, int maxNodes);
}
