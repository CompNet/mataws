package tr.edu.gsu.sine.in;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import tr.edu.gsu.sine.col.Collection;
import tr.edu.gsu.sine.col.Service;

/**
 * Provides collection directory digger.
 */
public class Digger {

	/**
	 * Logger to be used for warnings and errors logs.
	 */
	private Logger logger;

	/**
	 * Creates a digger with default anonymous logger.
	 * 
	 * @param logger
	 *            the logger to be used for warnings and errors
	 */
	public Digger(Logger logger) {
		this.logger = logger;
	}

	/**
	 * Searches and parses web descriptions files to populate collection.
	 * <p>
	 * It searches recursively through directories beginning with the given
	 * top-level directory, for files written in the given description language.
	 * <p>
	 * Then it parses each of these files with the appropriated parser.
	 * <p>
	 * Finally it adds the resulting service to the collection, except if a web
	 * service of same name and location already exists in the collection.
	 * 
	 * @param topDir
	 *            the top-level directory of the collection
	 * @param lang
	 *            the language describing services in the collection
	 * @param colName
	 *            the name to be attributed to the resulting collection
	 * @return A named collection services described by files written in the
	 *         given language and located at the given path.
	 * @throws FileNotFoundException
	 *             if there are no description files of the given language in
	 *             the given path.
	 */
	public Collection dig(File topDir, Language lang, String colName)
			throws FileNotFoundException {

		// Create the collection with meta-data.
		Collection collection = new Collection(colName, lang);

		// List files of target language.
		logger.info("Opening Directory: " + topDir.getAbsolutePath());
		List<File> files = listFiles(topDir, lang);
		if (files == null || files.isEmpty()) {
			throw new FileNotFoundException("No *." + lang.getExtension()
					+ " file found in collection directory: "
					+ topDir.getAbsolutePath());
		}

		// Initialize parser of target language.
		Parser parser = lang.getParser();
		parser.setLogger(logger);

		// Parse each of the listed files.
		logger.info(files.size() + " files in process" + ".");
		for (int i = 0; i < files.size(); i++) {
			String relativePath = relativize(files.get(i), topDir);
			logger.info("Parsing file " + (i + 1) + "/" + files.size() + ": "
					+ relativePath);
			Service service = parser.parse(files.get(i), relativePath);

			// Try to add the resulting service to the collection.
			try {
				if (service == null) {
					throw new Exception("File " + relativePath
							+ " has not been parsed properly.");
				}
				// This throws an exception in case of duplicated service.
				collection.addService(service);
			} catch (Exception e) {
				logger.warning("Can not add service: " + e.getMessage());
			}
		}

		// Log a summarized report.
		logger.log(collection.getServices().size() == files.size() ? Level.INFO
				: Level.WARNING, collection.getServices().size() + " of "
				+ files.size() + " have been added to the collection.");

		return collection;
	}

	/**
	 * Recursively list the services description files of the collection.
	 * 
	 * @param colDir
	 *            the root directory of the collection
	 * @param lang
	 *            the language of files to list
	 * 
	 * @return the list of services description files of the collection
	 */
	private List<File> listFiles(File colDir, final Language lang) {
		List<File> allFiles = new ArrayList<File>();

		// List files that have the expected name extension.
		File[] subFiles = colDir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.isFile()
						&& file.getName().endsWith("." + lang.getExtension());
			}
		});
		if (subFiles == null) {
			logger.warning("Collection path '" + colDir.getPath()
					+ "' does not denote a directory.");
		} else {
			allFiles.addAll(Arrays.asList(subFiles));
		}

		// Recursively list files in sub-directories.
		File[] subDirs = colDir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.isDirectory();
			}
		});
		if (subDirs == null) {
			logger.warning("Collection path '" + colDir.getPath()
					+ "' does not denote a directory.");
		} else {
			for (File sub : subDirs) {
				allFiles.addAll(listFiles(sub, lang));
			}
		}

		return allFiles;
	}

	/**
	 * Returns the relative path string of a description file in a collection.
	 * 
	 * @param descFile
	 *            description file pathname
	 * @param colDir
	 *            collection directory pathname
	 * @return relative path string of the description file in the collection
	 */
	private static String relativize(File descFile, File colDir) {
		URI descURI = descFile.getAbsoluteFile().toURI();
		URI colURI = colDir.getAbsoluteFile().toURI();
		return colURI.relativize(descURI).getPath();
	}
	
	/**
	 * Sets the logger to be used for warnings and errors.
	 * 
	 * @param logger
	 *            the logger to be used for warnings and errors
	 */
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

}
