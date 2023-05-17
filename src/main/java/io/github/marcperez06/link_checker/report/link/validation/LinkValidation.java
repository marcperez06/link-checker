package io.github.marcperez06.link_checker.report.link.validation;

import java.util.List;

import io.github.marcperez06.java_utilities.strings.StringUtils;
import io.github.marcperez06.java_utilities.validation.ValidationUtils;
import io.github.marcperez06.link_checker.report.LinkCheckerReport;

public class LinkValidation {
	
	private static final String MAILTO = "mailto:";
	private static final String JAVASCRIPT = "javascript:";
	private static final String PHONE = "tel:";
	
	public static boolean canCheck(String link) {
		boolean isValid = true;
		isValid &= !StringUtils.isBlank(link);
		isValid &= !StringUtils.valueContainsAnyWord(link, MAILTO, JAVASCRIPT, PHONE);
		return isValid;
	}
	
	public static boolean linkBelongsToDomainOrWithelist(String link, LinkCheckerReport report) {
		boolean belongs = false;
		List<String> withelist = report.getConfiguration().getDomainWithelist();
		belongs = link.contains(report.getDomain());
		if (!belongs) {
			belongs = StringUtils.valueContainsAnyWord(link, withelist);	
		}
		return belongs;
	}

}
