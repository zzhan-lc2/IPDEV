package com.ipdev.common.entity.patent;

import java.io.Serializable;
import java.util.Date;

public class Article implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String title;
	private String bookOrJournalName;
	private Date pubDate;
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getBookOrJournalName() {
		return bookOrJournalName;
	}
	
	public void setBookOrJournalName(String bookOrJournalName) {
		this.bookOrJournalName = bookOrJournalName;
	}
	
	public Date getPubDate() {
		return pubDate;
	}
	
	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}
	
	
}
