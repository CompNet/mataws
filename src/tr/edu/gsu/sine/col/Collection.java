package tr.edu.gsu.sine.col;

import java.util.SortedSet;
import java.util.TreeSet;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import tr.edu.gsu.sine.in.Language;

/**
 * Represents a collection of web services.
 */
public class Collection {

	/**
	 * Whether this collection is semantically annotated or not.
	 */
	private boolean hasSemantics;

	/**
	 * Language describing web services of this collection.
	 */
	private Language language;
	
	/**
	 * Name of the collection.
	 */
	private String name;

	/**
	 * Web services of this collection, sorted by location and by name.
	 */
	private SortedSet<Service> services;

	/**
	 * Creates an empty collection of web services.
	 * 
	 * @param name
	 *            the name of the collection
	 * @param language
	 *            the language used to describes web services.
	 */
	public Collection(String name, Language language) {
		this.name = name;
		this.language = language;
		this.services = new TreeSet<Service>();
		this.hasSemantics = false;
	}

	/**
	 * Creates a collection from an XML DOM representation.
	 * 
	 * @param elem
	 *            XML DOM representation of a collection
	 */
	public Collection(Element elem) {
		this(elem.getAttribute("name"), Language.valueOf(elem
				.getAttribute("lang")));
		NodeList children = elem.getElementsByTagName("service");
		for (int i = 0; i < children.getLength(); i++) {
			try {
				addService(new Service((Element) children.item(i)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Adds a service to this collection.
	 * <p>
	 * If a service of same unique name already exists in the collection, then
	 * the service is not added and an exception is thrown by this method.
	 * 
	 * @param service
	 *            the service to be added to this collection
	 * @throws Exception
	 *             if the service already exists in this collection
	 */
	public void addService(Service service) throws Exception {
		for (Service ps: services) {
			if (ps.getUniqueName().equals(service.getUniqueName())) {
				throw new Exception("Service '" + service.getUniqueName()
						+ "' already exists in this collection.");
			}
		}
		if (!hasSemantics) {
			for (Operation o : service.getOperations()) {
				for (Parameter p : o.getParameters()) {
					if (p.getConceptURI() != null
							&& !p.getConceptURI().equals("")) {
						hasSemantics = true;
						break;
					}
				}
				if (hasSemantics) {
					break;
				}
			}
		}
		services.add(service);
	}

	/**
	 * Returns the language describing these web services.
	 * 
	 * @return the language describing these web services
	 */
	public Language getLanguage() {
		return language;
	}

	/**
	 * Returns the name of this collection.
	 * 
	 * @return the name of this collection
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the operations of this collection, sorted by their unique name.
	 * 
	 * @return the operations of this collection, sorted by their unique name
	 */
	public SortedSet<Operation> getOperations() {
		SortedSet<Operation> operations = new TreeSet<Operation>();
		for (Service s: services) {
			operations.addAll(s.getOperations());
		}
		return operations;
	}
	
	/**
	 * Returns the parameters of this collection, sorted by their unique name.
	 * 
	 * @return the parameters of this collection, sorted by their unique name
	 */
	public SortedSet<Parameter> getParameters() {
		SortedSet<Parameter> parameters = new TreeSet<Parameter>();
		for (Service s: services) {
			for (Operation o: s.getOperations()) {
				parameters.addAll(o.getParameters());
			}
		}
		return parameters;
	}
	
	/**
	 * Returns the web services of this collection, sorted by their unique name.
	 * 
	 * @return the web services of this collection, sorted by their unique name
	 */
	public SortedSet<Service> getServices() {
		return services;
	}

	/**
	 * Returns true if this collection is semantically annotated.
	 * 
	 * @return true if this collection is semantically annotated
	 */
	public boolean hasSemantics() {
		return hasSemantics;
	}

	/**
	 * Sets the language describing the services of this collection.
	 * 
	 * @param language
	 *            the language describing the services of this collection
	 */
	public void setLanguage(Language language) {
		this.language = language;
	}

	/**
	 * Sets the name of this collection.
	 * 
	 * @param name
	 *            the name of this collection
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns an XML DOM representation of this collection.
	 * 
	 * @param doc
	 *            the top-level XML document, needed to create an element.
	 * @return an XML DOM element representing this collection
	 */
	public Element toXML(Document doc) {
		Element elem = doc.createElement("collection");
		elem.setAttribute("name", name);
		elem.setAttribute("lang", language.toString());
		for (Service s : services) {
			elem.appendChild(s.toXML(doc));
		}
		return elem;
	}
}
