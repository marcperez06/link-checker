package link_checker.report.configuration;

public class LinkCheckerConfiguration {
	
	private Integer maxDepth;
	private Integer maxRequests;
	private Integer maxInteractions;
	private boolean sortNotFoundFirst;
	
	public LinkCheckerConfiguration() {
		this.maxDepth = Integer.valueOf(1);
		this.maxRequests = null;
		this.maxInteractions = null;
		this.sortNotFoundFirst = true;
	}
	
	public Integer getMaxDepth() {
		return this.maxDepth;
	}

	public void setMaxDepth(Integer maxDepth) {
		this.maxDepth = maxDepth;
	}

	public Integer getMaxRequests() {
		return this.maxRequests;
	}

	public void setMaxRequests(Integer maxRequests) {
		this.maxRequests = maxRequests;
	}

	public Integer getMaxInteractions() {
		return this.maxInteractions;
	}

	public void setMaxInteractions(Integer maxInteractions) {
		this.maxInteractions = maxInteractions;
	}

	public boolean isSortEnabled() {
		return this.sortNotFoundFirst;
	}

}
