package link_checker.report.link;

import java.util.ArrayList;
import java.util.List;

import io.github.marcperez06.java_utilities.collection.list.ListUtils;
import link_checker.report.link.enums.Status;

public class LinkInfo {
	
	private String link;
	private Status status;
	private int depth;
	private List<String> entries;
	private List<String> exits;
	
	public LinkInfo() {
		this.entries = new ArrayList<String>();
		this.exits = new ArrayList<String>();
	}
	
	public LinkInfo(String link) {
		this();
		this.link = link;
	}
	
	public LinkInfo(String link, Status status) {
		this(link);
		this.status = status;
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
	
}