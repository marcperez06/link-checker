package io.github.marcperez06.link_checker.report.link.comparator;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import io.github.marcperez06.java_utilities.validation.ValidationUtils;
import io.github.marcperez06.link_checker.report.link.LinkInfo;
import io.github.marcperez06.link_checker.report.link.enums.Status;

public class LinkInfoComparator implements Comparator<LinkInfo> {
	
	private static final Map<Status, Integer> order = new HashMap<Status, Integer>() {{
		put(Status.NOT_FOUND, 1);
		put(Status.EXCEPTION, -1);
		put(Status.UNEXPECTED, -2);
		put(Status.REQUEST_DENIED, -3);
		put(Status.OK, -4);
	}};
	
	@Override
    public int compare(LinkInfo infoA, LinkInfo infoB) {
		int compareResult = 0;
		boolean areNotNull = ValidationUtils.isNotNull(infoA) && ValidationUtils.isNotNull(infoB);
		if (areNotNull && infoA.getStatus() != infoB.getStatus()) {
			compareResult = order.get(infoB.getStatus());
		}
		return compareResult;
    }

}
