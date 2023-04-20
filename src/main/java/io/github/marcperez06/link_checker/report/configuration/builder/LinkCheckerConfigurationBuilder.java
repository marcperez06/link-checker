package io.github.marcperez06.link_checker.report.configuration.builder;

import io.github.marcperez06.link_checker.report.configuration.LinkCheckerConfiguration;

public class LinkCheckerConfigurationBuilder {
	
	private LinkCheckerConfiguration buildedObject;

    private Integer minDepth;

    private Integer minRequests;

    private Integer minInteractions;

    private boolean sortNotFoundFirst;

    private int numThreads;
    
    private String outputReportPath;
    
    private String baseReportName;

    public LinkCheckerConfigurationBuilder() {
        this.buildedObject = null;
        this.minDepth = Integer.valueOf(1);
        this.sortNotFoundFirst = true;
        this.numThreads = 1;
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
        this.buildedObject.setSortNotFoundFirst(this.sortNotFoundFirst);
        this.buildedObject.setNumThreads(this.numThreads);
        this.buildedObject.setOutputReportPath(this.outputReportPath);
        this.buildedObject.setBaseReportName(this.baseReportName);
        return this.buildedObject;
    }

}
