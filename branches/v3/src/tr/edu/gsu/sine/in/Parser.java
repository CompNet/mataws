package tr.edu.gsu.sine.in;

import java.io.File;
import java.util.logging.Logger;

import tr.edu.gsu.sine.col.Service;

/**
 * Defines the methods components required to parse a web service description
 * file.
 */
public interface Parser {

	/**
	 * Returns the web service described by the file.
	 * 
	 * @param file
	 *            the web service description file
	 * @param relativePath
	 *            the relative path of the description file in the collection
	 * @return the web service described by the file
	 */
	public Service parse(File file, String relativePath);
	
	/**
	 * Sets the logger to be used for warnings and errors.
	 * 
	 * @param logger
	 *            the logger to be used for warnings and errors
	 */
	public void setLogger(Logger logger);
}
