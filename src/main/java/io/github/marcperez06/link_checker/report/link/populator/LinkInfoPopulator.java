package io.github.marcperez06.link_checker.report.link.populator;

import java.util.List;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import io.github.marcperez06.java_utilities.api.request.Response;
import io.github.marcperez06.link_checker.report.link.LinkInfo;
import io.github.marcperez06.link_checker.report.link.enums.Status;

public class LinkInfoPopulator {
	
	public static <T> void populate(LinkInfo linkInfo, Response<T> response) {
		linkInfo.setStatusCode(response.getStatusCode());
		
		if (response.isSuccess()) {

			Document document = getHtmlPage(response.getOriginalBody(), linkInfo.getLink());

			if (document != null) {
				fillLinkInfoForGoodStatus(linkInfo, document);
			}

		} else if (response.isNotFound()) {
			fillLinkInfoForNotFoundStatus(linkInfo);
		} else if (response.isForbidden()) {
			fillLinkInfoForForbiddenStatus(linkInfo);
		} else if (response.statusCodeIs(999)) {
			fillLinkInfoForRequestDeniedStatus(linkInfo);
		} else if (response.statusCodeIs(-1)) {
			fillLinkInfoForExceptionStatus(linkInfo, response);
		} else {
			fillLinkInfoForUnexpectedStatus(linkInfo);
		}
		
	}
	
	private static void fillLinkInfoForGoodStatus(LinkInfo linkInfo, Document document) {
		Elements linkElements = document.select("a");
		List<String> links = linkElements.eachAttr("href");

		for (String exit : links) {
			linkInfo.addExit(exit);
		}

		linkInfo.setStatus(Status.OK);
	}

	private static void fillLinkInfoForNotFoundStatus(LinkInfo linkInfo) {
		fillLinkInfoForBadStatus(linkInfo, Status.NOT_FOUND);	
	}
	
	private static void fillLinkInfoForForbiddenStatus(LinkInfo linkInfo) {
		fillLinkInfoForBadStatus(linkInfo, Status.FORBIDDEN);	
	}
	
	private static void fillLinkInfoForRequestDeniedStatus(LinkInfo linkInfo) {
		fillLinkInfoForBadStatus(linkInfo, Status.REQUEST_DENIED);
	}
	
	private static void fillLinkInfoForUnexpectedStatus(LinkInfo linkInfo) {
		fillLinkInfoForBadStatus(linkInfo, Status.UNEXPECTED);
	}
	
	private static <T> void fillLinkInfoForExceptionStatus(LinkInfo linkInfo, Response<T> response) {
		Optional<Exception> error = response.getError();
		if (error.isPresent()) {
			linkInfo.setExceptionCausedBy(error.get().getCause().getCause().getMessage());
		}
		linkInfo.setStatusCode(null);
		fillLinkInfoForBadStatus(linkInfo, Status.EXCEPTION);
	}
	
	private static void fillLinkInfoForBadStatus(LinkInfo linkInfo, Status status) {
		linkInfo.setStatus(status);
		linkInfo.setExits(null);
	}
	
	private static Document getHtmlPage(String responseBody, String link) {
		Document document = null;

		try {
			document = Jsoup.parse(responseBody);
		} catch (Exception e) {
			System.out.println("Can not parse the page: " + link);
			e.printStackTrace();
		}

		return document;
	}

}
