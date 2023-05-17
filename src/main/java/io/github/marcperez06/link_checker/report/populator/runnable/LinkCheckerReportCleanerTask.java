package io.github.marcperez06.link_checker.report.populator.runnable;

import io.github.marcperez06.link_checker.report.LinkCheckerReport;
import io.github.marcperez06.link_checker.report.link.LinkRelation;
import io.github.marcperez06.link_checker.report.populator.LinkCheckerReportCleaner;

public class LinkCheckerReportCleanerTask implements Runnable {
	
	private LinkCheckerReport report;
	private LinkRelation relation;

	public LinkCheckerReportCleanerTask(LinkCheckerReport report, LinkRelation relation) {
		this.report = report;
		this.relation = relation;
	}
	
	@Override
	public void run() {
		LinkCheckerReportCleaner.cleanLinksNotVisited(this.report, this.relation);
	}

}
