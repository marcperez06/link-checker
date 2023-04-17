package io.github.marcperez06.link_checker.report.configuration.validation;

import io.github.marcperez06.java_utilities.validation.ValidationUtils;
import io.github.marcperez06.link_checker.report.LinkCheckerReport;
import io.github.marcperez06.link_checker.report.configuration.LinkCheckerConfiguration;

public class ConfigurationValidation {
	
	public static boolean stopReport(LinkCheckerReport report) {
		boolean stopReport = stopReportWithoutCheckDepth(report);
		stopReport |= reachWishedDepth(report);	
		return stopReport;
	}
	
	public static boolean stopReportWithoutCheckDepth(LinkCheckerReport report) {
		boolean stopReport = !hasLinksToVisit(report);
		stopReport |= reachMaxInteractions(report);
		stopReport |= reachMaxRequests(report);		
		return stopReport;
	}
	
	private static boolean hasLinksToVisit(LinkCheckerReport report) {
		return report.getLinksNotVisited().size() > 0;
	}
	
	public static boolean reachWishedDepth(LinkCheckerReport report) {
		boolean stopReport = false;
		if (isValidNumber(report.getConfiguration().getMinDepth())) {
			stopReport = report.getStatistics().getCurrentDepth() >= report.getConfiguration().getMinDepth();
		}
		return stopReport;
	}
	
	public static boolean reachMaxInteractions(LinkCheckerReport report) {
		boolean stopReport = false;
		if (isValidNumber(report.getConfiguration().getMinInteractions())) {
			stopReport = report.getStatistics().getNumInteractions() >= report.getConfiguration().getMinInteractions();
		}
		return stopReport;
	}
	
	public static boolean reachMaxRequests(LinkCheckerReport report) {
		boolean stopReport = false;
		if (isValidNumber(report.getConfiguration().getMinRequests())) {
			stopReport = report.getStatistics().getNumRequests() >= report.getConfiguration().getMinRequests();
		}
		return stopReport;
	}
	
	private static boolean isValidNumber(Integer number) {
		return ValidationUtils.isNotNull(number) && ValidationUtils.isNotNaN(number);
	}

}
