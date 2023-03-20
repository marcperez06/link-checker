package link_checker.services;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import io.github.marcperez06.java_utilities.api.rest.UnirestClient;
import io.github.marcperez06.java_utilities.collection.map.MapUtils;
import link_checker.enums.Status;
import link_checker.objects.LinkCheckerInfo;
import link_checker.objects.LinkInfo;

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
		linkCheckerInfo.addLinkNotVisited("https://goglopex.com/");
		this.checkLinksNotVisited(linkCheckerInfo);
		return linkCheckerInfo;
	}
	
	@SuppressWarnings("unchecked")
	private void checkLinksNotVisited(LinkCheckerInfo linkCheckerInfo) {
		ArrayList<String> linksNotVisited = (ArrayList<String>) linkCheckerInfo.getLinksNotVisited();
		List<String> copyOfLinksNotVisited = (List<String>) linksNotVisited.clone();
		
		for (String linkNotVisited : copyOfLinksNotVisited) {
			this.fillLinkCheckerInfo(linkCheckerInfo, linkNotVisited);
		}
		
		// Validation Call + depth;
		if (linkCheckerInfo.getLinksNotVisited().size() > 0) {
			linkCheckerInfo.addCurrentDepth();
			this.checkLinksNotVisited(linkCheckerInfo);
		}
		
	}
	
	private void fillLinkCheckerInfo(LinkCheckerInfo linkCheckerInfo, String link) {
		boolean existLink = MapUtils.existObjectInMap(linkCheckerInfo.getLinksVisited(), link);
		
		if (!existLink) {
			this.newLinkStrategy(linkCheckerInfo, link);
		} else {
			this.visitedLinkStrategy(linkCheckerInfo, link);
		}
	}
	
	private void visitedLinkStrategy(LinkCheckerInfo linkCheckerInfo, String link) {
		LinkInfo linkInfo = MapUtils.getMapValue(linkCheckerInfo.getLinksVisited(), link);
		linkInfo.addEntry(link);
	}
	
	private void newLinkStrategy(LinkCheckerInfo linkCheckerInfo, String link) {
		LinkInfo linkInfo = this.getLinkInfo(link);
		
		if (linkInfo.isGood()) {
			linkInfo.setDepth(linkCheckerInfo.getCurrentDepth());
			linkCheckerInfo.addLinksNotVisited(linkInfo.getExits());
		}
		
		linkCheckerInfo.addLinkVisited(link, linkInfo);
		linkCheckerInfo.removeLinkNotVisited(link);
	}
	
	private LinkInfo getLinkInfo(String link) {
		LinkInfo linkInfo = new LinkInfo(link);
		Document document = this.getHtmlPage(link);
		
		if (document != null) {
			this.fillLinkInfoForGoodStatus(linkInfo, document);
		} else {
			this.fillLinkInfoForBadStatus(linkInfo);
		}
		
		return linkInfo;
	}
	
	private void fillLinkInfoForGoodStatus(LinkInfo linkInfo, Document document) {
		Elements linkElements = document.select("a");
		List<String> links = linkElements.eachAttr("href");
		linkInfo.setExits(links);
		linkInfo.setStatus(Status.OK);
	}
	
	private void fillLinkInfoForBadStatus(LinkInfo linkInfo) {
		linkInfo.setStatus(Status.NOT_FOUND);
		linkInfo.setExits(null);
	}
	
	private Document getHtmlPage(String link) {
		Document document = null;
		
		try {
			document = Jsoup.connect(link).timeout(3000).get();
		} catch (Exception e) {
			System.out.println("Can not parse the page: " + link);
			e.printStackTrace();
		}
		
		return document;
	}
	
}