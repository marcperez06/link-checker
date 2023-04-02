package link_checker.report.link.comparator;

import java.util.Comparator;

import io.github.marcperez06.java_utilities.validation.ValidationUtils;
import link_checker.report.link.LinkInfo;
import link_checker.report.link.enums.Status;

public class LinkInfoComparator implements Comparator<LinkInfo> {
	
	@Override
    public int compare(LinkInfo infoA, LinkInfo infoB) {
		int compareResult = 0;
		boolean areNotNull = ValidationUtils.isNotNull(infoA) && ValidationUtils.isNotNull(infoB);
		if (areNotNull && infoA.getStatus() != infoB.getStatus()) {
			if (infoB.getStatus() == Status.OK) {
				compareResult = -3;
			} else if (infoB.getStatus() == Status.REQUEST_DENIED){
				compareResult = -2;
			} else if (infoB.getStatus() == Status.UNEXPECTED){
				compareResult = -1;
			} else {
				compareResult = 1;
			}
		}
		return compareResult;
    }

}
