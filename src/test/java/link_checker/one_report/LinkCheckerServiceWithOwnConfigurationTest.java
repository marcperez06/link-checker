package link_checker.one_report;

import org.junit.Before;
import org.junit.Test;

import io.github.marcperez06.link_checker.report.LinkCheckerReport;
import io.github.marcperez06.link_checker.report.configuration.LinkCheckerConfiguration;
import io.github.marcperez06.link_checker.report.configuration.builder.LinkCheckerConfigurationBuilder;
import io.github.marcperez06.link_checker.report.services.LinkCheckerService;

public class LinkCheckerServiceWithOwnConfigurationTest {
	
	@Before
	public void beforeTest() {
		System.out.println("----------- Link Checker Service With Own Properties Test -----------------");
	}
	
	@Test
	public void linkCheckerServiceWithDefaultPropertiesTest() {
		String url = "https://www.nato.int/nato-welcome/index_es.html";
		LinkCheckerConfigurationBuilder configurationBuilder = new LinkCheckerConfigurationBuilder();
		configurationBuilder.minDepth(1);
		configurationBuilder.numThreads(2);
		configurationBuilder.minInteractions(10);
		configurationBuilder.minRequests(5);
		configurationBuilder.sortNotFoundFirst(false);
		LinkCheckerReport report = LinkCheckerService.getReport(url, configurationBuilder.build());
		assert report != null;
		assert report.getStatistics().getNumLinksVisited() > 0;
		assert report.getConfiguration().getMinDepth() == 1;
		assert report.getConfiguration().getMinInteractions() == 10;
		assert report.getConfiguration().getMinRequests() == 5;
		assert report.getConfiguration().getNumThreads() == 2;
		assert report.getConfiguration().isSortEnabled() == false;
	}

}
