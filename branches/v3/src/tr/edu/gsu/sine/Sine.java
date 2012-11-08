package tr.edu.gsu.sine;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import tr.edu.gsu.sine.col.Collection;
import tr.edu.gsu.sine.ext.Extractor;
import tr.edu.gsu.sine.ext.FlexibleMetric;
import tr.edu.gsu.sine.ext.Profile;
import tr.edu.gsu.sine.in.Digger;
import tr.edu.gsu.sine.in.Language;
import tr.edu.gsu.sine.net.Network;
import tr.edu.gsu.sine.out.NetworkWriter;
import tr.edu.gsu.sine.out.PajekDotNetWriter;
import tr.edu.gsu.sine.out.SineLogFormatter;
import tr.edu.gsu.sine.ui.CmdLine;
import tr.edu.gsu.sine.ui.GraphicalUI;

/**
 * Application main point entry and globals.
 */
public class Sine {
	
	/**
	 * Collection of web services, from which networks are extracted.
	 */
	private static Collection collection;
	
	/**
	 * Logger of the application.
	 */
	private static Logger logger;

	/**
	 * Log file handler of the application.
	 */
	private static FileHandler logFileHandler;
	
	/**
	 * New line character.
	 */
	public static final String NEW_LINE = System.getProperty("line.separator");

	/**
	 * Version of the application.
	 */
	public static final String VERSION = "0.4.17";

	/**
	 * Closes log handlers. It has to be called when shutting down the program.
	 */
	public static void closeLogHandlers() {
		for (Handler logHandler : logger.getHandlers()) {
			logHandler.close();
		}
	}

	/**
	 * Sets extraction directory and log handler up.
	 * <p>
	 * If it fails for any cause, it can still be retried later.
	 * 
	 * @throws IOException
	 *             if there are IO problems opening the log file
	 * @throws SecurityException
	 *             if there are problems with file permissions
	 */
	public static void endSetup() throws SecurityException, IOException {
		initExtractionDir();
		initLogHandler();
	}

