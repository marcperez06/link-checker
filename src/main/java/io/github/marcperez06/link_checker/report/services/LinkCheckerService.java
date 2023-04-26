package io.github.marcperez06.link_checker.report.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.github.marcperez06.java_utilities.api.request.Request;
import io.github.marcperez06.java_utilities.api.request.Response;
import io.github.marcperez06.java_utilities.api.request.enums.HttpMethodEnum;
import io.github.marcperez06.java_utilities.api.rest.UnirestClient;
import io.github.marcperez06.java_utilities.collection.map.MapUtils;
import io.github.marcperez06.java_utilities.date.DateUtils;
import io.github.marcperez06.java_utilities.file.FileUtils;
import io.github.marcperez06.java_utilities.json.GsonUtils;
import io.github.marcperez06.java_utilities.logger.Logger;
import io.github.marcperez06.java_utilities.strings.StringUtils;
import io.github.marcperez06.java_utilities.testdata.DataCollector;
import io.github.marcperez06.java_utilities.threads.ThreadUtils;
import io.github.marcperez06.java_utilities.timer.Timer;
import io.github.marcperez06.java_utilities.uri.UriUtils;
import io.github.marcperez06.java_utilities.validation.ValidationUtils;
import io.github.marcperez06.link_checker.information.Paths;
import io.github.marcperez06.link_checker.report.LinkCheckerReport;
import io.github.marcperez06.link_checker.report.LinkCheckerStatistics;
import io.github.marcperez06.link_checker.report.configuration.LinkCheckerConfiguration;
import io.github.marcperez06.link_checker.report.configuration.factory.LinkCheckerConfigurationFactory;
import io.github.marcperez06.link_checker.report.configuration.validation.ConfigurationValidation;
import io.github.marcperez06.link_checker.report.link.LinkInfo;
import io.github.marcperez06.link_checker.report.link.LinkRelation;
import io.github.marcperez06.link_checker.report.link.populator.LinkInfoPopulator;
import io.github.marcperez06.link_checker.report.link.validation.LinkValidation;
import io.github.marcperez06.link_checker.report.populator.LinkCheckerReportPopulator;
import io.github.marcperez06.link_checker.report.populator.runnable.LinkCheckerReportPopulateTask;

public class LinkCheckerService {

	public static LinkCheckerService instance;

	private final UnirestClient api;
	private final Timer timer;

	private LinkCheckerService() {
		this.api = new UnirestClient();
		this.timer = new Timer();
		this.api.disableCookieManagement();
		DataCollector.addData("timer", this.timer);
		DataCollector.addData("restClient", this.api);
	}

	public static LinkCheckerService getInstance() {
		if (instance == null) {
			instance = new LinkCheckerService();
		}
		return instance;
	}
	
	public static List<LinkCheckerReport> getReports(List<String> links) {
		List<LinkCheckerReport> reports = new ArrayList<LinkCheckerReport>();
		LinkCheckerConfiguration configuration = LinkCheckerConfigurationFactory.createConfiguration();
		for (String link : links) {
			reports.add(getReport(link, configuration));
		}
		return reports;
	}
	
	public static List<LinkCheckerReport> getReports(List<String> links, String propertiesPath) {
		List<LinkCheckerReport> reports = new ArrayList<LinkCheckerReport>();
		LinkCheckerConfiguration configuration = LinkCheckerConfigurationFactory.createConfiguration(propertiesPath);
		for (String link : links) {
			reports.add(getReport(link, configuration));
		}
		return reports;
	}
	
	public static List<LinkCheckerReport> getReports(List<String> links, LinkCheckerConfiguration configuration) {
		List<LinkCheckerReport> reports = new ArrayList<LinkCheckerReport>();
		for (String link : links) {
			reports.add(getReport(link, configuration));
		}
		return reports;
	}

	public static LinkCheckerReport getReport(String link) {
		LinkCheckerConfiguration configuration = LinkCheckerConfigurationFactory.createConfiguration();
		return getReport(link, configuration);
	}
	
	public static LinkCheckerReport getReport(String link, String propertiesPath) {
		LinkCheckerConfiguration configuration = LinkCheckerConfigurationFactory.createConfiguration(propertiesPath);
		return getReport(link, configuration);
	}
	
	public static LinkCheckerReport getReport(String link, LinkCheckerConfiguration configuration) {
		LinkCheckerService service = getInstance();
		return service.createLinkCheckerInfo(link, configuration);
	}

	private LinkCheckerReport createLinkCheckerInfo(String link, LinkCheckerConfiguration configuration) {
		LinkCheckerReport report = new LinkCheckerReport(link.trim(), configuration);
		this.timer.startTimer();
		this.checkLinksNotVisited(report);
		this.timer.stopTimer();
		this.fillLinkCheckerReportStatistics(report);
		report.sortLinksVisited();
		this.writeReportResult(report);
		return report;
	}

