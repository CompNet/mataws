package tr.edu.gsu.sine.col;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Represents a parameter of an operation.
 */
public class Parameter extends AbstractGrain {

	/**
	 * URI of the ontological concept associated to this parameter.
	 * <p>
	 * This is semantic information.
	 */
	private String conceptURI;
	
	/**
	 * Sub-parameters of this parameter, if this parameter is of complex type.
	 */
	private List<Parameter> subParameters;
	
	/**
	 * Name of its type as it is referred to in description file.
	 * <p>
	 * This is syntactic information.
	 */
	private String typeName; 
	
	/**
	 * The input/output way of this parameter in parent operation.
	 */
	private Way way;
	
	/**
	 * Creates a parameter with a name and undefined ontological concept.
	 * 
	 * @param name
	 *            the name of the parameter to be created
	 */
	public Parameter(String name) {
		this(name, null);
	}
	
	/**
	 * Creates a parameter with a name and an ontological concept.
	 * 
	 * @param name
	 *            the name of the parameter to be created
	 * @param conceptURI
	 *            the ontological concept of this parameter
	 */
	public Parameter(String name, String conceptURI) {
		this.name = name;
		this.conceptURI = conceptURI;
		subParameters = null;
		typeName = "";
		way = null; 
	}

	/**
	 * Creates a parameter from an XML DOM representation.
	 * 
	 * @param elem
	 *            XML DOM representation of a parameter
	 */
	public Parameter(Element elem) {
		this(elem.getAttribute("name"), elem.getAttribute("concept"));
		typeName = elem.getAttribute("type");
		NodeList children = elem.getElementsByTagName("parameter");
		for (int i = 0; i < children.getLength(); i++) {
			addSubParameter(new Parameter((Element) children.item(i)));
		}
	}
	
	/**
	 * Adds a sub-parameter to this parameter.
	 * <p>
	 * This is used for parameters of complex type.
	 * 
	 * @param subParam
	 *            the sub-parameter to be added
	 */
	public void addSubParameter(Parameter subParam) {
		if (subParameters == null) {
			subParameters = new ArrayList<Parameter>();
		}
		subParam.setParent(this);
		if (way != null) {
			subParam.setWay(way);
		}
		subParameters.add(subParam);
	}

	/**
	 * Returns true if object is equal to this, without considering contextual
	 * attributes, i.e. parent and way.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 * @.NOTE Do not confuse with AbstractGrain#compareTo(Grain).
	 */
	@Override
	public boolean equals(Object obj) {
		// check for self-comparison
		if (this == obj)
			return true;
	
		// check for type
		if (!(obj instanceof Parameter))
			return false;
	
		// cast to native object is now safe
		Parameter p = (Parameter) obj;
	
		// now a proper field-by-field evaluation can be made
		return this.name.equals(p.name)
				&& (this.conceptURI == p.conceptURI
						|| this.conceptURI != null
						&& this.conceptURI.equals(p.conceptURI))
				&& (this.subParameters == p.subParameters
						|| this.subParameters != null
						&& this.subParameters.containsAll(p.subParameters)
						&& p.subParameters != null
						&& p.subParameters.containsAll(this.subParameters));
	}

	/**
	 * Returns the URI of the ontological concept of this parameter.
	 * 
	 * @return the URI of the ontological concept of this parameter
	 */
	public String getConceptURI() {
		return conceptURI;
	}

	/**
	 * Returns the direct sub-parameters of this parameter.
	 * <p>
	 * This is used for parameters of complex type.
	 * 
	 * @return the direct sub-parameters of this parameter
	 */
	public List<Parameter> getSubParameters() {
		return subParameters;
	}
	
	/**
	 * Returns the enclosing parameter of this parameter.
	 * 
	 * @return the enclosing parameter ot this parameter
	 */
	public Parameter getSupParameter() {
		return (parent instanceof Parameter) ? (Parameter) parent : null;
	}

	/**
	 * Returns the name of the type as it is referred to in description file.
	 * 
	 * @return the name of the type as it is referred to in description file
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * Returns the input or output way of this parameter.
	 * 
	 * @return the input or output way of this parameter
	 */
	public Way getWay() {
		return way;
	}

	/**
	 * Sets the URI of the ontological concept of this parameter
	 * 
	 * @param conceptURI
	 *            the ontological concept URI
	 */
	public void setConceptURI(String conceptURI) {
		this.conceptURI = conceptURI;
	}

	/**
	 * Sets the type's name of this parameter.
	 * 
	 * @param typeName
	 *            the type's name as referred to in description file.
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * Sets way of this parameter in its parent operation.
	 * 
	 * @param way
	 *            input or output way
	 */
	public void setWay(Way way) {
		this.way = way;
		if (subParameters != null) {
			for (Parameter parameter : subParameters) {
				parameter.setWay(way);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String s;
		s = "Parameter '" + name + "' (" + way + ") of concept URI = '"
				+ conceptURI + "'";
		if (subParameters != null) {
			s += " with " + subParameters.size() + " sub-parameters";
		}
		return s;
	}
	
	/**
	 * Returns an XML DOM representation of this parameter.
	 * 
	 * @param doc
	 *            the top-level XML document, needed to create an element.
	 * @return an XML DOM element representing this parameter
	 */
	public Element toXML(Document doc) {
		Element elem = doc.createElement("parameter");
		elem.setAttribute("name", name);
		if (conceptURI != null && !conceptURI.isEmpty()) {
			elem.setAttribute("concept", conceptURI);
		}
		if (typeName != null && !typeName.isEmpty()) {
			elem.setAttribute("type", typeName);
		}
		if (subParameters != null) {
			for (Parameter sub : subParameters) {
				elem.appendChild(sub.toXML(doc));
			}
		}
		return elem;
	}
}
