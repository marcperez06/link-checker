package io.github.marcperez06.link_checker.report.configuration;

public class LinkCheckerConfiguration {
	
	private Integer minDepth;
	private Integer minRequests;
	private Integer minInteractions;
	private boolean sortNotFoundFirst;
	private int numThreads;
	private String outputReportPath;
	private String baseReportName;
	
	public LinkCheckerConfiguration() {
		this.minDepth = null;
		this.minRequests = null;
		this.minInteractions = null;
		this.sortNotFoundFirst = true;
		this.numThreads = 1;
		this.outputReportPath = null;
		this.baseReportName = null;
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

}
