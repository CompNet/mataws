package tr.edu.gsu.sine;

import java.io.File;

/**
 * Provides an enumeration of global paths, stating their name and value.
 * <p>
 * Descriptions and abbreviations are intended for user interface.
 * <p>
 * The default value of a path is its lower-cased name.
 */
public enum Path {
	
	/**
	 * Path to a directory containing web services descriptions files.  
	 */
	COLLECTION("Path of the collection directory (or an XML file)") {
		@Override
		public boolean isValid() {
			File file = new File(getValue());
			return file.canRead() && (file.isDirectory()
					|| file.isFile() && file.getName().endsWith(".xml"));
		}
	},
	
	/*
	 * Path to a directory containing mirrored repositories of ontologies.
	 * <p>
	 * FIXME: modify URI of namespaces as they are mirrored.
	 * @see sine.ext.OntologyUtils
	 
	ONTOLOGIES("Path of the mirrored ontologies") {
	@Override
	public boolean isValid() {
		// Ontologies are not required for syntactic matching. 
		return true;
	},
	
	*/
	
	/**
	 * Path to an empty directory going to contain extracted networks files.
	 */
	EXTRACTION("Path of the extraction directory") {
		@Override
		public boolean isValid() {
			File file = new File(getValue());
			return file.isDirectory() && file.canWrite();
		}
	};
	
	/**
	 * Returns true if all the required paths point to existing
	 * directories that can be respectively read/write.
	 * <p>
	 * The ontologies path is not checked as it is only mandatory for semantics.
	 * 
	 * @return true if the collection/extraction paths are correctly set.
	 */
	public static boolean areValid() {
		for (Path path : values()) {
			if (!path.isValid()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Description, for user information.
	 */
	private final String description;
	
	/**
	 * String value of the path, might not be valid though.
	 */
	private String value;
	
	/**
	 * Creates a path with its description and default value as its lower-case.
	 * <p>
	 * This constructor is private, as required for an enumerated class.
	 * 
	 * @param description 
	 */
	private Path(String description) {
		this.description = description;
		this.value = name().toLowerCase();
	}

	/**
	 * Returns description, for user information.
	 * 
	 * @return informative description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Returns string value, might not be valid though.
	 * 
	 * @return string value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the value of this path.
	 * 
	 * @param value
	 *            the value of this path
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	/**
	 * Returns true if the path value is valid according to file permissions.
	 * 
	 * @return true if the path value is valid according to file permissions
	 */
	public abstract boolean isValid();
}
