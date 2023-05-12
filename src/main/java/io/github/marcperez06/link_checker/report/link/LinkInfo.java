package io.github.marcperez06.link_checker.report.link;

import java.util.ArrayList;
import java.util.List;

import io.github.marcperez06.java_utilities.collection.list.ListUtils;
import io.github.marcperez06.link_checker.report.link.enums.Status;

public class LinkInfo {
	
	private String link;
	private Status status;
	private Integer statusCode;
	private int depth;
	private List<String> entries;
	private List<String> exits;
	private String exceptionCausedBy;
	
	public LinkInfo() {
		this.statusCode = null;
		this.entries = new ArrayList<String>();
		this.exits = new ArrayList<String>();
		this.exceptionCausedBy = null;
	}
	
	public LinkInfo(String link) {
		this();
		this.link = link;
	}
	
	public LinkInfo(String link, Status status) {
		this(link);
		this.status = status;
	}
	
	public LinkInfo(String link, Status status, Integer statusCode) {
		this(link, status);
		this.statusCode = statusCode;
	}

	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	public Integer getStatusCode() {
		return this.statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}
	
	public boolean isGood() {
		return (this.status != null) ? this.status == Status.OK : false;
	}
	
	public int getDepth() {
		return this.depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public List<String> getEntries() {
		return this.entries;
	}

	public void setEntries(List<String> entries) {
		this.entries = entries;
	}
	
	public void addEntry(String link) {
		ListUtils.addObjectInList(this.entries, link);
	}

	public List<String> getExits() {
		return this.exits;
	}

	public void setExits(List<String> exits) {
		this.exits = exits;
	}
	
	public void addExit(String link) {
		ListUtils.addObjectInList(this.exits, link);
	}
	
	public String getExceptionCausedBy() {
		return this.exceptionCausedBy;
	}

	public void setExceptionCausedBy(String exception) {
		this.exceptionCausedBy = exception;
	}
	
}