package link_checker.report.populator.runnable;

import link_checker.report.LinkCheckerReport;
import link_checker.report.link.LinkRelation;
import link_checker.report.populator.LinkCheckerReportPopulator;

public class LinkCheckerReportPopulateTask implements Runnable {
	
	private LinkCheckerReport report;
	private LinkRelation relation;

	public LinkCheckerReportPopulateTask(LinkCheckerReport report, LinkRelation relation) {
		this.report = report;
		this.relation = relation;
	}
	
	@Override
	public void run() {
		LinkCheckerReportPopulator.populate(this.report, this.relation);
	}

}
