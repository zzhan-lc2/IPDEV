package com.ipdev.common.entity.patent;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Patent implements Serializable {
    private static final long serialVersionUID = 1L;

    private String pid; // 专利信息id
    private String sysId;
    private List<String> ipc; // 分类号
    private String title; // 名称
    private String appNumber; // 申请号
    private String pubNumber; // 公开（公告）号
    private Date appDate; // 申请日
    private Date pubDate; // 公开（公告）日
    private String agencyName; // 专利代理机构
    private List<String> agentName; // 代理人
    private String addProvince;
    private String addrCity;
    private String addrCounty;
    private String address;
    private int patType; // 专利类型: 3-外观专利
    private String iapp; // 国际申请
    private String ipub; // 国际公布
    private Date den; // 进入国家日期
    private String abs; // 摘要
    private String lprs; // 最新法律状态
    private String draws; // 摘要附图存储路径: 外观专利，无摘要附图，此字段无意义
    private List<String> applicantName; // 申请（专利权）人
    private List<String> inventroName; // 发明（设计）人
    private List<String> priority; // 优先权
    private List<String> family; // 优同族专利项权
    private String tifDistributePath; // 发布路径, 全文图片
    private Integer pages; // 对应tifDistributePath的图片张数
    private String relevance; // 相似度
    private String gazettePath; // 公报发布路径
    private Integer gazettePage; // 公报所在页
    private Integer gazetteCount; // 公报翻页信息: 0代表不翻页，其他数字代表翻的页数
    private String proCode; // 国省代码
    private String appCoun; // 申请国代码

    // 更详细
    private String cl; // 主权项
    private String patentWords; // 关键词
    private String autoAbs; // 自动摘要
    private String claimsPath; // 权利要求书
    private String instrPath; // 说明书
    private String instrTif; // 说明书附图
    private String censor; // 审查员
    private String refDoc; // 参考文献
    private Date issueDate; // 颁证日
    private String initMainIpc; // 本国主分类号
    private String initIpc; // 本国分类号
    private String divideInitAppNo; // 分案原申请号

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getIpc() {
        return ipc;
    }

    public void setIpc(List<String> ipc) {
        this.ipc = ipc;
    }

    public Date getAppDate() {
        return this.appDate;
    }

    public void setAppDate(Date appDate) {
        this.appDate = appDate;
    }

    public Date getPubDate() {
        return this.pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public String getAppNumber() {
        return appNumber;
    }

    public void setAppNumber(String appNumber) {
        this.appNumber = appNumber;
    }

    public String getPubNumber() {
        return pubNumber;
    }

    public void setPubNumber(String pubNumber) {
        this.pubNumber = pubNumber;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getAddProvince() {
        return addProvince;
    }

    public void setAddProvince(String addProvince) {
        this.addProvince = addProvince;
    }

    public String getAddrCity() {
        return addrCity;
    }

    public void setAddrCity(String addrCity) {
        this.addrCity = addrCity;
    }

    public String getAddrCounty() {
        return addrCounty;
    }

    public void setAddrCounty(String addrCounty) {
        this.addrCounty = addrCounty;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPatType() {
        return patType;
    }

    public void setPatType(int patType) {
        this.patType = patType;
    }

    public String getIapp() {
        return iapp;
    }

    public void setIapp(String iapp) {
        this.iapp = iapp;
    }

    public String getIpub() {
        return ipub;
    }

    public void setIpub(String ipub) {
        this.ipub = ipub;
    }

    public Date getDen() {
        return den;
    }

    public void setDen(Date den) {
        this.den = den;
    }

    public String getAbs() {
        return abs;
    }

    public void setAbs(String abs) {
        this.abs = abs;
    }

    public String getLprs() {
        return lprs;
    }

    public void setLprs(String lprs) {
        this.lprs = lprs;
    }

    public String getDraws() {
        return draws;
    }

    public void setDraws(String draws) {
        this.draws = draws;
    }

    public List<String> getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(List<String> applicantName) {
        this.applicantName = applicantName;
    }

    public List<String> getInventroName() {
        return inventroName;
    }

    public void setInventroName(List<String> inventroName) {
        this.inventroName = inventroName;
    }

    public List<String> getPriority() {
        return priority;
    }

    public void setPriority(List<String> priority) {
        this.priority = priority;
    }

    public List<String> getFamily() {
        return family;
    }

    public void setFamily(List<String> family) {
        this.family = family;
    }

    public List<String> getAgentName() {
        return agentName;
    }

    public void setAgentName(List<String> agentName) {
        this.agentName = agentName;
    }

    public String getTifDistributePath() {
        return tifDistributePath;
    }

    public void setTifDistributePath(String tifDistributePath) {
        this.tifDistributePath = tifDistributePath;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public String getRelevance() {
        return relevance;
    }

    public void setRelevance(String relevance) {
        this.relevance = relevance;
    }

    public String getGazettePath() {
        return gazettePath;
    }

    public void setGazettePath(String gazettePath) {
        this.gazettePath = gazettePath;
    }

    public Integer getGazettePage() {
        return gazettePage;
    }

    public void setGazettePage(Integer gazettePage) {
        this.gazettePage = gazettePage;
    }

    public Integer getGazetteCount() {
        return gazetteCount;
    }

    public void setGazetteCount(Integer gazetteCount) {
        this.gazetteCount = gazetteCount;
    }

    public String getProCode() {
        return proCode;
    }

    public void setProCode(String proCode) {
        this.proCode = proCode;
    }

    public String getAppCoun() {
        return appCoun;
    }

    public void setAppCoun(String appCoun) {
        this.appCoun = appCoun;
    }

    public String getCl() {
        return cl;
    }

    public void setCl(String cl) {
        this.cl = cl;
    }

    public String getPatentWords() {
        return patentWords;
    }

    public void setPatentWords(String patentWords) {
        this.patentWords = patentWords;
    }

    public String getAutoAbs() {
        return autoAbs;
    }

    public void setAutoAbs(String autoAbs) {
        this.autoAbs = autoAbs;
    }

    public String getClaimsPath() {
        return claimsPath;
    }

    public void setClaimsPath(String claimsPath) {
        this.claimsPath = claimsPath;
    }

    public String getInstrPath() {
        return instrPath;
    }

    public void setInstrPath(String instrPath) {
        this.instrPath = instrPath;
    }

    public String getInstrTif() {
        return instrTif;
    }

    public void setInstrTif(String instrTif) {
        this.instrTif = instrTif;
    }

    public String getCensor() {
        return censor;
    }

    public void setCensor(String censor) {
        this.censor = censor;
    }

    public String getRefDoc() {
        return refDoc;
    }

    public void setRefDoc(String refDoc) {
        this.refDoc = refDoc;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public String getInitMainIpc() {
        return initMainIpc;
    }

    public void setInitMainIpc(String initMainIpc) {
        this.initMainIpc = initMainIpc;
    }

    public String getInitIpc() {
        return initIpc;
    }

    public void setInitIpc(String initIpc) {
        this.initIpc = initIpc;
    }

    public String getDivideInitAppNo() {
        return divideInitAppNo;
    }

    public void setDivideInitAppNo(String divideInitAppNo) {
        this.divideInitAppNo = divideInitAppNo;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
            ToStringStyle.MULTI_LINE_STYLE);
    }
}
