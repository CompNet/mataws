package tr.edu.gsu.sine.col;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import tr.edu.gsu.sine.Sine;

/**
 * Represents a web service.
 * <p>
 * It is assumed to be unique by its name and location.
 * The relative path to its description file is just an additional information.
 */
public class Service extends AbstractGrain {

	/**
	 * Location of this web service.
	 */
	private String location;
	
	/**
	 * Operations of this web service.
	 */
	private List<Operation> operations;

	/**
	 * Relative path of the file describing this service.
	 */
	private String path;
	
	/**
	 * Creates a web service of specified name.
	 * 
	 * @param name
	 *            the name of the web service
	 */
	public Service(String name) {
		this(name, "");
	}

	/**
	 * Creates a web service of specified name.
	 * 
	 * @param name
	 *            the name of the web service
	 * @param path
	 *            the relative path of the description file
	 */
	public Service(String name, String path) {
		this.name = name;
		this.location = "";
		this.operations = new ArrayList<Operation>();
		this.path = path;
	}

	/**
	 * Creates a service from an XML DOM representation.
	 * 
	 * @param elem
	 *            XML DOM representation of a service
	 */
	public Service(Element elem) {
		this(elem.getAttribute("name"), elem.getAttribute("path"));
		this.location = elem.getAttribute("location");
		NodeList children = elem.getElementsByTagName("operation");
		for (int i = 0; i < children.getLength(); i++) {
			addOperation(new Operation((Element) children.item(i)));
		}
	}
	
	/**
	 * Adds an operation to the web service.
	 * 
	 * @param operation
	 *            the operation to be added
	 */
	public void addOperation(Operation operation) {
		operation.setParent(this);
		operations.add(operation);
	}

	/**
	 * Compares to the specified grain by name, then by location.
	 * @see tr.edu.gsu.sine.col.AbstractGrain#compareTo(tr.edu.gsu.sine.col.Grain)
	 */
	@Override
	public int compareTo(Grain grain) {
		int r = name.compareTo(grain.getName());
		return r == 0 ? location.compareTo(((Service) grain).location) : r;
	}

	/**
	 * Returns true if object is equal to this, without considering contextual
	 * attribute, i.e. location.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 * @.NOTE Do not confuse with AbstractGrain#compareTo(Grain).
	 */
	@Override
	public boolean equals(Object obj) {
	    // check for self-comparison
	    if (this == obj) return true;
	    
	    // check for type
	    if ( !(obj instanceof Service) ) return false;
	    
	    // cast to native object is now safe
	    Service s = (Service) obj;
	    
	    // now a proper field-by-field evaluation can be made
		return this.name.equals(s.name)
				&& this.operations.containsAll(s.operations)
				&& s.operations.containsAll(this.operations);
	}

	/**
	 * Returns the location of this web service.
	 * 
	 * @return the location of this web service
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Returns the operations of this web service.
	 * 
	 * @return the operations of this web service
	 */
	public List<Operation> getOperations() {
		return operations;
	}

	/**
	 * Returns the input or output parameters of this operation.
	 * 
	 * @param way
	 *            the input or output way of parameters
	 * @return the input or output parameters of this operation
	 */
	public List<Parameter> getParameters(Way way) {
		List<Parameter> parameters = new ArrayList<Parameter>();
		for (Operation o: operations) {
			parameters.addAll(o.getParameters(way));
		}
		return parameters;
	}
	
	/**
	 * Returns the path of the file describing this service.
	 * 
	 * @return the path of the file describing this service
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Returns the unique name of this grain.
	 * <p>
	 * Since a web service has no parent, uniqueness is acquired by
	 * concatenation of location to name.
	 * <p>
	 * The relative path to the description file is added for information only.
	 * 
	 * @return the unique name of this grain
	 * @see tr.edu.gsu.sine.col.AbstractGrain#getUniqueName()
	 */
	@Override
	public String getUniqueName() {
		return escape(name) + NAME_SEP + escape(location) + NAME_SEP
				+ escape(path);
	}

	/**
	 * Sets the location of this web service.
	 * 
	 * @param location
	 *            the location of this web service
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String s;
		s = "Service '" + name + "' at '" + location + "' from file '" + path
				+ "' with " + operations.size() + " operation(s):";
		for (Operation o : operations) {
			s += Sine.NEW_LINE + o.toString();
		}
		return s;
	}

	/**
	 * Returns an XML DOM representation of this service.
	 * 
	 * @param doc
	 *            the top-level XML document, needed to create an element.
	 * @return an XML DOM element representing this service
	 */
	public Element toXML(Document doc) {
		Element elem = doc.createElement("service");
		elem.setAttribute("name", name);
		if (location != null && !location.isEmpty()) {
			elem.setAttribute("location", location);
		}
		if (path != null && !path.isEmpty()) {
			elem.setAttribute("path", path);
		}
		for (Operation o : operations) {
			elem.appendChild(o.toXML(doc));
		}
		return elem;
	}
}
