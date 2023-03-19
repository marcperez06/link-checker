package link_checker.services;

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
	
	public static void getPageInfo() {
		getInstance().fillLinkCheckerInfo();
	}
	
	private void fillLinkCheckerInfo() {
		this.getLinkStatus("https://vandal.elespanolas.com");
	}
	
	private void getPageInfo(Object configuration) {
		
	}

	private void getLinkStatus(String link) {
		boolean existLink = MapUtils.existObjectInMap(LinkCheckerInfo.getLinksVisited(), link);
		
		if (!existLink) {
			this.newLinkStrategy(link);
		} else {
			this.visitedLinkStrategy(link);
		}

	}
	
	private void visitedLinkStrategy(String link) {
		LinkInfo linkInfo = MapUtils.getMapValue(LinkCheckerInfo.getLinksVisited(), link);
		linkInfo.addEntry(link);
	}
	
	private void newLinkStrategy(String link) {
		LinkInfo linkInfo = new LinkInfo(link);
		Document document = this.getHtmlPage(link);
		
		if (document != null) {
			this.fillLinkInfoForGoodStatus(linkInfo, document);
		} else {
			this.fillLinkInfoForBadStatus(linkInfo);
		}
		
		LinkCheckerInfo.addLinkVisited(link, linkInfo);
		LinkCheckerInfo.removeLinkNotVisited(link);
	}
	
	private void fillLinkInfoForGoodStatus(LinkInfo linkInfo, Document document) {
		Elements linkElements = document.select("a");
		List<String> links = linkElements.eachAttr("href");
		linkInfo.setDepth(LinkCheckerInfo.getCurrentDepth());
		linkInfo.setExits(links);
		linkInfo.setStatus(Status.OK);
		LinkCheckerInfo.addLinksNotVisited(links);
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