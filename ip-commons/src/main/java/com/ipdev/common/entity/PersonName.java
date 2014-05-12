package com.ipdev.common.entity;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class PersonName implements Serializable {
	private static final long serialVersionUID = 1L;

	public static enum FullNameStyle {
		CHINESE,
		LAST_MIDDLE_FIRST,
		FISR_MIDDLE_LAST
	}
	
	private String prefix;	// Mr, Mrs. Ms, etc
	private String lastName;
	private String firstName;
	private String middleName;
	
	public String getPrefix() {
		return prefix;
	}
	
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getMiddleName() {
		return middleName;
	}
	
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	
	public String toFullName(FullNameStyle style) {
		// TODO
		return new StringBuilder()
			.append(StringUtils.isEmpty(prefix) ? "" : prefix+" ")
			.append(lastName)
			.append(firstName)
			.toString(); 
	}
	
	@Override
	public String toString() {
		return toFullName(FullNameStyle.FISR_MIDDLE_LAST);
	}
	
}
