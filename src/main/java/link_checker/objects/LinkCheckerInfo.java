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
	private List<LinkRelation> linksNotVisited;
	private List<LinkRelation> linksNotValid;
	
	public LinkCheckerInfo(String link) {
		this.firstLink = link;
		this.numGoodLinks = 0;
		this.numBadLinks = 0;
		this.currentDepth = 0;
		this.wishedDepth = 0;
		this.linksVisited = new ConcurrentHashMap<String, LinkInfo>();
		this.linksNotVisited = new ArrayList<LinkRelation>();
		this.linksNotValid = new ArrayList<LinkRelation>();
		ListUtils.addObjectInList(this.linksNotVisited, new LinkRelation(null, link));
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
	
	public synchronized List<LinkRelation> getLinksNotVisited() {
		return this.linksNotVisited;
	}
	
	public synchronized void setLinksNotVisited(List<LinkRelation> links) {
		this.linksNotVisited = links;
	}
	
	public synchronized void addLinkNotVisited(LinkRelation linkRelation) {
		ListUtils.addObjectInList(this.linksNotVisited, linkRelation);
	}
	
	public synchronized void addLinksNotVisited(String fromLink, List<String> linksTo) {
		for (String to : linksTo) {
			LinkRelation relation = new LinkRelation(fromLink, to);
			this.addLinkNotVisited(relation);
		}
	}
	
	public synchronized void removeLinkNotVisited(LinkRelation linkRelation) {
		ListUtils.removeObjectInList(this.linksNotVisited, linkRelation);
	}
	
	public synchronized List<LinkRelation> getLinksNotValid() {
		return this.linksNotValid;
	}
	
	public synchronized void setLinksNotValid(List<LinkRelation> links) {
		this.linksNotValid = links;
	}
	
	public synchronized void addLinkNotValid(LinkRelation linkRelation) {
		ListUtils.addObjectInList(this.linksNotValid, linkRelation);
	}
	
	public synchronized void addLinksNotValid(String fromLink, List<String> linksTo) {
		for (String to : linksTo) {
			LinkRelation relation = new LinkRelation(fromLink, to);
			this.addLinkNotValid(relation);
		}
	}
	
	public boolean reachWishedDepth() {
		return this.currentDepth >= this.wishedDepth;
	}

}