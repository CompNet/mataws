package tr.edu.gsu.sine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import tr.edu.gsu.sine.ext.FlexibleMetric;
import tr.edu.gsu.sine.out.ParamsWriter;

/**
 * Provides secondary tasks, not directly related to networks extraction.
 * 
 * If they fail (with any exception), these tasks can be retried later again. 
 */
public enum SecondaryTask {
	/**
	 * List parameters unique names, sorted alphabetically.
	 */
	LIST_PARAMETERS("list all parameters by unique name", true) {
		@Override
		public void execute() throws IOException {
			File namesFile = new File(Path.EXTRACTION.getValue()
					+ File.separator + "parameters_names.txt");
			Sine.getLogger().info(
					"Writing parameters names to: " + namesFile.getName());
			ParamsWriter writer = new ParamsWriter(namesFile);
			writer.writeByName(Sine.getCollection());
			writer.close();
		}
	},
	
	/**
	 * List parameters names, sorted by the number of occurrences.
	 */
	COUNT_PARAMETERS("count occurrences of parameters names", true) {
		@Override
		public void execute() throws FileNotFoundException {
			File statsFile = new File(Path.EXTRACTION.getValue()
					+ File.separator + "parameters_stats.txt");
			Sine.getLogger().info(
					"Writing parameters stats to: " + statsFile.getName());
			ParamsWriter writer = new ParamsWriter(statsFile);
			writer.writeByCount(Sine.getCollection());
			writer.close();
		}
	},
	
	/**
	 * Evaluate the flexible matching of parameters names.
	 */
	EVAL_FLEXIBLE("evaluate the flexible matching of parameters names", false) {
		@Override
		public void execute() {
			File outDir = new File(Path.EXTRACTION.getValue());
			for (FlexibleMetric fm : FlexibleMetric.values()) {
				Sine.getLogger().info("Evaluating " + fm + " metric.");
				FlexibleMetricEval fmEval = new FlexibleMetricEval(fm, Sine
						.getCollection(), outDir);
				fmEval.run();
			}
		}
	},
	
	/**
	 * Test composition algorithms.
	 */
	TEST_COMPOSITION("test composition algorithms", false) {
		@Override
		public void execute() throws Exception {
			// FIXME: see package sine.comp in branch 0.8.x
		}
	},

	/**
	 * Serialize the collection to an XML file.
	 */
	XML_SERIALIZE("serialize the collection to an XML file", false) {
		@Override
		public void execute() throws TransformerException,
				ParserConfigurationException {
			File colFile = new File(Path.EXTRACTION.getValue() + File.separator
					+ "collection.xml");
			Sine.getLogger()
					.info("Writing collection to: " + colFile.getName());
			Document doc = Sine.serialize(Sine.getCollection());
			DOMSource src = new DOMSource(doc);
			StreamResult res = new StreamResult(colFile);
			TransformerFactory trFactory = TransformerFactory.newInstance();
			Transformer tr = trFactory.newTransformer();
			tr.setOutputProperty(OutputKeys.VERSION, Sine.VERSION);
			tr.setOutputProperty(OutputKeys.INDENT, "yes");
			tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount",
					"2");
			tr.transform(src, res);
		}
	};

	private final String description;

	/**
	 * True if this secondary task must be executed.
	 */
	private boolean enabled;
	
	/**
	 * This constructor is private, as required for an enumerated class.
	 */
	private SecondaryTask(String description, boolean enabled) {
		this.description = description;
		this.enabled = enabled;
	}

	/**
	 * Execute this secondary task.
	 * 
	 * @throws Exception
	 *             mostly thrown in case of IO problem.
	 */
	public abstract void execute() throws Exception;

	/**
	 * Returns a brief description of this secondary task.
	 * 
	 * @return a brief description of this secondary task
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Returns true if this secondary task must be executed.
	 * 
	 * @return true if this secondary task must be executed
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * Enables or disables the execution of this secondary task.
	 * 
	 * @param enabled
	 *            true if this task must be executed
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	/**
	 * Executes all of enabled secondary tasks.
	 * <p>
	 * If it fails for any cause, it can still be retried later.
	 * 
	 * @throws Exception
	 *             mostly in case of IO problem
	 */
	public static void executeAll() throws Exception {
		for (SecondaryTask et : SecondaryTask.values()) {
			if (et.isEnabled()) {
				et.execute();
			}
		}
		Sine.getLogger().info("Getting done with all secondary tasks.");
	}
}
