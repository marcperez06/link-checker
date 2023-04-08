package link_checker.multiple_reports;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import link_checker.report.LinkCheckerReport;
import link_checker.report.configuration.builder.LinkCheckerConfigurationBuilder;
import link_checker.report.services.LinkCheckerService;

public class LinkCheckerServiceMultipleReportsTest {
	
	@Before
	public void beforeTest() {
		System.out.println("----------- Link Checker Service Multiple Reports Test -----------------");
	}
	
	@Test
	public void linkCheckerServiceWithDefaultPropertiesTest() {
		List<String> urls = new ArrayList<String>();
		urls.add("https://www.nato.int/nato-welcome/index_es.html");
		urls.add("https://www.fao.org/home/es");
		LinkCheckerConfigurationBuilder configurationBuilder = new LinkCheckerConfigurationBuilder();
		configurationBuilder.minDepth(1);
		configurationBuilder.numThreads(3);
		configurationBuilder.minRequests(5);
		configurationBuilder.sortNotFoundFirst(false);
		List<LinkCheckerReport> reports = LinkCheckerService.getReports(urls, configurationBuilder.build());
		for (LinkCheckerReport report : reports) {
			assert report != null;
			assert report.getStatistics().getNumLinksVisited() > 0;
			assert report.getConfiguration().getMinDepth() == 1;
			assert report.getConfiguration().getMinRequests() == 5;
			assert report.getConfiguration().getNumThreads() == 3;
			assert report.getConfiguration().isSortEnabled() == false;	
		}
	}

}
