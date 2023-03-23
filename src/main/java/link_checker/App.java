package link_checker;

import io.github.marcperez06.java_utilities.file.FileUtils;
import io.github.marcperez06.java_utilities.json.GsonUtils;
import link_checker.information.Paths;
import link_checker.objects.LinkCheckerInfo;
import link_checker.services.LinkCheckerService;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
    	
    	//LinkCheckerService.getPageInfo("https://vandal.elespanol.com");
    	LinkCheckerInfo linkCheckerInfo = LinkCheckerService.getPageInfo("https://vivus.es");
    	
    	String result = GsonUtils.getJSON(linkCheckerInfo);
    	FileUtils.writeTxt(result, Paths.OUTPUT_FOLDER + "results.txt");
    	
    	System.out.println(result);
    }
}
