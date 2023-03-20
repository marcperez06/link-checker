package link_checker;

import link_checker.services.LinkCheckerService;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
    	
    	//LinkCheckerService.getPageInfo("https://vandal.elespanol.com");
    	LinkCheckerService.getPageInfo("https://vivus.es");
    	
        System.out.println( "Hello World!" );
    }
}
