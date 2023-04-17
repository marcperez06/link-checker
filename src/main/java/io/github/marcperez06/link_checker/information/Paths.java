package io.github.marcperez06.link_checker.information;

public class Paths {
	
	public static final String FILE_SEPARATOR = System.getProperty("file.separator");
	public static final String USER_DIR = System.getProperty("user.dir");
	public static final String OUTPUT_FOLDER = USER_DIR + FILE_SEPARATOR + "output" + FILE_SEPARATOR;
	public static final String RESOURCES_FOLDER = USER_DIR + FILE_SEPARATOR + "resources" + FILE_SEPARATOR;
	private static final String PROPERTIES_FOLDER = RESOURCES_FOLDER + "properties" + FILE_SEPARATOR;
	public static final String PROPERTIES_PATH = PROPERTIES_FOLDER + "link_checker.properties";

}