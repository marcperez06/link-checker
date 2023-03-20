package link_checker.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.github.marcperez06.java_utilities.collection.list.ListUtils;
import io.github.marcperez06.java_utilities.collection.map.MapUtils;
import link_checker.enums.Status;

public class LinkCheckerInfo {
	
	private String firstLink;
	private int numGoodLinks;
	private int numBadLinks;
	private int currentDepth;
	private int wishedDepth;
	private Map<String, LinkInfo> linksVisited;
	private List<String> linksNotVisited;
	
	public LinkCheckerInfo(String link) {
		this.firstLink = link;
		this.numGoodLinks = 0;
		this.numBadLinks = 0;
		this.currentDepth = 0;
		this.wishedDepth = 0;
		this.linksVisited = new ConcurrentHashMap<String, LinkInfo>();
		this.linksNotVisited = new ArrayList<String>();
		ListUtils.addObjectInList(this.linksNotVisited, link);
	}
	
	public synchronized String getFirstLink() {
		return this.firstLink;
	}
	
	public synchronized void setFirstLink(String link) {
		this.firstLink = link;
	}
	
	public synchronized int getNumGoodLinks() {
		return this.numGoodLinks;
	}
	
	public synchronized void setNumGoodLinks(int numLinks) {
		this.numGoodLinks = numLinks;
	}
	
	public synchronized void addNumGoodLinks() {
		this.numGoodLinks++;
	}
	
	public synchronized int getNumBadLinks() {
		return this.numBadLinks;
	}
	
	public synchronized void setNumBadLinks(int numLinks) {
		this.numBadLinks = numLinks;
	}
	
	public synchronized void addNumBadLinks() {
		this.numBadLinks++;
	}
	
	public synchronized int getCurrentDepth() {
		return this.currentDepth;
	}
	
	public synchronized void setCurrentDepth(int depth) {
		this.currentDepth = depth;
	}
	
	public synchronized void addCurrentDepth() {
		this.currentDepth++;
	}
	
	public synchronized int getWishedDepth() {
		return this.wishedDepth;
	}
	
	public synchronized void setWishedDepth(int depth) {
		this.wishedDepth = depth;
	}
	
	public synchronized Map<String, LinkInfo> getLinksVisited() {
		return this.linksVisited;
	}
	
	public synchronized void setLinksVisited(Map<String, LinkInfo> links) {
		this.linksVisited = links;
	}
	
	public synchronized void addLinkVisited(String link, LinkInfo linkInfo) {
		MapUtils.addObjectIfNotExistInMap(this.linksVisited, link, linkInfo);
		if (linkInfo.getStatus() == Status.OK) {
			addNumGoodLinks();
		} else {
			addNumBadLinks();
		}
	}
	
	public synchronized List<String> getLinksNotVisited() {
		return this.linksNotVisited;
	}
	
	public synchronized void setLinksNotVisited(List<String> links) {
		this.linksNotVisited = links;
	}
	
	public synchronized void addLinkNotVisited(String link) {
		ListUtils.addObjectInList(this.linksNotVisited, link);
	}
	
	public synchronized void addLinksNotVisited(List<String> link) {
		ListUtils.addObjectsInList(this.linksNotVisited, link);
	}
	
	public synchronized void removeLinkNotVisited(String link) {
		ListUtils.removeObjectInList(this.linksNotVisited, link);
	}

}