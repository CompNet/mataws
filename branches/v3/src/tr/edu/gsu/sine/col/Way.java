package tr.edu.gsu.sine.col;

/**
 * Way of a parameter in the eyes of its parent operation.
 */
public enum Way {

	/**
	 * Input.
	 */
	IN,

	/**
	 * Output.
	 */
	OUT;
	
	/**
	 * Returns the XML tag name associated to this way.
	 * 
	 * @return the XML tag name representing this way
	 */
	public String toXMLTagName() {
		return name().toLowerCase() + "put";
	}
};
