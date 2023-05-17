package io.github.marcperez06.link_checker.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.github.marcperez06.java_utilities.collection.list.ListUtils;
import io.github.marcperez06.java_utilities.collection.map.MapUtils;
import io.github.marcperez06.java_utilities.strings.StringUtils;
import io.github.marcperez06.link_checker.report.configuration.LinkCheckerConfiguration;
import io.github.marcperez06.link_checker.report.configuration.factory.LinkCheckerConfigurationFactory;
import io.github.marcperez06.link_checker.report.link.LinkInfo;
import io.github.marcperez06.link_checker.report.link.LinkRelation;
import io.github.marcperez06.link_checker.report.link.comparator.LinkInfoComparator;
import io.github.marcperez06.link_checker.report.link.enums.Status;

public class LinkCheckerReport {
	
	private String firstLink;
	private String domain;
	private List<String> summaryNotFoundLinks;
	private LinkCheckerStatistics statistics;
	private Map<String, LinkInfo> linksVisited;
	private List<LinkRelation> linksNotVisited;
	private List<LinkRelation> linksCanNotChecked;
	private List<String> summaryGoodLinks;
	private List<String> summaryForbiddenLinks;
	private List<String> summaryRequestDeniedLinks;
	private List<String> summaryLinksThrownException;
	private LinkCheckerConfiguration configuration;
	
	
	public LinkCheckerReport(String link) {
		this.firstLink = (link != null) ? link : "";
		this.domain = this.buildDomain();
		this.statistics = new LinkCheckerStatistics();
		this.linksVisited = new ConcurrentHashMap<String, LinkInfo>();
		this.linksNotVisited = new ArrayList<LinkRelation>();
		this.linksCanNotChecked = new ArrayList<LinkRelation>();
		this.summaryGoodLinks = new ArrayList<String>();
		this.summaryNotFoundLinks = new ArrayList<String>();
		this.summaryForbiddenLinks = new ArrayList<String>();
		this.summaryRequestDeniedLinks = new ArrayList<String>();
		this.summaryLinksThrownException = new ArrayList<String>();
		this.configuration = LinkCheckerConfigurationFactory.createDefaultConfiguration();
		ListUtils.addObjectInList(this.linksNotVisited, new LinkRelation(null, link));
	}
	
	public LinkCheckerReport(String link, LinkCheckerConfiguration configruation) {
		this(link);
		this.configuration = configruation;
	}
	
	public synchronized String getDomain() {
		return this.domain;
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
			this.addGoodLink(link);
		} else if (linkInfo.getStatus() == Status.NOT_FOUND) {
			this.addNotFoundLink(link);
		} else if (linkInfo.getStatus() == Status.FORBIDDEN) {
			this.addForbiddenLink(link);
		} else if (linkInfo.getStatus() == Status.REQUEST_DENIED) {
			this.addRequestDeniedLink(link);
		} else if (linkInfo.getStatus() == Status.EXCEPTION) {
			this.addLinkThrownException(link);
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
	
	public synchronized List<String> getSummaryNotFoundLinks() {
		return this.summaryNotFoundLinks;
	}
	
	public synchronized void addNotFoundLink(String link) {
		ListUtils.addObjectInList(this.summaryNotFoundLinks, link);
	}
	
	public synchronized List<String> getSummaryForbiddenLinks() {
		return this.summaryForbiddenLinks;
	}
	
	public synchronized void addForbiddenLink(String link) {
		ListUtils.addObjectInList(this.summaryForbiddenLinks, link);
	}
	
	public synchronized List<String> getSummaryRequestDeniedLinks() {
		return this.summaryRequestDeniedLinks;
	}
	
	public synchronized void addRequestDeniedLink(String link) {
		ListUtils.addObjectInList(this.summaryRequestDeniedLinks, link);
	}
	
	public synchronized List<String> getSummaryLinksThrownException() {
		return this.summaryLinksThrownException;
	}
	
	public synchronized void addLinkThrownException(String link) {
		ListUtils.addObjectInList(this.summaryLinksThrownException, link);
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
	
	public synchronized int countNumNotFoundLinks() {
		int count = this.summaryNotFoundLinks.size();
		this.statistics.setNumNotFoundLinks(count);
		return count;
	}
	
	public synchronized int countNumForbiddenLinks() {
		int count = this.summaryForbiddenLinks.size();
		this.statistics.setNumForbiddenLinks(count);
		return count;
	}
	
	public synchronized int countNumRequestDeniedLinks() {
		int count = this.summaryRequestDeniedLinks.size();
		this.statistics.setNumRequestDeniedLinks(count);
		return count;
	}
	
	public synchronized int countNumLinksThrownException() {
		int count = this.summaryLinksThrownException.size();
		this.statistics.setNumLinksThrownException(count);
		return count;
	}
	
	public synchronized void setExecutionDuration(long duration) {
		this.statistics.setExecutionDurationInSeconds(duration);
	}
	
	// -------------- OTHER METHODS -------------------------------
	
	public synchronized void sortLinksVisited() {
		if (this.configuration.isSortEnabled()) {
			this.linksVisited = MapUtils.sortMapByValue(this.linksVisited, new LinkInfoComparator());
		}
	}
	
	private synchronized String buildDomain() {
		String domain = this.cleanFirstLink("https://");
		
		if (domain.isEmpty()) {
			this.cleanFirstLink("http://");
		}
		
		if (domain.isEmpty()) {
			domain = this.firstLink;
		}
		
		return domain;
	}
	
	private synchronized String cleanFirstLink(String startsWith) {
		String cleanedFirstLink = "";
		if (this.firstLink.startsWith(startsWith)) {
			cleanedFirstLink = StringUtils.cutStartingPartOfString(this.firstLink, startsWith);
			List<String> partsOfLink = StringUtils.splitList(cleanedFirstLink, "\\/");
			cleanedFirstLink = partsOfLink.size() > 0 ? partsOfLink.get(0) : "";
		}
		return cleanedFirstLink;
	}

}