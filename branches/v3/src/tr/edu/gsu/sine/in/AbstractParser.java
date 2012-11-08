package tr.edu.gsu.sine.in;

import java.io.File;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

import tr.edu.gsu.sine.col.Service;

/**
 * Provides facilities for implementing a SAX-based parser for web service
 * description files.
 */
public abstract class AbstractParser implements Parser {
	
	/**
	 * Name of the parsed file.
	 */
	protected String filename;
	
	/**
	 * Relative path of the parsed file in the collection.
	 */
	protected String relativePath;
	
	/**
	 * Logger to be used for warnings and errors logs.
	 */
	protected Logger logger;

	/**
	 * Returns a formal XML path string of the mark-ups names.
	 * 
	 * @param names
	 *            the mark-ups names
	 * @return a formal XML path string
	 */
	protected String buildXPath(String ... names) {
		String xpath = "/";
		for (String name : names) {
			xpath += "/*[local-name()='" + name + "']";
		}
		return xpath;
	}

	/**
	 * Formats and logs an exception with a specific error message.
	 * 
	 * @param e
	 *            exception to be logged
	 * @param msg
	 *            specific error message
	 */
	protected void logException(Exception e, String msg) {
		logger.severe("Parser error: " + relativePath + ": " + msg + ": "
				+ e.getMessage());
	}
	
	/**
	 * Formats and logs a specific warning message.
	 * 
	 * @param msg
	 *            specific warning message
	 */
	protected void logWarning(String msg) {
		logger.warning("Parser warning: " + relativePath + ": " + msg);
	}

	/**
	 * Parses an XML document describing a web service.
	 * 
	 * @param doc
	 *            the XML document describing a web service
	 * @return a Service built from that description
	 */
	public abstract Service parse(Document doc);
	
	/* (non-Javadoc)
	 * @see sine.in.Parser#parse(java.io.File, java.lang.String)
	 */
	@Override
	public Service parse(File file, String relativePath) {
		DocumentBuilderFactory dbf;
		DocumentBuilder db;
		Document doc;
		
		// get the factory
		dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		
		// Using factory get an instance of document builder
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			logger.severe("Failed to parse file '" + relativePath + "': "
					+ e.getMessage());
			e.printStackTrace();
			return null;
		}
		
		try {
			doc = db.parse(file);
		} catch (Exception e) {
			logger.severe("Failed to parse file '" + relativePath + "': "
					+ e.getMessage());
			e.printStackTrace();
			return null;
		}
		
		this.filename = doc.getDocumentURI();
		this.relativePath = relativePath;
		
		return parse(doc);
	}

	/* (non-Javadoc)
	 * @see sine.parse.Parser#setLogger(java.util.logging.Logger)
	 */
	@Override
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	/**
	 * Returns the identifier string without name-space prefix.
	 * 
	 * @param identifier
	 *            the string identifier to strip
	 * @return the identifier string without name-space prefix
	 */
	protected String stripNameSpace(String identifier) {
		return identifier.substring(identifier.lastIndexOf(':') + 1);
	}
	
	/**
	 * Returns an user-friendly string of xpath.
	 * 
	 * @param xpath
	 *            the formal XML path
	 * @return an user-friendly string of xpath
	 */
	protected String xpathToString(String xpath) {
		String[] names = xpath.split("/");
		String friendly = "";
		for (String name : names) {
			friendly += "/"
					+ (name.indexOf('\'') == -1 ? name : name.substring(name
							.indexOf('\''), name.lastIndexOf('\'')));
		}
		return friendly;
	}
}
