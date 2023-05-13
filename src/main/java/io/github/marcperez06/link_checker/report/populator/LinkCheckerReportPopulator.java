package io.github.marcperez06.link_checker.report.populator;

import java.util.concurrent.TimeUnit;

import io.github.marcperez06.java_utilities.api.request.Request;
import io.github.marcperez06.java_utilities.api.request.Response;
import io.github.marcperez06.java_utilities.api.request.enums.HttpMethodEnum;
import io.github.marcperez06.java_utilities.api.rest.interfaces.IRestClient;
import io.github.marcperez06.java_utilities.collection.map.MapUtils;
import io.github.marcperez06.java_utilities.testdata.DataCollector;
import io.github.marcperez06.java_utilities.timer.Timer;
import io.github.marcperez06.java_utilities.uri.UriUtils;
import io.github.marcperez06.java_utilities.validation.ValidationUtils;
import io.github.marcperez06.link_checker.report.LinkCheckerReport;
import io.github.marcperez06.link_checker.report.link.LinkInfo;
import io.github.marcperez06.link_checker.report.link.LinkRelation;
import io.github.marcperez06.link_checker.report.link.populator.LinkInfoPopulator;
import io.github.marcperez06.link_checker.report.link.validation.LinkValidation;

public class LinkCheckerReportPopulator {
	
	public static void populate(LinkCheckerReport report, LinkRelation linkNotVisited) {
		report.addNumInteraction();
		fillLinkCheckerInfo(report, linkNotVisited);
	}
	
	private static void fillLinkCheckerInfo(LinkCheckerReport report, LinkRelation linkRelation) {
		String link = linkRelation.getTo();
		link = getCorrectLink(link, report.getFirstLink());
		linkRelation.setTo(link);

		if (LinkValidation.canCheck(link)) {
			
			report.addNumRequest();
			boolean existLink = MapUtils.existObjectInMap(report.getLinksVisited(), link);

			if (!existLink) {
				newLinkStrategy(report, linkRelation);
			} else {
				visitedLinkStrategy(report, link);
			}

		} else {
			report.addLinkCanNotChecked(linkRelation);
			report.removeLinkNotVisited(linkRelation);
		}

	}

	private static String getCorrectLink(String link, final String host) {
		boolean transformLink = !link.startsWith("http://") && !link.startsWith("https://");
		if (transformLink) {
			link = UriUtils.path(host, link);
		}
		return link;
	}

	private static void addLinkEntry(LinkInfo linkInfo, String linkEntry) {
		if (linkEntry != null && !ValidationUtils.equalsIgnoreCase(linkInfo.getLink(), linkEntry)) {
			linkInfo.addEntry(linkEntry);
		}
	}

	private static void visitedLinkStrategy(LinkCheckerReport report, String link) {
		LinkInfo linkInfo = MapUtils.getMapValue(report.getLinksVisited(), link);
		addLinkEntry(linkInfo, link);
	}

	private static void newLinkStrategy(LinkCheckerReport report, LinkRelation linkRelation) {
		String link = linkRelation.getTo();
		LinkInfo linkInfo = getLinkInfo(link);
		addLinkEntry(linkInfo, linkRelation.getFrom());

		if (linkInfo.isGood()) {
			linkInfo.setDepth(report.getStatistics().getCurrentDepth());

			if (LinkValidation.linkBelongsToDomainOrWithelist(link, report)) {
				report.addLinksNotVisited(link, linkInfo.getExits());
			}
		}

		report.addLinkVisited(link, linkInfo);
		report.removeLinkNotVisited(linkRelation);
	}

	private static LinkInfo getLinkInfo(String link) {
		LinkInfo linkInfo = new LinkInfo(link);
		Response<Void> response = getUrlResponse(link);
		LinkInfoPopulator.populate(linkInfo, response);
		return linkInfo;
	}

	private static Response<Void> getUrlResponse(String link) {
		IRestClient api = (IRestClient) DataCollector.getData("restClient");
		Request request = new Request(HttpMethodEnum.GET, link);
		Response<Void> response = null;

		try {
			response = api.send(request);
		} catch (Exception e) {
			response = new Response<Void>(-1);
			response.setError(e);
		}

		return response;
	}
	
	public static void fillLinkCheckerReportStatistics(LinkCheckerReport report) {
		Timer timer = (Timer) DataCollector.getData("timer");
		report.setExecutionDuration(timer.getTime(TimeUnit.SECONDS));
		report.countNumLinksVisited();
		report.countNumLinksNotVisited();
		report.countNumLinksCanNotChecked();
		report.countNumGoodLinks();
		report.countNumNotFoundLinks();
		report.countNumForbiddenLinks();
		report.countNumRequestDeniedLinks();
		report.countNumLinksThrownException();
	}

}
