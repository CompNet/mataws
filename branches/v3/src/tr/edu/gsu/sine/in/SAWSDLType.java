package tr.edu.gsu.sine.in;

/**
 * Represents a type in SAWSDL, with both syntactic and semantic parts.
 * <p>
 * This is only used as an intermediate representation by SAWSDL parser.
 */
public class SAWSDLType {
	
	/**
	 * Base type, also known as syntactic part of the type.
	 */
	private final String baseType;
	
	/**
	 * Ontological concept URI, also known as semantic part of the type.
	 */
	private final String conceptURI;
	
	/**
	 * Creates a type with undefined syntactic and semantic parts.
	 */
	public SAWSDLType() {
		this.baseType = "";
		this.conceptURI = "";
	}
	
	/**
	 * Creates a type with the specified syntactic and semantic types.
	 * 
	 * @param baseType
	 *            the syntactic part of the type
	 * @param conceptURI
	 *            the semantic part of the type
	 */
	public SAWSDLType(String baseType, String conceptURI) {
		this.baseType = baseType;
		this.conceptURI = conceptURI;
	}

	/**
	 * Returns the syntactic part of the type.
	 *  
	 * @return the syntactic part of the type
	 */
	public String getBaseType() {
		return baseType;
	}

	/**
	 * Returns the URI of the ontological concept of this type.
	 * 
	 * @return the URI of the ontological concept of this type
	 */
	public String getConceptURI() {
		return conceptURI;
	}

	@Override
	public boolean equals(Object obj) {
		// check for self-comparison
		if (this == obj)
			return true;

		// check for type
		if (!(obj instanceof SAWSDLType))
			return false;

		// cast to native object is now safe
		SAWSDLType t = (SAWSDLType) obj;

		return baseType.equals(t.baseType) && conceptURI.equals(t.conceptURI);
	}
}
