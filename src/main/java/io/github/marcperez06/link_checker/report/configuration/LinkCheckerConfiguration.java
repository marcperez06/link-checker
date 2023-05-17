package io.github.marcperez06.link_checker.report.configuration;

import java.util.List;

public class LinkCheckerConfiguration {
	
	private Integer minDepth;
	private Integer minRequests;
	private Integer minInteractions;
	private List<String> domainWithelist;
	private boolean sortNotFoundFirst;
	private int numThreads;
	private String outputReportPath;
	private String baseReportName;
	private boolean cleanLinksNotVisited;
	
	public LinkCheckerConfiguration() {
		this.minDepth = null;
		this.minRequests = null;
		this.minInteractions = null;
		this.domainWithelist = null;
		this.sortNotFoundFirst = true;
		this.numThreads = 1;
		this.outputReportPath = null;
		this.baseReportName = null;
		this.cleanLinksNotVisited = true;
	}
	
	public Integer getMinDepth() {
		return this.minDepth;
	}

	public void setMinDepth(Integer maxDepth) {
		this.minDepth = maxDepth;
	}

	public Integer getMinRequests() {
		return this.minRequests;
	}

	public void setMinRequests(Integer maxRequests) {
		this.minRequests = maxRequests;
	}

	public Integer getMinInteractions() {
		return this.minInteractions;
	}

	public void setMinInteractions(Integer maxInteractions) {
		this.minInteractions = maxInteractions;
	}
	
	public List<String> getDomainWithelist() {
		return this.domainWithelist;
	}

	public void setDomainWithelist(List<String> domainWithelist) {
		this.domainWithelist = domainWithelist;
	}

	public boolean isSortEnabled() {
		return this.sortNotFoundFirst;
	}
	
	public void setSortNotFoundFirst(boolean sortNotFoundFirst) {
		this.sortNotFoundFirst = sortNotFoundFirst;
	}
	
	public int getNumThreads() {
		return this.numThreads;
	}

	public void setNumThreads(int numThreads) {
		this.numThreads = numThreads;
	}

	public String getOutputReportPath() {
		return this.outputReportPath;
	}

	public void setOutputReportPath(String outputReportPath) {
		this.outputReportPath = outputReportPath;
	}

	public String getBaseReportName() {
		return this.baseReportName;
	}

	public void setBaseReportName(String reportName) {
		this.baseReportName = reportName;
	}
	
	public boolean cleanLinksNotVisited() {
		return this.cleanLinksNotVisited;
	}
	
	public void setCleanLinksNotVisited(boolean cleanLinksNotVisited) {
		this.cleanLinksNotVisited = cleanLinksNotVisited;
	}

}
