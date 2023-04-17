package io.github.marcperez06.link_checker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import io.github.marcperez06.java_utilities.date.DateUtils;
import io.github.marcperez06.java_utilities.file.FileUtils;
import io.github.marcperez06.java_utilities.json.GsonUtils;
import io.github.marcperez06.java_utilities.strings.StringUtils;
import io.github.marcperez06.java_utilities.validation.ValidationUtils;
import io.github.marcperez06.link_checker.report.LinkCheckerReport;
import io.github.marcperez06.link_checker.report.configuration.LinkCheckerConfiguration;
import io.github.marcperez06.link_checker.report.configuration.factory.LinkCheckerConfigurationFactory;
import io.github.marcperez06.link_checker.report.services.LinkCheckerService;

/**
 * Can execute with arguments or without arguments, if execute without arguments, terminal request for arguments
 * Execute with arguments: java -jar link-checker.jar {url} [propertiesPath] [outputFolderPath]
 * Execute without arguments: java -jar link-checker.jar
 * Arguments:
 * Arg 0 - Urls separated by coma, can write only one
 * Arg 1 (Optional) - Path for properties, if indicates default or not indicates, use default properties
 * Arg 2 (Optional) - Output folder path
 * @author Marc Perez Rodriguez
 */
public class Main {
	
    public static void main(String[] args) {
    	if (haveArguments(args)) {
    		executionWithArguments(args);
    	} else {
    		executionWithoutArguments();
    	}
    }
    
    private static boolean haveArguments(String[] args) {
    	return (args != null && args.length > 0);
    }
    
    private static void executionWithArguments(String[] args) {
    	List<LinkCheckerReport> reports = null;
    	
    	print("ARGS ---> " + args.toString());
    	
    	if (argumentsAreCorrect(args)) {
    		List<String> urls = StringUtils.splitList(args[0], "\\,");
    		print("URLS -----> " + urls);
    		LinkCheckerConfiguration configuration = createConfiguration(args[1]);
    		reports = LinkCheckerService.getReports(urls, configuration);
    		String result = GsonUtils.getPrettyJSON(reports);
    		
    		if (ValidationUtils.isNotNull(args[2])) {
    			String fileName = "results_" + DateUtils.getCurrentTime() + ".txt";
    			FileUtils.writeTxt(result, args[2] + System.getProperty("file.separator") + fileName);
    		} else {
    			print(result);
    		}
    		
    	} else {
    		throw new RuntimeException("Arguments not are correct");
    	}
    }
    
    private static boolean argumentsAreCorrect(String[] args) {
    	boolean argsAreOk = haveArguments(args);
    	argsAreOk &= args.length < 4;
    	return argsAreOk;
    }
    
    private static LinkCheckerConfiguration createConfiguration(String propertiesPath) {
    	print("PROPERTEIS PATH ---> " + propertiesPath);
    	LinkCheckerConfiguration config = LinkCheckerConfigurationFactory.createDefaultConfiguration();
    	if (ValidationUtils.isNotNull(propertiesPath) && !ValidationUtils.equalsIgnoreCase(propertiesPath, "default")) {
    		config = LinkCheckerConfigurationFactory.createConfiguration(propertiesPath);
    	}
    	return config;
    }
    
    private static void executionWithoutArguments() {
    	String[] fakeArgs = new String[3];
    	
    	fakeArgs[0] = request("Insert url or urls (spearated by coma) to check links");
		
		String decision = request("Want specify properties from file? y / n or yes / no");
		if (ValidationUtils.equalsIgnoreCase(decision, "y") || ValidationUtils.equalsIgnoreCase(decision, "yes")) {
			fakeArgs[1] = request("Write properties path");
		} else {
			fakeArgs[1] = "default";
		}
		
		decision = request("Want write results in file? y / n or yes / no");
		if (ValidationUtils.equalsIgnoreCase(decision, "y") || ValidationUtils.equalsIgnoreCase(decision, "yes")) {
			fakeArgs[2] = request("Write output folder path");
		} else {
			fakeArgs[2] = null;
		}
		
    	executionWithArguments(fakeArgs);
    }
    
    private static void print(String message) {
    	System.out.println(message);
    }
    
    private static String request(String message) {
    	String readLine = "";
    	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    	print(message);
    	try {
    		readLine = reader.readLine();
    	} catch (IOException e) {
    		print("Can not read what you write, reading as empty");
    	}
    	return readLine;
    }

}