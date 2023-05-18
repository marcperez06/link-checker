package io.github.marcperez06.link_checker.report.populator;

import java.util.Map;

import io.github.marcperez06.java_utilities.collection.map.MapUtils;
import io.github.marcperez06.link_checker.report.LinkCheckerReport;
import io.github.marcperez06.link_checker.report.link.LinkInfo;
import io.github.marcperez06.link_checker.report.link.LinkRelation;

public class LinkCheckerReportCleaner {
	
	public static void cleanLinksNotVisited(LinkCheckerReport report, LinkRelation linkNotVisited) {
		String exit = linkNotVisited.getTo();
		String entry = linkNotVisited.getFrom();
		Map<String, LinkInfo> linksVisited = report.getLinksVisited();
		LinkInfo exitLink = getLinkInfoIfExistOrNull(linksVisited, exit);
		LinkInfo entryLink = getLinkInfoIfExistOrNull(linksVisited, entry);
		
		addLinkToEntry(exitLink, entry);
		addLinkToExit(entryLink, exit);
		
		report.removeLinkNotVisited(linkNotVisited);
	}
	
	private static void addLinkToEntry(LinkInfo linkInfo, String entry) {
		if (linkInfo != null) {
			linkInfo.addEntry(entry);	
		}
	}
	
	private static void addLinkToExit(LinkInfo linkInfo, String exit) {
		if (linkInfo != null) {
			linkInfo.addExit(exit);	
		}
	}
	
	private static LinkInfo getLinkInfoIfExistOrNull(Map<String, LinkInfo> linksVisited, String key) {
		LinkInfo linkInfo = null;
		if (MapUtils.existObjectInMap(linksVisited, key)) {
			linkInfo = linksVisited.get(key);	
		}
		return linkInfo;
	}

}
