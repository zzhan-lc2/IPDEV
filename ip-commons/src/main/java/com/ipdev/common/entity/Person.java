package com.ipdev.common.entity;

import java.io.Serializable;
import java.util.List;

import javax.xml.registry.infomodel.PostalAddress;

import com.google.common.collect.Lists;
import com.ipdev.common.entity.business.Company;
import com.ipdev.common.entity.geo.Phone;

public class Person implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private PersonName name;
	private PostalAddress address;
	private List<Phone> phones = Lists.newArrayList();
	private Company company;
	
	public PersonName getName() {
		return name;
	}

	public void setName(PersonName name) {
		this.name = name;
	}

	public PostalAddress getAddress() {
		return address;
	}

	public void setAddress(PostalAddress address) {
		this.address = address;
	}

	public List<Phone> getPhones() {
		return phones;
	}

	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	
}
