package io.github.marcperez06.link_checker.report.link.validation;

import io.github.marcperez06.java_utilities.strings.StringUtils;

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

}
