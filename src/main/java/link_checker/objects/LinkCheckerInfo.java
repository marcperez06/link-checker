package link_checker.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.marcperez06.java_utilities.collection.list.ListUtils;
import io.github.marcperez06.java_utilities.collection.map.MapUtils;
import link_checker.enums.Status;

public class LinkCheckerInfo {
	
	private static String firstLink;
	private static int numGoodLinks;
	private static int numBadLinks;
	private static int currentDepth;
	private static int wishedDepth;
	private static Map<String, LinkInfo> linksVisited = new HashMap<String, LinkInfo>();
	private static List<String> linksNotVisited = new ArrayList<String>();
	
	public static String getFirstLink() {
		return firstLink;
	}
	
	public static void setFirstLink(String link) {
		firstLink = link;
	}
	
	public static int getNumGoodLinks() {
		return numGoodLinks;
	}
	
	public static void setNumGoodLinks(int numLinks) {
		numGoodLinks = numLinks;
	}
	
	public static void addNumGoodLinks() {
		numGoodLinks++;
	}
	
	public static int getNumBadLinks() {
		return numBadLinks;
	}
	
	public static void setNumBadLinks(int numLinks) {
		numBadLinks = numLinks;
	}
	
	public static void addNumBadLinks() {
		numBadLinks++;
	}
	
	public static int getCurrentDepth() {
		return currentDepth;
	}
	
	public static void setCurrentDepth(int depth) {
		currentDepth = depth;
	}
	
	public static void addCurrentDepth() {
		currentDepth++;
	}
	
	public static int getWishedDepth() {
		return wishedDepth;
	}
	
	public static void setWishedDepth(int depth) {
		wishedDepth = depth;
	}
	
	public static Map<String, LinkInfo> getLinksVisited() {
		return linksVisited;
	}
	
	public static void setLinksVisited(Map<String, LinkInfo> links) {
		linksVisited = links;
	}
	
	public static void addLinkVisited(String link, LinkInfo linkInfo) {
		MapUtils.addObjectIfNotExistInMap(linksVisited, link, linkInfo);
		if (linkInfo.getStatus() == Status.OK) {
			addNumGoodLinks();
		} else {
			addNumBadLinks();
		}
	}
	
	public static List<String> getLinksNotVisited() {
		return linksNotVisited;
	}
	
	public static void setLinksNotVisited(List<String> links) {
		linksNotVisited = links;
	}
	
	public static void addLinkNotVisited(String link) {
		ListUtils.addObjectInList(linksNotVisited, link);
	}
	
	public static void addLinksNotVisited(List<String> link) {
		ListUtils.addObjectsInList(linksNotVisited, link);
	}
	
	public static void removeLinkNotVisited(String link) {
		ListUtils.removeObjectInList(linksNotVisited, link);
	}

}