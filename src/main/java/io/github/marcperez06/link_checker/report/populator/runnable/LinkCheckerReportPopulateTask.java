package io.github.marcperez06.link_checker.report.populator.runnable;

import io.github.marcperez06.link_checker.report.LinkCheckerReport;
import io.github.marcperez06.link_checker.report.link.LinkRelation;
import io.github.marcperez06.link_checker.report.populator.LinkCheckerReportPopulator;

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
