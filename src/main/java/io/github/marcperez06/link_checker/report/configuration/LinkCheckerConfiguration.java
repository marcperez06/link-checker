package io.github.marcperez06.link_checker.report.configuration;

public class LinkCheckerConfiguration {
	
	private Integer minDepth;
	private Integer minRequests;
	private Integer minInteractions;
	private boolean sortNotFoundFirst;
	public int numThreads;
	
	public LinkCheckerConfiguration() {
		this.minDepth = null;
		this.minRequests = null;
		this.minInteractions = null;
		this.sortNotFoundFirst = true;
		this.numThreads = 1;
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

}
