package io.github.marcperez06.link_checker.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.github.marcperez06.java_utilities.collection.list.ListUtils;
import io.github.marcperez06.java_utilities.collection.map.MapUtils;
import io.github.marcperez06.link_checker.report.configuration.LinkCheckerConfiguration;
import io.github.marcperez06.link_checker.report.link.LinkInfo;
import io.github.marcperez06.link_checker.report.link.LinkRelation;
import io.github.marcperez06.link_checker.report.link.comparator.LinkInfoComparator;
import io.github.marcperez06.link_checker.report.link.enums.Status;

public class LinkCheckerReport {
	
	private String firstLink;
	private List<String> summaryBadLinks;
	private LinkCheckerStatistics statistics;
	private Map<String, LinkInfo> linksVisited;
	private List<LinkRelation> linksNotVisited;
	private List<LinkRelation> linksCanNotChecked;
	private List<String> summaryGoodLinks;
	private LinkCheckerConfiguration configuration;
	
	
	public LinkCheckerReport(String link) {
		this.firstLink = link;
		this.statistics = new LinkCheckerStatistics();
		this.linksVisited = new ConcurrentHashMap<String, LinkInfo>();
		this.linksNotVisited = new ArrayList<LinkRelation>();
		this.linksCanNotChecked = new ArrayList<LinkRelation>();
		this.summaryGoodLinks = new ArrayList<String>();
		this.summaryBadLinks = new ArrayList<String>();
		this.configuration = new LinkCheckerConfiguration();
		ListUtils.addObjectInList(this.linksNotVisited, new LinkRelation(null, link));
	}
	
	public LinkCheckerReport(String link, LinkCheckerConfiguration configruation) {
		this(link);
		this.configuration = configruation;
	}
	
	public synchronized String getFirstLink() {
		return this.firstLink;
	}
	
	public synchronized void setFirstLink(String link) {
		this.firstLink = link;
	}
	
	public synchronized LinkCheckerStatistics getStatistics() {
		return this.statistics;
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
			addGoodLink(link);
		} else {
			addBadLink(link);
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
	
	public synchronized List<LinkRelation> getLinksCanNotChecked() {
		return this.linksCanNotChecked;
	}
	
	public synchronized void setLinksCanNotChecked(List<LinkRelation> links) {
		this.linksCanNotChecked = links;
	}
	
	public synchronized void addLinkCanNotChecked(LinkRelation linkRelation) {
		ListUtils.addObjectInList(this.linksCanNotChecked, linkRelation);
	}
	
	public synchronized void addLinksNotValid(String fromLink, List<String> linksTo) {
		for (String to : linksTo) {
			LinkRelation relation = new LinkRelation(fromLink, to);
			this.addLinkCanNotChecked(relation);
		}
	}
	
	public synchronized List<String> getSummaryGoodLinks() {
		return this.summaryGoodLinks;
	}
	
	public synchronized void addGoodLink(String link) {
		ListUtils.addObjectInList(this.summaryGoodLinks, link);
	}
	
	public synchronized List<String> getSummaryBadLinks() {
		return this.summaryBadLinks;
	}
	
	public synchronized void addBadLink(String link) {
		ListUtils.addObjectInList(this.summaryBadLinks, link);
	}
	
	public synchronized void setConfiguration(LinkCheckerConfiguration configuration) {
		this.configuration = configuration;
	}
	
	public synchronized LinkCheckerConfiguration getConfiguration() {
		return this.configuration;
	}
	
	// ---------------------- STATISTICS ACTIONS ------------------------------
	
	public synchronized void addNumInteraction() {
		this.statistics.addNumInteractions();
	}
	
	public synchronized void addNumRequest() {
		this.statistics.addNumRequests();
	}
	
	public synchronized void addCurrentDepth() {
		this.statistics.addCurrentDepth();
	}
	
	public synchronized int countNumLinksVisited() {
		int count = this.linksVisited.size();
		this.statistics.setNumLinksVisited(count);
		return count;
	}
	
	public synchronized int countNumLinksNotVisited() {
		int count = this.linksNotVisited.size();
		this.statistics.setNumLinksNotVisited(count);
		return count;
	}
	
	public synchronized int countNumLinksCanNotChecked() {
		int count = this.linksCanNotChecked.size();
		this.statistics.setNumLinksCanNotChecked(count);
		return count;
	}
	
	public synchronized int countNumGoodLinks() {
		int count = this.summaryGoodLinks.size();
		this.statistics.setNumGoodLinks(count);
		return count;
	}
	
	public synchronized int countNumBadLinks() {
		int count = this.summaryBadLinks.size();
		this.statistics.setNumBadLinks(count);
		return count;
	}
	
	public synchronized void setExecutionDuration(long duration) {
		this.statistics.setExecutionDurationInSeconds(duration);
	}
	
	// -------------- OTHER METHODS -------------------------------
	
	public void sortLinksVisited() {
		if (this.configuration.isSortEnabled()) {
			this.linksVisited = MapUtils.sortMapByValue(this.linksVisited, new LinkInfoComparator());
		}
	}

}