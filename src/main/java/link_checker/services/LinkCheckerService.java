package link_checker.services;

import io.github.marcperez06.java_utilities.api.request.Request;
import io.github.marcperez06.java_utilities.api.request.Response;
import io.github.marcperez06.java_utilities.api.request.ResponseTypeHolder;
import io.github.marcperez06.java_utilities.api.request.enums.HttpMethodEnum;
import io.github.marcperez06.java_utilities.api.rest.UnirestClient;
import link_checker.objects.LinkCheckerInfo;

public class LinkCheckerService {
	
	public static LinkCheckerService instance;
	
	private UnirestClient api;
	
	private LinkCheckerService() {
		this.api = new UnirestClient();
	}
	
	public static LinkCheckerService getInstance() {
		return (instance != null) ? instance : new LinkCheckerService();
	}
	
	public static void getPageInfo() {
		getInstance().fillLinkCheckerInfo();
	}
	
	private void fillLinkCheckerInfo() {
		this.getLinkStatus("https://vandal.elespanol.com");
	}
	
	private void getPageInfo(Object configuration) {
		
	}

	private void getLinkStatus(String link) {
		Request request = new Request(HttpMethodEnum.GET, link);
		request.setResponseType(new ResponseTypeHolder<String>() {});
		Response<String> response = api.send(request);
		this.fillLinkStatus(response);
	}
	
	private void fillLinkStatus(Response<String> response) {
		
	}
	
}