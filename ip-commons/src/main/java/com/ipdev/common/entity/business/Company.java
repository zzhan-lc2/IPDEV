package com.ipdev.common.entity.business;

import java.io.Serializable;
import java.util.List;

import javax.xml.registry.infomodel.PostalAddress;

import com.google.common.collect.Lists;
import com.ipdev.common.entity.geo.Phone;

public class Company implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private PostalAddress postalAddress;
	private String name;
	private String type;	// company type
	private String description;	// 公司简介
	private List<String> websites = Lists.newArrayList();
	private List<Phone> phones = Lists.newArrayList();
	
	public PostalAddress getPostalAddress() {
		return postalAddress;
	}
	
	public void setPostalAddress(PostalAddress postalAddress) {
		this.postalAddress = postalAddress;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<String> getWebsites() {
		return websites;
	}
	
	public void setWebsites(List<String> websites) {
		this.websites = websites;
	}
	
	public List<Phone> getPhones() {
		return phones;
	}
	
	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}

		
}