	/**
	 * Extract networks using profiles in {@link Batch#TODO} and write them.
	 * 
	 * @param col the collection to extract networks from
	 */
	public static void extractNetworks(Collection col) {
		
		Extractor extractor = new Extractor(col);
		Network network;
		String filepath = "";
		NetworkWriter writer = null;
		List<Profile> profilesToDo = Batch.TODO.getContent();
		
		int nbProfiles = profilesToDo.size();
		int profileIndex = 0;
		
		// Extract each of profiles in the todo list.
		for (Profile profile : profilesToDo) {
			
			// Extract the network.
			profileIndex++;
			logger.info("Extracting profile #" + profileIndex + "/"
					+ nbProfiles + ": " + profile.getDescription());
			network = extractor.proceed(profile);
			Batch.DONE.put(profile);
			
			try {
				// Write it to a file in Pajek .net format.
				filepath = Path.EXTRACTION.getValue() + File.separator
						+ profile.toAbbrev() + ".net";
				writer = new PajekDotNetWriter(filepath);
				writer.write(network);
			} catch (IOException e2) {
				logger.severe("Can not write network to '" + filepath + "': "
						+ e2.getMessage());
				e2.printStackTrace();
			} finally {
				// In both cases, truly close the file.
				try {
					writer.close();
				} catch (Exception e3) {
					logger.severe("Can not close '" + filepath + "': "
							+ e3.getMessage());
					e3.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Returns the collection of web services descriptions.
	 * 
	 * @return the collection of web services descriptions
	 */
	public static Collection getCollection() {
		return collection;
	}
	
	public static void setCollection(Collection col) {
		collection = col;
	}

	/**
	 * Returns SINE application's global logger.
	 * <p>
	 * This logger gathers noticeable events and is written down into log file.
	 * 
	 * @return SINE application's global logger
	 * @see #endSetup()
	 */
	public static Logger getLogger() {
		return logger;
	}

	/**
	 * Initializes extraction directory.
	 * <p>
	 * If it fails for any cause, it can still be retried later.
	 * 
	 * @throws SecurityException
	 *             if there are problems with file permissions
 	 */
	private static void initExtractionDir() throws SecurityException {
		File dir = new File(Path.EXTRACTION.getValue());
		if (!dir.exists()) {
			dir.mkdirs();
		}
		if (!(dir.isDirectory() && dir.canWrite()))
			throw new SecurityException(
					"Extraction directory is not writeable: "
							+ dir.getAbsolutePath());
	}

	/**
	 * Initializes application's log handler.
	 * <p>
	 * If it fails for any cause, it can still be retried later.
	 * 
	 * @throws IOException
	 *             if there are IO problems opening the log file
	 * @throws SecurityException
	 *             if there are problems with file permissions
	 */
	private static void initLogHandler() throws SecurityException, IOException {
		String logPath = Path.EXTRACTION.getValue() + File.separator + "log";
		logger.removeHandler(logFileHandler);
		logFileHandler = new FileHandler(logPath, true);
		logFileHandler.setFormatter(new SineLogFormatter());
		logger.addHandler(logFileHandler);
		logger.info("SINE " + VERSION);
	}

	/**
	 * Main point entry of the application.
	 * 
	 * @param args
	 *            arguments on the command-line
	 */
	public static void main(String[] args) {
		// Parse (and apply) command-line arguments.
		CmdLine.parseArguments(args);

		// Initialize SINE logger.
		logger = Logger.getLogger("SINE");
		logger.setUseParentHandlers(false);
		
		// Logs handler will be initialized later (with extraction directory).
		logFileHandler = null;

		// If no one task is requested, then open a window.
		if (Batch.TODO.getContent().isEmpty()) {
			GraphicalUI.start();
		} else {
			runWithoutGUI();
		}
	}

	/**
	 * Runs SINE in non-interactive mode (without Graphical User Interface).
	 * <p>
	 * It proceeds to setup checking, collection parsing and extraction of
	 * selected networks. If any of needed parameters is wrong or missing,
	 * it stops and shuts the program down.
	 */
	private static void runWithoutGUI() {
		logger.addHandler(new ConsoleHandler());

		try {
			endSetup();
		} catch (Exception e) {
			logger.severe("Can not end setup: " + e.getMessage());
			e.printStackTrace();
			closeLogHandlers();
			System.exit(1);
		}

		try {
			loadCollection();
			// Set flexible matching threshold
			Float threshold = Float.valueOf(Option.FLEX.getValue());
			FlexibleMetric.setThreshold(threshold.floatValue());
			// Write some reports (parameters, names, collection, ...)
			SecondaryTask.executeAll();
		} catch (Exception e) {
			logger.severe("Can not parse collection or write parameters: "
					+ e.getMessage());
			e.printStackTrace();
			closeLogHandlers();
			System.exit(1);
		}

		extractNetworks(Sine.getCollection());
	}

	/**
	 * Serialize a collection to an XML human-readable document.
	 * 
	 * @param col
	 *            the collection to serialize
	 * @return XML document representing the collection
	 * @throws ParserConfigurationException
	 */
	public static Document serialize(Collection col)
			throws ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.newDocument();
		document.appendChild(col.toXML(document));
		return document;
	}

	/**
	 * Deserialize a collection from an XML document.
	 * 
	 * @param doc
	 *            XML document representing the collection
	 * @return a collection
	 */
	public static Collection deserialize(Document doc) {
		NodeList children = doc.getElementsByTagName("collection");
		return new Collection((Element) children.item(0));
	}

	/**
	 * Load a collection from {@link tr.edu.gsu.sine.Path#COLLECTION}.
	 * 
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	public static void loadCollection() throws ParserConfigurationException,
			SAXException, IOException {
		File colPath = new File(Path.COLLECTION.getValue());
		Collection col;
		if (colPath.isFile() && colPath.getName().endsWith(".xml")) {
			// Parse collection XML file
			DocumentBuilderFactory dbf = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(colPath);
			col = Sine.deserialize(doc);
		} else {
			// Dig collection directory
			Digger digger = new Digger(logger);
			Language lang = Language.valueOf(Option.LANG.getValue());
			String name = Option.NAME.getValue();
			col = digger.dig(colPath, lang, name);
		}
		Sine.setCollection(col);
	}
}
