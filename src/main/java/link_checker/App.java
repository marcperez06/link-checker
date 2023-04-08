package link_checker;

import io.github.marcperez06.java_utilities.file.FileUtils;
import io.github.marcperez06.java_utilities.json.GsonUtils;
import link_checker.information.Paths;
import link_checker.report.LinkCheckerReport;
import link_checker.report.configuration.builder.LinkCheckerConfigurationBuilder;
import link_checker.report.services.LinkCheckerService;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
    	
    	String url = "https://www.fao.org/home/es";
    	
    	//LinkCheckerService.getPageInfo("https://vandal.elespanol.com");
    	LinkCheckerReport linkCheckerInfo = LinkCheckerService.getReport(url, new LinkCheckerConfigurationBuilder().minDepth(0).build());
    	
    	String result = GsonUtils.getPrettyJSON(linkCheckerInfo);
    	FileUtils.writeTxt(result, Paths.OUTPUT_FOLDER + "results.txt");
    	
    	//System.out.println(result);
    }
}
