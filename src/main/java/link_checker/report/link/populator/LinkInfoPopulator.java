package link_checker.report.link.populator;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import io.github.marcperez06.java_utilities.api.request.Response;
import link_checker.report.link.LinkInfo;
import link_checker.report.link.enums.Status;

public class LinkInfoPopulator {
	
	public static <T> void populate(LinkInfo linkInfo, Response<T> response) {
	
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
