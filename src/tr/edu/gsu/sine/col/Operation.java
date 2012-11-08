package tr.edu.gsu.sine.col;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import tr.edu.gsu.sine.Sine;

/**
 * Represents an operation of a web service.
 */
public class Operation extends AbstractGrain {

	/**
	 * Input and output lists of parameters.
	 */
	private Map<Way, List<Parameter>> parameters;

	/**
	 * Creates an operation with its name.
	 * 
	 * @param name
	 *            the name of this operation
	 */
	public Operation(String name) {
		this.name = name;
		parameters = new HashMap<Way, List<Parameter>>(Way.values().length);
		for (Way way : Way.values()) {
			parameters.put(way, new ArrayList<Parameter>());
		}
	}
	
	/**
	 * Creates an operation from an XML DOM representation.
	 * 
	 * @param elem
	 *            XML DOM representation of an operation
	 */
	public Operation(Element elem) {
		this(elem.getAttribute("name"));
		for (Way w : Way.values()) {
			NodeList wayChildren = elem.getElementsByTagName(w.toXMLTagName());
			Element wayElement = (Element) wayChildren.item(0);
			NodeList children = wayElement.getElementsByTagName("parameter");
			for (int i = 0; i < children.getLength(); i++) {
				addParameter(new Parameter((Element) children.item(i)), w);
			}
		}
	}

	/**
	 * Adds an input or output parameter.
	 * 
	 * @param p
	 *            the parameter to be added
	 * @param way
	 *            the input or output way
	 */
	public void addParameter(Parameter p, Way way) {
		p.setParent(this);
		p.setWay(way);
		parameters.get(way).add(p);
	}

	/**
	 * Returns true if object is equal to this, without considering contextual
	 * attribute, i.e. parent.
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
		if (!(obj instanceof Operation))
			return false;

		// cast to native object is now safe
		Operation op = (Operation) obj;

		// now a proper field-by-field evaluation can be made
		return this.name.equals(op.name)
				&& this.parameters.get(Way.IN).containsAll(
						op.parameters.get(Way.IN))
				&& op.parameters.get(Way.IN).containsAll(
						this.parameters.get(Way.IN))
				&& this.parameters.get(Way.OUT).containsAll(
						op.parameters.get(Way.OUT))
				&& op.parameters.get(Way.OUT).containsAll(
						this.parameters.get(Way.OUT));
	}

	/**
	 * Returns the list of all parameters.
	 * 
	 * @return the list of all parameters
	 */
	public List<Parameter> getParameters() {
		List<Parameter> lp = new ArrayList<Parameter>();
		lp.addAll(parameters.get(Way.IN));
		lp.addAll(parameters.get(Way.OUT));
		return lp;
	}

	/**
	 * Returns the input or output list of parameters.
	 * 
	 * @param way
	 *            the input or output way
	 * @return the list of parameters of that way
	 */
	public List<Parameter> getParameters(Way way) {
		return parameters.get(way);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String s;
		s = "Operation: " + name + " with " + getParameters().size()
				+ " parameter(s):";
		for (Way w : parameters.keySet()) {
			for (Parameter p : parameters.get(w)) {
				s += Sine.NEW_LINE + p.toString();
			}
		}
		return s;
	}

	/**
	 * Returns an XML DOM representation of this operation.
	 * 
	 * @param doc
	 *            the top-level XML document, needed to create an element.
	 * @return an XML DOM element representing this operation
	 */
	public Element toXML(Document doc) {
		Element elem = doc.createElement("operation");
		elem.setAttribute("name", name);
		for (Way w : Way.values()) {
			if (!parameters.get(w).isEmpty()) {
				Element wayElem = doc.createElement(w.toXMLTagName());
				for (Parameter p : parameters.get(w)) {
					wayElem.appendChild(p.toXML(doc));
				}
				elem.appendChild(wayElem);
			}
		}
		return elem;
	}
}
