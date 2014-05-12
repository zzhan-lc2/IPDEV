package com.ipdev.cnipr.entity.method;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.google.common.collect.Sets;

public class Sf1Request extends ExpRequest {
	private static final long serialVersionUID = 1L;
	
    public static int MAX_SEARCH_RESULTS = 50;

    // from与to的值相差不能大于50，大于50返回错误
    private String orderBy; // 字段前加"+"表示升序，加"-"表示降序，例子如下： 加号 "+申请日"：按照申请日排序升序； 减号 "-申请日"：按照申请日排序降序
	private boolean ascOrder = true;
    private int option = 1; // 1 or 2: 1-按词检索; 2-按字检索
	private Set<String> dbs = Sets.newHashSet();
	private Set<String> displayCols = Sets.newHashSet();

	public Sf1Request() {
		super("search/sf1", CallType.POST, Sf1Response.class);
	}
	
	public boolean isAscOrder() {
		return ascOrder;
	}

	public void setAscOrder(boolean ascOrder) {
		this.ascOrder = ascOrder;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrderBy() {
		return orderBy;
	}
	
	public String getOrderByDirection() {
		if (ascOrder) {
			return "+" + orderBy;
		} else {
			return "-" + orderBy;
		}
	}

	public void setOrderBy(String orderBy, boolean ascOrder) {
		this.orderBy = orderBy;
		this.ascOrder = ascOrder;
	}

	public int getOption() {
		return option;
	}

	public void setOption(int option) {
		this.option = option;
	}

	public Set<String> getDbs() {
		return dbs;
	}

	public void setDbs(Set<String> dbs) {
		this.dbs = dbs;
	}
	
	public void addDb(String db) {
		this.dbs.add(db);
	}
	
	public Set<String> getDisplayCols() {
		return displayCols;
	}

	public void setDisplayCols(Set<String> displayCols) {
		this.displayCols = displayCols;
	}
	
	public String getDisplayColsStr() {
		if (displayCols==null) return "";
		StringBuilder sb = new StringBuilder();
		boolean init = true;
		for (String col : displayCols) {
			if (init) {
				sb.append(col);
				init = false;
			} else {
				sb.append(",").append(col);
			}
		}
		return sb.toString();
	}
	
	@Override
    public void populateNameValuePairs(List<NameValuePair> nvps) {
		super.populateNameValuePairs(nvps);
		
		for (String db : this.getDbs()) {
			nvps.add(new BasicNameValuePair("dbs", db));
		}
		if (StringUtils.isNotEmpty(this.getOrderBy())) {
			nvps.add(new BasicNameValuePair("order", this.getOrderByDirection()));
		}
		nvps.add(new BasicNameValuePair("option", Integer.toString(this.getOption())));   
		nvps.add(new BasicNameValuePair("displayCols", this.getDisplayColsStr()));   
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
