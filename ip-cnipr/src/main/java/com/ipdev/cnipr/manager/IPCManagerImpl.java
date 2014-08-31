package com.ipdev.cnipr.manager;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.ipdev.cnipr.dao.patent.PatentSearchByCNIPRDao;
import com.ipdev.cnipr.entity.method.If1Request;
import com.ipdev.cnipr.entity.method.If2Request;
import com.ipdev.cnipr.entity.method.IfxResponse;
import com.ipdev.cnipr.entity.patent.IpcInfo;
import com.ipdev.cnipr.entity.patent.IpcInfoNode;

public class IPCManagerImpl implements IPCManager {

    @Autowired
    PatentSearchByCNIPRDao cniprDao;

    public PatentSearchByCNIPRDao getCniprDao() {
        return this.cniprDao;
    }

    @Override
    public List<IpcInfoNode> getIpcTreeByKey(String ipcKey, int maxNodes) {
        List<IpcInfoNode> results = Lists.newArrayList();

        IfxResponse response = null;
        if (StringUtils.isEmpty(ipcKey)) {
            If1Request request = new If1Request();
            response = cniprDao.getRootByIpc(request);
        } else {
            If2Request request = new If2Request();
            request.setKey(ipcKey);
            response = cniprDao.getChildrenByIpc(request);
        }

        LinkedList<IpcInfoNode> queue = Lists.newLinkedList();
        int counter = 0;
        for (IpcInfo info : response.getResults()) {
            IpcInfoNode pNode = convert(info);
            results.add(pNode);
            if (info.isHasChild()) {
                queue.add(pNode);
            }
            counter++;
        }

        while (!queue.isEmpty()) {
            if (maxNodes > 0 && counter >= maxNodes) {
                break;
            }

            IpcInfoNode node = queue.pop();
            List<IpcInfoNode> subs = getIpcChildren(node);
            queue.addAll(subs);
            counter += subs.size();
        }

        return results;
    }

    List<IpcInfoNode> getIpcChildren(IpcInfoNode parent) {
        List<IpcInfoNode> addToQueue = Lists.newArrayList();
        If2Request request = new If2Request();
        request.setKey(parent.getIpc());
        IfxResponse response = cniprDao.getChildrenByIpc(request);
        for (IpcInfo info : response.getResults()) {
            IpcInfoNode node = convert(info);
            parent.addChild(node);
            if (info.isHasChild()) {
                addToQueue.add(node);
            }
        }
        return addToQueue;
    }

    IpcInfoNode convert(IpcInfo info) {
        IpcInfoNode node = new IpcInfoNode();
        node.setIpc(info.getIpc());
        node.setNote(info.getNote());
        return node;
    }

}
