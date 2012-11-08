package tr.edu.gsu.sine.in;

/**
 * Provides an enumeration of supported web services descriptions languages.
 * <p>
 * It does not take place in the package {@literal sine.col},
 * because it also provides some information for parsing.
 * A enhancement of design would be to split it in these two packages.  
 */
public enum Language {

	/**
	 * SAWSDL (<a href="http://www.w3.org/TR/sawsdl">W3C Technical Report</a>)
	 */
	SAWSDL("wsdl", new SAWSDLParser()),

	/**
	 * WSDL 1.1 (<a href="http://www.w3.org/TR/wsdl">W3C Technical Report</a>)
	 */
	WSDL("wsdl", new WSDLParser());

	/**
	 * File name extension known to be used for this language.
	 */
	private final String extension;

	/**
	 * Suitable parser for parsing files written in this language.
	 */
	private final Parser parser;

	/**
	 * Creates a language, of known filename extension and parser.
	 * <p>
	 * This constructor is private, as required for an enumerated class.
	 * 
	 * @param extension
	 *            the name extension of files written in this language.
	 * @param parser
	 *            a suitable parser for this language
	 */
	private Language(String extension, Parser parser) {
		this.extension = extension;
		this.parser = parser;
	}

	/**
	 * Returns the file name extension known to be used for this language.
	 * 
	 * @return the file name extension known to be used for this language
	 */
	public String getExtension() {
		return extension;
	}

	/**
	 * Returns a suitable parser for parsing files written in this language.
	 * 
	 * @return a suitable parser for parsing files written in this language
	 */
	public Parser getParser() {
		return parser;
	}
}
