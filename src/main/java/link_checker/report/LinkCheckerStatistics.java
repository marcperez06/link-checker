package link_checker.report;

public class LinkCheckerStatistics {
	
	private int numInteractions;
	private int numRequests;
	private int numLinksVisited;
	private int numLinksNotVisited;
	private int numLinksCanNotChecked;
	private int numGoodLinks;
	private int numBadLinks;
	private int currentDepth;
	private long executionDurationInSeconds;
	
	public LinkCheckerStatistics() {
		this.numInteractions = 0;
		this.numRequests = 0;
		this.numLinksVisited = 0;
		this.numLinksNotVisited = 0;
		this.numLinksCanNotChecked = 0;
		this.numGoodLinks = 0;
		this.numBadLinks = 0;
		this.currentDepth = 0;
		this.executionDurationInSeconds = 0;
	}
	
	public synchronized int getNumInteractions() {
		return this.numInteractions;
	}
	
	public synchronized void setNumInteractions(int numInteractions) {
		this.numInteractions = numInteractions;
	}
	
	public synchronized void addNumInteractions() {
		this.numInteractions++;
	}
	
	public synchronized int getNumRequests() {
		return this.numRequests;
	}
	
	public synchronized void setNumRequests(int numRequest) {
		this.numRequests = numRequest;
	}
	
	public synchronized void addNumRequests() {
		this.numRequests++;
	}
	
	public synchronized int getNumLinksVisited() {
		return this.numLinksVisited;
	}
	
	public synchronized void setNumLinksVisited(int numLinks) {
		this.numLinksVisited = numLinks;
	}
	
	public synchronized int getNumLinksNotVisited() {
		return this.numLinksNotVisited;
	}
	
	public synchronized void setNumLinksNotVisited(int numLinks) {
		this.numLinksNotVisited = numLinks;
	}
	
	public synchronized int getNumLinksCanNotChecked() {
		return this.numLinksCanNotChecked;
	}
	
	public synchronized void setNumLinksCanNotChecked(int numLinks) {
		this.numLinksCanNotChecked = numLinks;
	}
	
	public synchronized int getNumGoodLinks() {
		return this.numGoodLinks;
	}
	
	public synchronized void setNumGoodLinks(int numLinks) {
		this.numGoodLinks = numLinks;
	}
	
	public synchronized int getNumBadLinks() {
		return this.numBadLinks;
	}
	
	public synchronized void setNumBadLinks(int numLinks) {
		this.numBadLinks = numLinks;
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
	
	public synchronized long getExecutionDurationInSeconds() {
		return this.executionDurationInSeconds;
	}
	
	public synchronized void setExecutionDurationInSeconds(long executionDuration) {
		this.executionDurationInSeconds = executionDuration;
	}

}
