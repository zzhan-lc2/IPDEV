package com.ipdev.common.entity.patent;

import java.io.Serializable;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class PatentPledge extends PatentRecordBase {
	private static final long serialVersionUID = 1L;

	// type // 质押保全类型

	private String pledgor; // 出质人
	private String pledgee; // 质权人
	private String curPledgee; // 当前质权人
	private String contractStatus; // 合同状态
	private String contractRegistration; // 合同登记号
	private Date effectiveDate; // 生效日
	private Date changeDate; // 变更日
	private Date calcelDate; // 解除日

	public String getPledgor() {
		return pledgor;
	}

	public void setPledgor(String pledgor) {
		this.pledgor = pledgor;
	}

	public String getPledgee() {
		return pledgee;
	}

	public void setPledgee(String pledgee) {
		this.pledgee = pledgee;
	}

	public String getCurPledgee() {
		return curPledgee;
	}

	public void setCurPledgee(String curPledgee) {
		this.curPledgee = curPledgee;
	}

	public String getContractStatus() {
		return contractStatus;
	}

	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}

	public String getContractRegistration() {
		return contractRegistration;
	}

	public void setContractRegistration(String contractRegistration) {
		this.contractRegistration = contractRegistration;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Date getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}

	public Date getCalcelDate() {
		return calcelDate;
	}

	public void setCalcelDate(Date calcelDate) {
		this.calcelDate = calcelDate;
	}

}
