package tr.edu.gsu.sine;

import tr.edu.gsu.sine.ext.FlexibleMetric;
import tr.edu.gsu.sine.in.Language;

/**
 * Provides options names, descriptions and application methods.
 */
public enum Option {
	
	/**
	 * Option to set threshold for flexible matching.
	 * <p>
	 * The same threshold is applied to both string similarity metrics.
	 * 
	 * @see tr.edu.gsu.sine.ext.Trait#FLEXIBLE
	 * @see tr.edu.gsu.sine.ext.FlexibleMetric
	 */
	FLEX("<float>", "Flexible matching threshold in [0,1]",
			Float.toString(FlexibleMetric.getThreshold())) {
		@Override
		public void setValue(String stringThreshold) throws Exception {
			try {
				// Verify that it is a string representation of a float.
				Float threshold = Float.valueOf(stringThreshold);
				this.value = threshold.toString();
			} catch (NumberFormatException e) {
				throw new Exception("Invalid float max: " + stringThreshold);
			}
		}
	},
	
	/**
	 * Option to specify the language describing services in the collection.
	 * <p>
	 * This is used to search for services description files and for using
	 * the corresponding parser.
	 * 
	 * @see tr.edu.gsu.sine.in.Language
	 */
	LANG("[SA]WSDL", "Language of the collection",
			Language.SAWSDL.toString()) {
		@Override
		public void setValue(String langName) throws Exception {
			try {
				// Verify that it is a string representation of a language.
				Language lang = Language.valueOf(langName.toUpperCase());
				this.value = lang.toString();
			} catch (Exception e) {
				throw new Exception("Unknown language: " + langName);
			}
		}
	},
	
	/**
	 * Option to specify the name of the collection.
	 * <p>
	 * This is used in logs and networks meta-data, to keep track of collection.
	 */
	NAME("<name>", "Name of the collection",
			"Untitled") {
		@Override
		public void setValue(String colName) {
			this.value = colName;
		}
	};
	
	/** */
	private final String type;
	/** */
	private final String description;
	/** */
	protected String value;
	
	/**
	 * This constructor is private, as required for an enumerated class.
	 * 
	 * @param type 
	 * @param description 
	 * @param defaultValue 
	 */
	private Option(String type, String description, String defaultValue) {
		this.type = type;
		this.description = description;
		this.value = defaultValue;
	}
	
	/**
	 * @return ?
	 */
	public String getShort() {
		return name().substring(0, 1).toLowerCase();
	}
	
	/**
	 * @return ?
	 */
	public String getLong() {
		return name().toLowerCase();
	}
	
	/**
	 * @return ?
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * @return ?
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * @return ?
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * 
	 * @param value
	 * @throws Exception
	 */
	public abstract void setValue(String value) throws Exception;
}