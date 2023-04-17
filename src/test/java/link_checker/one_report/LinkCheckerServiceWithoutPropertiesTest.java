package link_checker.one_report;

import org.junit.Before;
import org.junit.Test;

import io.github.marcperez06.link_checker.information.Paths;
import io.github.marcperez06.link_checker.report.LinkCheckerReport;
import io.github.marcperez06.link_checker.report.services.LinkCheckerService;

public class LinkCheckerServiceWithoutPropertiesTest {
	
	@Before
	public void beforeTest() {
		System.out.println("----------- Link Checker Service Without Properties Test -----------------");
	}
	
	@Test
	public void linkCheckerServiceWithoutPropertiesTest() {
		// Not Exist any property file in this path
		String propertiesPath = Paths.USER_DIR + "not_exist.properties";
		String url = "https://www.nato.int/nato-welcome/index_es.html";
		LinkCheckerReport report = LinkCheckerService.getReport(url, propertiesPath);
		assert report != null;
		assert report.getStatistics().getCurrentDepth() == report.getConfiguration().getMinDepth();
		assert report.getStatistics().getNumLinksVisited() > 0;
		assert report.getConfiguration().getMinDepth() == 0;
		assert report.getConfiguration().getNumThreads() == 1;
		assert report.getConfiguration().isSortEnabled() == true;
	}

}
