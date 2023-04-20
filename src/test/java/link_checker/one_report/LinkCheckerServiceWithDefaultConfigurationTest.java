package link_checker.one_report;

import org.junit.Before;
import org.junit.Test;

import io.github.marcperez06.link_checker.report.LinkCheckerReport;
import io.github.marcperez06.link_checker.report.configuration.LinkCheckerConfiguration;
import io.github.marcperez06.link_checker.report.configuration.builder.LinkCheckerConfigurationBuilder;
import io.github.marcperez06.link_checker.report.services.LinkCheckerService;

public class LinkCheckerServiceWithDefaultConfigurationTest {
	
	@Before
	public void beforeTest() {
		System.out.println("----------- Link Checker Service With Default Properties Test -----------------");
	}
	
	@Test
	public void linkCheckerServiceWithDefaultPropertiesTest() {
		String url = "https://www.linkedin.com/company/4financespain";
		LinkCheckerConfiguration configuration = new LinkCheckerConfigurationBuilder().build();
		LinkCheckerReport report = LinkCheckerService.getReport(url, configuration);
		assert report != null;
		assert report.getStatistics().getNumLinksVisited() > 0;
		assert report.getConfiguration().getMinDepth() == 1;
		assert report.getConfiguration().getNumThreads() == 1;
		assert report.getConfiguration().isSortEnabled() == true;
	}

}
