package io.github.marcperez06.link_checker.report.configuration.factory;

import io.github.marcperez06.java_utilities.file.FileUtils;
import io.github.marcperez06.java_utilities.properties.EnvironmentProperties;
import io.github.marcperez06.java_utilities.validation.ValidationUtils;
import io.github.marcperez06.link_checker.information.Paths;
import io.github.marcperez06.link_checker.information.PropertiesKeys;
import io.github.marcperez06.link_checker.report.configuration.LinkCheckerConfiguration;
import io.github.marcperez06.link_checker.report.configuration.builder.LinkCheckerConfigurationBuilder;

public class LinkCheckerConfigurationFactory {
	
	private LinkCheckerConfigurationFactory() {}
	
	public static LinkCheckerConfiguration createConfiguration() {
		LinkCheckerConfiguration config = createDefaultConfiguration();
		
		if (FileUtils.existFile(Paths.PROPERTIES_PATH)) {
			config = createConfiguration(Paths.PROPERTIES_PATH);
		}
		
		return config;
	}
	
	public static LinkCheckerConfiguration createDefaultConfiguration() {
		return new LinkCheckerConfigurationBuilder().build();
	}
	
	public static LinkCheckerConfiguration createConfiguration(String propertiesPath) {
		LinkCheckerConfigurationBuilder builder = new LinkCheckerConfigurationBuilder();
		EnvironmentProperties properties = new EnvironmentProperties();
		properties.loadPropertiesFromFile(propertiesPath);
		
		populateBuilder(builder, properties);
		clearBuilder(builder, properties);
		
		return builder.build();
	}
	
	private static void clearBuilder(LinkCheckerConfigurationBuilder builder, EnvironmentProperties properties) {
		
		if (ValidationUtils.equals(properties.getPropertyAsBoolean(PropertiesKeys.MIN_DEPTH_ENABLED), false)) {
			builder.minDepth(null);
		}
		
		if (ValidationUtils.equals(properties.getPropertyAsBoolean(PropertiesKeys.MIN_INTERACTIONS_ENABLED), false)) {
			builder.minInteractions(null);
		}
		
		if (ValidationUtils.equals(properties.getPropertyAsBoolean(PropertiesKeys.MIN_REQUESTS_ENABLED), false)) {
			builder.minRequests(null);
		}
		
		if (!properties.existProperty(PropertiesKeys.NUM_THREADS)) {
			builder.numThreads(1);
		}
		
		if (!properties.existProperty(PropertiesKeys.SORT_NOT_FOUND_FIRST)) {
			builder.sortNotFoundFirst(true);
		}
		
		if (builder.minDepth() == null && builder.minInteractions() == null && builder.minRequests() == null) {
			builder.minDepth(0).minInteractions(0).minRequests(0);
		}
		
	}
	
	private static void populateBuilder(LinkCheckerConfigurationBuilder builder, EnvironmentProperties properties) {
		if (ValidationUtils.equals(properties.getPropertyAsBoolean(PropertiesKeys.MIN_DEPTH_ENABLED), true)) {
			builder.minDepth(properties.getPropertyAsInteger(PropertiesKeys.MIN_DEPTH));
		}
		
		if (ValidationUtils.equals(properties.getPropertyAsBoolean(PropertiesKeys.MIN_INTERACTIONS_ENABLED), true)) {
			builder.minInteractions(properties.getPropertyAsInteger(PropertiesKeys.MIN_INTERACTIONS));
			
		}
		
		if (ValidationUtils.equals(properties.getPropertyAsBoolean(PropertiesKeys.MIN_REQUESTS_ENABLED), true)) {
			builder.minRequests(properties.getPropertyAsInteger(PropertiesKeys.MIN_REQUESTS));
		}
		
		if (properties.existProperty(PropertiesKeys.NUM_THREADS)) {
			builder.numThreads(properties.getPropertyAsInteger(PropertiesKeys.NUM_THREADS).intValue());
		}
		
		if (properties.existProperty(PropertiesKeys.SORT_NOT_FOUND_FIRST)) {
			builder.sortNotFoundFirst(properties.getPropertyAsBoolean(PropertiesKeys.SORT_NOT_FOUND_FIRST).booleanValue());
		}
	}

}