	@SuppressWarnings("unchecked")
	private void checkLinksNotVisited(LinkCheckerReport report) {
		ArrayList<LinkRelation> linksNotVisited = (ArrayList<LinkRelation>) report.getLinksNotVisited();
		List<LinkRelation> copyOfLinksNotVisited = (List<LinkRelation>) linksNotVisited.clone();

		ThreadUtils.createPool(report.getConfiguration().getNumThreads());
		
		boolean stopReport = false;
		for (int i = 0; i < copyOfLinksNotVisited.size() && !stopReport; i++) {
			LinkRelation linkNotVisited = copyOfLinksNotVisited.get(i);
			ThreadUtils.addTask(new LinkCheckerReportPopulateTask(report, linkNotVisited));
			stopReport = ConfigurationValidation.stopReportWithoutCheckDepth(report);
			if (i % report.getConfiguration().getNumThreads() == 0) {
				ThreadUtils.executeAllTask();
			}
		}
		
		ThreadUtils.waitUntilExecutionFinish();
		
		this.printReportStatusInfo(report);

		// Validation Call + depth;
		if (!ConfigurationValidation.stopReport(report)) {
			report.addCurrentDepth();
			this.checkLinksNotVisited(report);
		}

	}

	private void fillLinkCheckerInfo(LinkCheckerReport report, LinkRelation linkRelation) {
		String link = linkRelation.getTo();
		link = this.getCorrectLink(link, report.getFirstLink());
		linkRelation.setTo(link);

		if (LinkValidation.canCheck(link)) {
			
			report.addNumRequest();
			boolean existLink = MapUtils.existObjectInMap(report.getLinksVisited(), link);

			if (!existLink) {
				this.newLinkStrategy(report, linkRelation);
			} else {
				this.visitedLinkStrategy(report, link);
			}

		} else {
			report.addLinkCanNotChecked(linkRelation);
			report.removeLinkNotVisited(linkRelation);
		}

	}

	private String getCorrectLink(String link, final String host) {
		boolean transformLink = !link.startsWith("http://") && !link.startsWith("https://");
		if (transformLink) {
			link = UriUtils.path(host, link);
		}
		return link;
	}

	private void addLinkEntry(LinkInfo linkInfo, String linkEntry) {
		if (linkEntry != null && !ValidationUtils.equalsIgnoreCase(linkInfo.getLink(), linkEntry)) {
			linkInfo.addEntry(linkEntry);
		}
	}

	private void visitedLinkStrategy(LinkCheckerReport report, String link) {
		LinkInfo linkInfo = MapUtils.getMapValue(report.getLinksVisited(), link);
		this.addLinkEntry(linkInfo, link);
	}

	private void newLinkStrategy(LinkCheckerReport report, LinkRelation linkRelation) {
		String link = linkRelation.getTo();
		LinkInfo linkInfo = this.getLinkInfo(link);
		this.addLinkEntry(linkInfo, linkRelation.getFrom());

		if (linkInfo.isGood()) {
			linkInfo.setDepth(report.getStatistics().getCurrentDepth());

			if (link.contains(report.getFirstLink())) {
				report.addLinksNotVisited(link, linkInfo.getExits());
			}
		}

		report.addLinkVisited(link, linkInfo);
		report.removeLinkNotVisited(linkRelation);
	}

	private LinkInfo getLinkInfo(String link) {
		LinkInfo linkInfo = new LinkInfo(link);
		Response<Void> response = this.getUrlResponse(link);
		LinkInfoPopulator.populate(linkInfo, response);
		return linkInfo;
	}

	private Response<Void> getUrlResponse(String link) {
		Request request = new Request(HttpMethodEnum.GET, link);
		Response<Void> response = null;

		try {
			response = this.api.send(request);
		} catch (Exception e) {
			response = new Response<Void>(Response.NOT_FOUND);
		}

		return response;
	}
	
	public void fillLinkCheckerReportStatistics(LinkCheckerReport report) {
		report.setExecutionDuration(this.timer.getTime(TimeUnit.SECONDS));
		report.countNumLinksVisited();
		report.countNumLinksNotVisited();
		report.countNumLinksCanNotChecked();
		report.countNumGoodLinks();
		report.countNumBadLinks();
		report.countNumForbiddenLinks();
		report.countNumRequestDeniedLinks();
	}
	
	private void printReportStatusInfo(LinkCheckerReport report) {
		LinkCheckerReportPopulator.fillLinkCheckerReportStatistics(report);
		LinkCheckerStatistics statistics = report.getStatistics();
		String currentDepth = String.valueOf(statistics.getCurrentDepth());
		String linksVisited = String.valueOf(statistics.getNumLinksVisited());
		String linksNotVisited = String.valueOf(statistics.getNumLinksNotVisited());
		String interactions = String.valueOf(statistics.getNumInteractions());
		String requets = String.valueOf(statistics.getNumRequests());
		
		String reportStatus = "------------ Current depth: %s | Links visited: %s | Links not visited: %s | ";
		reportStatus += "Intractions: %s | Requests: %s ----------";
		
		reportStatus = StringUtils.format(reportStatus, currentDepth, linksVisited, 
												linksNotVisited, interactions, requets);
		
		Logger.log(reportStatus);
	}
	
	private void writeReportResult(LinkCheckerReport report) {
		String outputReportPath = report.getConfiguration().getOutputReportPath();
		if (ValidationUtils.isNotEmpty(outputReportPath)) {
			String result = GsonUtils.getPrettyJSON(report);
			String fileName = report.getConfiguration().getBaseReportName() + "_";
			fileName += StringUtils.encode(report.getFirstLink());
			fileName += "_" + DateUtils.getCurrentTime() + ".txt";
			String path = UriUtils.path(outputReportPath, Paths.FILE_SEPARATOR) + fileName;
			FileUtils.writeTxt(result, path);
		}
	}

}