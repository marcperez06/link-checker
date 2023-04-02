package link_checker.report.link;

public class LinkRelation {
	
	private String from;
	private String to;
	
	public LinkRelation(final String from, final String to) {
		this.from = from;
		this.to = to;
	}
	
	public String getFrom() {
		return this.from;
	}
	
	public String getTo() {
		return this.to;
	}
	
	public void setTo(final String to) {
		this.to = to;
	}

}