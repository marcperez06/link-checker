package io.github.marcperez06.link_checker.report.populator;

import java.util.Map;

import io.github.marcperez06.java_utilities.collection.map.MapUtils;
import io.github.marcperez06.link_checker.report.LinkCheckerReport;
import io.github.marcperez06.link_checker.report.link.LinkInfo;
import io.github.marcperez06.link_checker.report.link.LinkRelation;

public class LinkCheckerReportCleaner {
	
	public static void cleanLinksNotVisited(LinkCheckerReport report, LinkRelation linkNotVisited) {
		String link = linkNotVisited.getTo();
		String entry = linkNotVisited.getFrom();
		Map<String, LinkInfo> linksVisited = report.getLinksVisited();
		
		if (MapUtils.existObjectInMap(linksVisited, link)) {
			LinkInfo linkInfo = linksVisited.get(link);
			linkInfo.addEntry(entry);
			report.removeLinkNotVisited(linkNotVisited);
		}
	}
	
	//TODO: improve method;

}
