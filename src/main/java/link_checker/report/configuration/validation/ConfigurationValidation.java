package link_checker.report.configuration.validation;

import io.github.marcperez06.java_utilities.validation.ValidationUtils;
import link_checker.report.LinkCheckerReport;
import link_checker.report.configuration.LinkCheckerConfiguration;

public class ConfigurationValidation {
	
	public static boolean stopReport(LinkCheckerReport report) {
		boolean stopReport = !hasLinksToVisit(report);
		
		if (!stopReport) {
			stopReport = reachWishedDepth(report);
		}
		
		if (!stopReport) {
			stopReport &= reachMaxInteractions(report);
		}
		
		if (!stopReport) {
			stopReport &= reachMaxRequests(report);
		}
		
		/*
		if (!stopReport) {
			stopReport &= reachMaxExecutionDurationInMilliseconds(report);
		}
		*/
		
		return stopReport;
	}
	
	private static boolean hasLinksToVisit(LinkCheckerReport report) {
		return report.getLinksNotVisited().size() > 0;
	}
	
	public static boolean reachWishedDepth(LinkCheckerReport report) {
		boolean stopReport = false;
		if (isValidNumber(report.getConfiguration().getMaxDepth())) {
			stopReport = report.getStatistics().getCurrentDepth() >= report.getConfiguration().getMaxDepth();
		}
		return stopReport;
	}
	
	public static boolean reachMaxInteractions(LinkCheckerReport report) {
		boolean stopReport = false;
		if (isValidNumber(report.getConfiguration().getMaxInteractions())) {
			stopReport = report.getStatistics().getNumInteractions() >= report.getConfiguration().getMaxInteractions();
		}
		return stopReport;
	}
	
	public static boolean reachMaxRequests(LinkCheckerReport report) {
		boolean stopReport = false;
		if (isValidNumber(report.getConfiguration().getMaxRequests())) {
			stopReport = report.getStatistics().getNumRequests() >= report.getConfiguration().getMaxRequests();
		}
		return stopReport;
	}
	
	/*
	public static boolean reachMaxExecutionDurationInMilliseconds(LinkCheckerReport report) {
		boolean stopReport = false;
		if (ValidationUtils.isNotNaN(report.getConfiguration().getMaxExecutionTime())) {
			stopReport &= report.getStatistics().getCurrentDepth() >= report.getConfiguration().getMaxDepth();
		}
		return stopReport;
	}
	*/
	
	private static boolean isValidNumber(Integer number) {
		return ValidationUtils.isNotNull(number) && ValidationUtils.isNotNaN(number);
	}

}
