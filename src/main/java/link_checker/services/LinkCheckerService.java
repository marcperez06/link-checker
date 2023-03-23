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
import io.github.marcperez06.java_utilities.uri.UriUtils;
import io.github.marcperez06.java_utilities.validation.ValidationUtils;
import link_checker.enums.Status;
import link_checker.objects.LinkCheckerInfo;
import link_checker.objects.LinkInfo;
import link_checker.objects.LinkRelation;
import link_checker.validation.LinkValidation;

public class LinkCheckerService {
	
	public static LinkCheckerService instance;
	
	private UnirestClient api;
	
	private LinkCheckerService() {
		this.api = new UnirestClient();
	}
	
	public static LinkCheckerService getInstance() {
		return (instance != null) ? instance : new LinkCheckerService();
	}
	
	public static LinkCheckerInfo getPageInfo(String link) {
		LinkCheckerService service = getInstance();
		return service.createLinkCheckerInfo(link);
	}
	
	private LinkCheckerInfo createLinkCheckerInfo(String link) {
		LinkCheckerInfo linkCheckerInfo = new LinkCheckerInfo(link);
		linkCheckerInfo.addLinkNotVisited(new LinkRelation(null, "https://goglopex.com/"));
		linkCheckerInfo.setWishedDepth(1);
		this.checkLinksNotVisited(linkCheckerInfo);
		return linkCheckerInfo;
	}
	
	@SuppressWarnings("unchecked")
	private void checkLinksNotVisited(LinkCheckerInfo linkCheckerInfo) {
		ArrayList<LinkRelation> linksNotVisited = (ArrayList<LinkRelation>) linkCheckerInfo.getLinksNotVisited();
		List<LinkRelation> copyOfLinksNotVisited = (List<LinkRelation>) linksNotVisited.clone();
		
		for (LinkRelation linkNotVisited : copyOfLinksNotVisited) {
			this.fillLinkCheckerInfo(linkCheckerInfo, linkNotVisited);
		}
		
		// Validation Call + depth;
		if (linkCheckerInfo.getLinksNotVisited().size() > 0 && !linkCheckerInfo.reachWishedDepth()) {
			linkCheckerInfo.addCurrentDepth();
			this.checkLinksNotVisited(linkCheckerInfo);
		}
		
	}
	
	private void fillLinkCheckerInfo(LinkCheckerInfo linkCheckerInfo, LinkRelation linkRelation) {
		String link = linkRelation.getTo();
		link = this.getCorrectLink(link, linkCheckerInfo.getFirstLink());
		linkRelation.setTo(link);
		
		if (LinkValidation.isValid(link)) {
		
			boolean existLink = MapUtils.existObjectInMap(linkCheckerInfo.getLinksVisited(), link);
			
			if (!existLink) {
				this.newLinkStrategy(linkCheckerInfo, linkRelation);
			} else {
				this.visitedLinkStrategy(linkCheckerInfo, link);
			}
			
		} else {
			linkCheckerInfo.addLinkNotValid(linkRelation);
			linkCheckerInfo.removeLinkNotVisited(linkRelation);
		}
		
	}
	
	private String getCorrectLink(String link, final String host) {
		boolean transformLink = !link.startsWith("http://") && !link.startsWith("https://"); 
		if (transformLink) {
			link = UriUtils.path(host, link);
		}
		return link;
	}
	
	private void visitedLinkStrategy(LinkCheckerInfo linkCheckerInfo, String link) {
		LinkInfo linkInfo = MapUtils.getMapValue(linkCheckerInfo.getLinksVisited(), link);
		if (!ValidationUtils.equalsIgnoreCase(linkInfo.getLink(), link)) {
			linkInfo.addEntry(link);	
		}
	}
	
	private void newLinkStrategy(LinkCheckerInfo linkCheckerInfo, LinkRelation linkRelation) {
		String link = linkRelation.getTo();
		LinkInfo linkInfo = this.getLinkInfo(link);
		
		if (linkInfo.isGood()) {
			linkInfo.setDepth(linkCheckerInfo.getCurrentDepth());
			
			if (link.contains(linkCheckerInfo.getFirstLink()) && !linkCheckerInfo.reachWishedDepth()) {
				linkCheckerInfo.addLinksNotVisited(link, linkInfo.getExits());	
			}
		}
		
		linkCheckerInfo.addLinkVisited(link, linkInfo);
		linkCheckerInfo.removeLinkNotVisited(linkRelation);
	}
	
	private LinkInfo getLinkInfo(String link) {
		LinkInfo linkInfo = new LinkInfo(link);
		Response<Void> response = this.getUrlResponse(link);
		
		if (response.isSuccess()) {
		
			Document document = this.getHtmlPage(response.getOriginalBody(), link);
			
			if (document != null) {
				this.fillLinkInfoForGoodStatus(linkInfo, document);
			}
			
		} else if (response.isClientError() ) {
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
	
}