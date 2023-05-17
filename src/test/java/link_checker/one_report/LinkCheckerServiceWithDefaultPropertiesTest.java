package link_checker.one_report;

import org.junit.Before;
import org.junit.Test;

import io.github.marcperez06.link_checker.report.LinkCheckerReport;
import io.github.marcperez06.link_checker.report.services.LinkCheckerService;

public class LinkCheckerServiceWithDefaultPropertiesTest {
	
	@Before
	public void beforeTest() {
		System.out.println("----------- Link Checker Service With Default Properties Test -----------------");
	}
	
	@Test
	public void linkCheckerServiceWithDefaultPropertiesTest() {
		String url = "https://www.nato.int/nato-welcome/index_es.html";
		LinkCheckerReport report = LinkCheckerService.getReport(url);
		assert report != null;
		assert report.getStatistics().getNumLinksVisited() > 0;
		assert report.getConfiguration().getMinDepth() == 3;
		assert report.getConfiguration().getNumThreads() == 3;
		assert report.getConfiguration().isSortEnabled() == true;
	}

}
