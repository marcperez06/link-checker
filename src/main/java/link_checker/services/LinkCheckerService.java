package link_checker.services;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import io.github.marcperez06.java_utilities.api.request.Request;
import io.github.marcperez06.java_utilities.api.request.Response;
import io.github.marcperez06.java_utilities.api.request.enums.HttpMethodEnum;
import io.github.marcperez06.java_utilities.api.rest.UnirestClient;
import io.github.marcperez06.java_utilities.collection.map.MapUtils;
import io.github.marcperez06.java_utilities.timer.Timer;
import io.github.marcperez06.java_utilities.uri.UriUtils;
import io.github.marcperez06.java_utilities.validation.ValidationUtils;
import link_checker.enums.Status;
import link_checker.objects.LinkCheckerReport;
import link_checker.objects.LinkInfo;
import link_checker.objects.LinkRelation;
import link_checker.validation.LinkValidation;

public class LinkCheckerService {

	public static LinkCheckerService instance;

	private final UnirestClient api;
	private final Timer timer;

	private LinkCheckerService() {
		this.api = new UnirestClient();
		this.timer = new Timer();
	}

	public static LinkCheckerService getInstance() {
		return (instance != null) ? instance : new LinkCheckerService();
	}

	public static LinkCheckerReport getPageInfo(String link) {
		LinkCheckerService service = getInstance();
		return service.createLinkCheckerInfo(link);
	}

	private LinkCheckerReport createLinkCheckerInfo(String link) {
		LinkCheckerReport report = new LinkCheckerReport(link);
		this.timer.startTimer();
		this.checkLinksNotVisited(report);
		this.timer.stopTimer();
		this.fillLinkCheckerReportStatistics(report);
		
		return report;
	}

	@SuppressWarnings("unchecked")
	private void checkLinksNotVisited(LinkCheckerReport report) {
		ArrayList<LinkRelation> linksNotVisited = (ArrayList<LinkRelation>) report.getLinksNotVisited();
		List<LinkRelation> copyOfLinksNotVisited = (List<LinkRelation>) linksNotVisited.clone();

		for (LinkRelation linkNotVisited : copyOfLinksNotVisited) {
			report.addNumInteraction();
			this.fillLinkCheckerInfo(report, linkNotVisited);
		}

		// Validation Call + depth;
		if (report.getLinksNotVisited().size() > 0 && !report.reachWishedDepth()) {
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

			if (link.contains(report.getFirstLink()) && !report.reachWishedDepth()) {
				report.addLinksNotVisited(link, linkInfo.getExits());
			}
		}

		report.addLinkVisited(link, linkInfo);
		report.removeLinkNotVisited(linkRelation);
	}

	private LinkInfo getLinkInfo(String link) {
		LinkInfo linkInfo = new LinkInfo(link);
		Response<Void> response = this.getUrlResponse(link);

		if (response.isSuccess()) {

			Document document = this.getHtmlPage(response.getOriginalBody(), link);

			if (document != null) {
				this.fillLinkInfoForGoodStatus(linkInfo, document);
			}

		} else if (response.isClientError()) {
			this.fillLinkInfoForBadStatus(linkInfo);
		}

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

	private void fillLinkInfoForGoodStatus(LinkInfo linkInfo, Document document) {
		Elements linkElements = document.select("a");
		List<String> links = linkElements.eachAttr("href");

		for (String exit : links) {
			linkInfo.addExit(exit);
		}

		linkInfo.setStatus(Status.OK);
	}

	private void fillLinkInfoForBadStatus(LinkInfo linkInfo) {
		linkInfo.setStatus(Status.NOT_FOUND);
		linkInfo.setExits(null);
	}

	private Document getHtmlPage(String responseBody, String link) {
		Document document = null;

		try {
			document = Jsoup.parse(responseBody);
		} catch (Exception e) {
			System.out.println("Can not parse the page: " + link);
			e.printStackTrace();
		}

		return document;
	}
	
	
	public void fillLinkCheckerReportStatistics(LinkCheckerReport report) {
		report.setExecutionDuration(this.timer.getTime());
		report.countNumLinksVisited();
		report.countNumLinksNotVisited();
		report.countNumLinksCanNotChecked();
		report.countNumGoodLinks();
		report.countNumBadLinks();
	}

}