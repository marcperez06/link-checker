package io.github.marcperez06.link_checker.report.configuration.builder;

import java.util.List;

import io.github.marcperez06.java_utilities.strings.StringUtils;
import io.github.marcperez06.link_checker.report.configuration.LinkCheckerConfiguration;

public class LinkCheckerConfigurationBuilder {
	
	private LinkCheckerConfiguration buildedObject;

    private Integer minDepth;

    private Integer minRequests;

    private Integer minInteractions;
    
    private List<String> domainWithelist;

    private boolean sortNotFoundFirst;

    private int numThreads;
    
    private String outputReportPath;
    
    private String baseReportName;

    public LinkCheckerConfigurationBuilder() {
        this.buildedObject = null;
        this.minDepth = Integer.valueOf(1);
        this.sortNotFoundFirst = true;
        this.numThreads = 1;
        this.domainWithelist = null;
        this.outputReportPath = null;
        this.baseReportName = "report";
    }

    public Integer minDepth() {
        return this.minDepth;
    }

    public LinkCheckerConfigurationBuilder minDepth(Integer maxDepth) {
        this.minDepth = maxDepth;
        return this;
    }

    public Integer minRequests() {
        return this.minRequests;
    }

    public LinkCheckerConfigurationBuilder minRequests(Integer maxRequests) {
        this.minRequests = maxRequests;
        return this;
    }

    public Integer minInteractions() {
        return this.minInteractions;
    }

    public LinkCheckerConfigurationBuilder minInteractions(Integer maxInteractions) {
        this.minInteractions = maxInteractions;
        return this;
    }

    public boolean sortNotFoundFirst() {
        return this.sortNotFoundFirst;
    }

    public LinkCheckerConfigurationBuilder sortNotFoundFirst(boolean sortNotFoundFirst) {
        this.sortNotFoundFirst = sortNotFoundFirst;
        return this;
    }

    public int numThreads() {
        return this.numThreads;
    }

    public LinkCheckerConfigurationBuilder numThreads(int numThreads) {
        this.numThreads = numThreads;
        return this;
    }
    
    public List<String> domainWithelist() {
        return this.domainWithelist;
    }
    
    public LinkCheckerConfigurationBuilder domainWithelist(String domainWithelist) {
    	LinkCheckerConfigurationBuilder currentBuilder = this;
    	if (domainWithelist != null) {
    		currentBuilder = this.domainWithelist(StringUtils.splitList(domainWithelist, ","));
    	}
    	return currentBuilder;
    }

    public LinkCheckerConfigurationBuilder domainWithelist(List<String> domainWithelist) {
        this.domainWithelist = domainWithelist;
        return this;
    }
    
    public String outputReportPath() {
        return this.baseReportName;
    }

    public LinkCheckerConfigurationBuilder outputReportPath(String outputReportPath) {
        this.outputReportPath = outputReportPath;
        return this;
    }
    
    public String baseReportName() {
        return this.baseReportName;
    }

    public LinkCheckerConfigurationBuilder baseReportName(String baseReportName) {
        this.baseReportName = baseReportName;
        return this;
    }

    public LinkCheckerConfiguration build() {
        this.buildedObject = new LinkCheckerConfiguration();
        this.buildedObject.setMinDepth(this.minDepth);
        this.buildedObject.setMinRequests(this.minRequests);
        this.buildedObject.setMinInteractions(this.minInteractions);
        this.buildedObject.setDomainWithelist(this.domainWithelist);
        this.buildedObject.setSortNotFoundFirst(this.sortNotFoundFirst);
        this.buildedObject.setNumThreads(this.numThreads);
        this.buildedObject.setOutputReportPath(this.outputReportPath);
        this.buildedObject.setBaseReportName(this.baseReportName);
        return this.buildedObject;
    }

}
