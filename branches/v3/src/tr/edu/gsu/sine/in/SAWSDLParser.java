package tr.edu.gsu.sine.in;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.transform.TransformerException;

import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import tr.edu.gsu.sine.col.Operation;
import tr.edu.gsu.sine.col.Parameter;
import tr.edu.gsu.sine.col.Service;
import tr.edu.gsu.sine.col.Way;

/**
 * Provides support for parsing SAWSDL files.
 */
public class SAWSDLParser extends AbstractWSDLParser {
	
	/**
	 * The SAWSDL document to be parsed.
	 */
	private Document document;
	
	/**
	 * The service to be built from the SAWSDL document.
	 */
	private Service service;

	/**
	 * Simple type name to simple type
	 */
	private Map<String, SAWSDLType> simpleTypes;

	/**
	 * Complex type name to complex type
	 */
	private Map<String, SAWSDLType> complexTypes;
	
	/**
	 * Complex type name to map of parameters names to types names.
	 */
	private Map<String, Map<String, String>> ctElementsTypeNames;
	
	/**
	 * Message name to map of parameters names to types names.
	 */
	private Map<String, Map<String, String>> msgPartsTypeNames;
	
	/**
	 * Message name to list of parameters.
	 */
	private Map<String, List<Parameter>> msgParameters;

	/**
	 * Operation name to input and output arrays of messages names.
	 */
	private Map<String, Map<Way, Set<String>>> opMsgNames;
	
	/**
	 * Initialize internal maps.
	 */
	private void initTables() {
		simpleTypes = new HashMap<String, SAWSDLType>();
		complexTypes = new HashMap<String, SAWSDLType>();
		ctElementsTypeNames = new HashMap<String, Map<String, String>>();
		msgPartsTypeNames = new HashMap<String, Map<String,String>>();
		msgParameters = new HashMap<String, List<Parameter>>();
		opMsgNames = new HashMap<String, Map<Way, Set<String>>>();
	}

	/**
	 * Parses a SAWSDL document and returns the described service.
	 * <p>
	 * Here is an overview of the algorithm:
	 * <ul>
	 * <li>retrieve service name and location</li>
	 * <li>retrieve simple and complex types with ontological concepts</li>
	 * <li>for each message, retrieve its name and its parameters names</li>
	 * <li>for each operation, retrieve its name and its messages names</li>
	 * <li>for each message, build the parameters lists</li>
	 * <li>build operations using parameters lists</li>
	 * <li>return the service built in this way.</li>
	 * </ul>
	 * 
	 * @param document
	 *            the SAWSDL document to be parsed
	 * @return the service described by this document
	 */
	@Override
	public Service parse(Document document) {
		// get the root element
		// Element docEle = doc.getDocumentElement();
		
		initTables();
		this.document = document;
		this.service = null;
		
		String name;
		try {
			name = parseServiceName(document);
		} catch (TransformerException e1) {
			logException(e1, "unable to retrieve service name (try portType)");
			// use portType name instead
			try {
				name = parsePortTypeName(document);
			} catch (TransformerException e2) {
				logException(e2, "unable to retrieve portType name (filename)");
				// use relative file name instead
				name = new File(URI.create(filename)).getName();
			}
		}
		service = new Service(name, relativePath);
		
		try {
			String location = parseServiceLocation(document);
			service.setLocation(location);
		} catch (TransformerException e) {
			logException(e, "unable to retrieve service location");
			service.setLocation("");
		}

		try {
			parseSimpleTypes();
		} catch (TransformerException e) {
			logException(e, "unable to retrieve simple types");
		}
		
		try {
			parseComplexTypes();
		} catch (TransformerException e) {
			logException(e, "unable to retrieve complex types");
		}
		
		try {
			parseMessages();
		} catch (TransformerException e) {
			logException(e, "unable to retrieve messages");
		}

		buildParameters();
		
		try {
			parsePortTypes();
		} catch (TransformerException e) {
			e.printStackTrace();
			logException(e, "unable to retrieve parameters");
		}

		buildOperations();

		return service;
	}

	/**
	 * Parses each of simpleType elements.
	 * 
	 * @throws TransformerException
	 */
	private void parseSimpleTypes() throws TransformerException {
		String xpath = buildXPath("types", "schema", "simpleType");
		NodeList simpleTypeNodes = XPathAPI.selectNodeList(document, xpath);
		
		// get the simpleType elements
		for (int i = 0; i < simpleTypeNodes.getLength(); i++) {
			Element simpleTypeElem = (Element) simpleTypeNodes.item(i);
			parseSimpleType(simpleTypeElem);
		}
	}
	
	/**
	 * Parses a simpleType element and stores its name in {@link #simpleTypes}.
	 * 
	 * @param simpleTypeElem
	 *            the simpleType element to be parsed.
	 */
	private void parseSimpleType(Element simpleTypeElem) {
		String namespace = simpleTypeElem.getPrefix();
		namespace = (namespace == null) ? "" : (namespace + ":");

		// get the name attribute
		String name = simpleTypeElem.getAttribute("name");
		name = stripNameSpace(name);
		
		// get the base (type) attribute
		String baseType = "";
		NodeList restrictionNodes = simpleTypeElem
				.getElementsByTagName(namespace + "restriction");
		Element restrictionElem = (Element) restrictionNodes.item(0);
		if (restrictionElem != null) {
			baseType = restrictionElem.getAttribute("base");
			baseType = stripNameSpace(baseType);
		}		

		// get the modelReference attribute
		String modelRef = simpleTypeElem.getAttribute("sawsdl:modelReference");
		if (modelRef.isEmpty() && restrictionElem != null) {
			modelRef = restrictionElem.getAttribute("sawsdl:modelReference");
		}
		
		// build complete type
		SAWSDLType sawsdlType = new SAWSDLType(baseType, modelRef);
		
		// store it for further usage
		simpleTypes.put(name, sawsdlType);
	}
	
	/**
	 * Parses each of complexType elements.
	 * 
	 * @throws TransformerException
	 */
	private void parseComplexTypes() throws TransformerException {
		String xpath = buildXPath("types", "schema", "complexType");
		NodeList complexTypeNodes = XPathAPI.selectNodeList(document, xpath);

		// get complexType elements
		for (int k = 0; k < complexTypeNodes.getLength(); k++) {
			Element complexTypeElem = (Element) complexTypeNodes.item(k);
			parseComplexType(complexTypeElem);
		}
	}
	
	/**
	 * Parses a complexType element and stores it in {@link #complexTypes}.
	 * 
	 * @param complexTypeElem
	 *            the complexType element to be parsed
	 * @throws TransformerException
	 */
	private void parseComplexType(Element complexTypeElem)
			throws TransformerException {
		// get name attribute
		String name = complexTypeElem.getAttribute("name");
		name = stripNameSpace(name);
		
		// get model reference attribute
		String modelRef = complexTypeElem.getAttribute("sawsdl:modelReference");
		
		// build type
		SAWSDLType sawsdlType = new SAWSDLType("", modelRef);
		
		// store type for further usage
		complexTypes.put(name, sawsdlType);
		
		parseElements(name, complexTypeElem);
	}
	
	/**
	 * Parses each element of a complexType and stores it in
	 * {@link #ctElementsTypeNames}.
	 * 
	 * @param complexTypeName
	 *            the name of the parent complexType
	 * @param complexTypeElem
	 *            the XML element of the complexType
	 * @throws TransformerException
	 */
	private void parseElements(String complexTypeName, Element complexTypeElem)
			throws TransformerException {
		String xpath = "*[local-name()='sequence']/*[local-name()='element']";
		NodeList elementNodes = XPathAPI.selectNodeList(complexTypeElem, xpath);
		
		// get the 'element' elements
		Map<String, String> elements = new HashMap<String, String>();
		for (int m = 0; m < elementNodes.getLength(); m++) {
			Element elementElem = (Element) elementNodes.item(m);
			String elementName = elementElem.getAttribute("name");
			String elementType = elementElem.getAttribute("type");
			elementName = stripNameSpace(elementName);
			elementType = stripNameSpace(elementType);
			elements.put(elementName, elementType);
			
			// store it for further usage
			ctElementsTypeNames.put(complexTypeName, elements);
		}
	}

	/**
	 * Parses each of messages and store it in {@link #msgPartsTypeNames}.
	 * 
	 * @throws TransformerException
	 */
	private void parseMessages() throws TransformerException {
		String xpath = buildXPath("message");
		NodeList msgNodes = XPathAPI.selectNodeList(document, xpath);

		if (msgNodes.getLength() > 0) {
			for (int i = 0; i < msgNodes.getLength(); i++) {
				// get the message element
				Element messageElem = (Element) msgNodes.item(i);
				String namespace = messageElem.getPrefix();
				namespace = (namespace == null) ? "" : (namespace + ":");

				// get message name
				String messageName = messageElem.getAttribute("name");
				messageName = stripNameSpace(messageName);
				
				// get part names
				NodeList partNodes = messageElem.getElementsByTagName(namespace
						+ "part");

				Map<String, String> partsTypes = new HashMap<String, String>();
				for (int j = 0; j < partNodes.getLength(); j++) {
					Element partElem = (Element) partNodes.item(j);
					String partName = partElem.getAttribute("name");
					String partType = partElem.getAttribute("type");
					partName = stripNameSpace(partName);
					partType = stripNameSpace(partType);
					partsTypes.put(partName, partType);
				}
				msgPartsTypeNames.put(messageName, partsTypes);
			}
		}
	}

	/**
	 * Builds parameters lists corresponding to each message. Parameters lists
	 * are stored in {@link #msgParameters}.
	 */
	private void buildParameters() {
		for (String msgName : msgPartsTypeNames.keySet()) {
			List<Parameter> parameters = buildParameters(msgName);
			msgParameters.put(msgName, parameters);
		}
	}
	
	/**
	 * Builds the parameters list corresponding to a message.
	 * 
	 * @param msgName
	 *            the name of the message
	 * @return the parameters list corresponding to msgName
	 */
	private List<Parameter> buildParameters(String msgName) {
		List<Parameter> parameters = new ArrayList<Parameter>();
		Map<String, String> partsTypes = msgPartsTypeNames.get(msgName);
		for (String part : partsTypes.keySet()) {
			Parameter parameter = buildParameter(part, partsTypes.get(part),
					new ArrayList<String>());
			if (parameter != null) {
				parameters.add(parameter);
			}
		}
		return parameters;
	}

	/**
	 * Builds a parameter of specified name and type.
	 * 
	 * @param name
	 *            the name of the parameter
	 * @param typeName
	 *            the type of the parameter
	 * @param hierarchy
	 *            list of types preceding typeName in parameters hierarchy 
	 * @return the parameter of specified name and type
	 */
	private Parameter buildParameter(String name, String typeName,
			List<String> hierarchy) {
		// Discard recursive occurrences of a parameter.
		if (hierarchy.contains(typeName)) {
			logWarning("Discard recursive occurrence of '" + typeName + "'.");
			return null;
		}
		
		// Retrieve the type (name and concept)
		SAWSDLType sawsdlType;
		if (simpleTypes.containsKey(typeName)) {
			sawsdlType = simpleTypes.get(typeName);
		} else if (complexTypes.containsKey(typeName)) {
			sawsdlType = complexTypes.get(typeName);
		} else {
			sawsdlType = new SAWSDLType();
		}

		// Instantiate the parameter
		Parameter parameter = new Parameter(name, sawsdlType.getConceptURI());
		parameter.setTypeName(sawsdlType.getBaseType());

		// If parameter is of complex type, it should have sub-parameters.
		if (complexTypes.containsKey(typeName)) {
			// Look for elements of this complex type
			Map<String, String> elementsTypes = ctElementsTypeNames
					.get(typeName);
			if (elementsTypes != null) {
				// Add this parameter to the hierarchy of types.
				hierarchy.add(typeName);
				// Build each of sub-parameters.
				for (String elementName : elementsTypes.keySet()) {
					String elementType = elementsTypes.get(elementName);

					// Recursive call does not not loop infinitely,
					// because recursive occurrences are discarded above.
					Parameter sub = buildParameter(elementName, elementType,
							hierarchy);
					if (sub != null) {
						parameter.addSubParameter(sub);
					}
				}
				// Restore hierarchy list.
				hierarchy.remove(typeName);
			}

			// Discard parameters of empty complex type.
			if (AbstractWSDLParser.discardEmptyParameters) {
				if (parameter.getSubParameters() == null
						|| parameter.getSubParameters().isEmpty()) {
					logWarning("Discard empty complex type '" + typeName + "'.");
					return null;
				}
			}
		}
		return parameter;
	}

	/**
	 * Parses each of portType elements, searching for operation children.
	 * 
	 * @throws TransformerException
	 */
	private void parsePortTypes() throws TransformerException {
		String xpath = buildXPath("portType");
		NodeList portTypeNodes = XPathAPI.selectNodeList(document, xpath);

		for (int i = 0; i < portTypeNodes.getLength(); i++) {
			// get the portType element
			Element portTypeElem = (Element) portTypeNodes.item(i);
			String namespace = portTypeElem.getPrefix();
			namespace = (namespace == null) ? "" : (namespace + ":");
			
			// get operation names
			NodeList operationNodes = portTypeElem
					.getElementsByTagName(namespace + "operation");
			
			if (operationNodes != null) {
				for (int j = 0; j < operationNodes.getLength(); j++) {
					Element operationElem = (Element) operationNodes.item(j);
					parseOperation(operationElem, namespace);
				}
			}
		}
	}
	
	/**
	 * Parses an operation element and stores it in {@link #opMsgNames}.
	 * 
	 * @param operationElem
	 *            the operation element to be parsed
	 * @param namespace
	 *            the current name-space
	 */
	private void parseOperation(Element operationElem, String namespace) {
		// get operation name
		String operationName = operationElem.getAttribute("name");
		operationName = stripNameSpace(operationName);

		Map<Way, Set<String>> msgMap = new HashMap<Way, Set<String>>();
		
		for (Way way : Way.values()) {
			Set<String> msgNames = new HashSet<String>();
			
			String wsdlWay = (way == Way.IN) ? "input" : "output";
			NodeList msgNodes = operationElem.getElementsByTagName(namespace
					+ wsdlWay);
			
			if (msgNodes != null) {
				for (int k = 0; k < msgNodes.getLength(); k++) {
					Element msgElem = (Element) msgNodes.item(k);
					String msgName = msgElem.getAttribute("message");
					msgName = stripNameSpace(msgName);
					msgNames.add(msgName);
				}
			}
			
			msgMap.put(way, msgNames);
		}

		opMsgNames.put(operationName, msgMap);
	}

	/**
	 * Builds each operation, using parameters lists of its messages. Operations
	 * are stored in the service being built.
	 */
	private void buildOperations() {
		for (String opName : opMsgNames.keySet()) {
			Operation operation = new Operation(opName);
			Map<Way, Set<String>> inOutMsgNames = opMsgNames.get(opName);
			for (Way way : Way.values()) {
				for (String msgName : inOutMsgNames.get(way)) {
					for (Parameter parameter : msgParameters.get(msgName)) {
						operation.addParameter(parameter, way);
					}
				}
			}
			// Add finalized operation to service
			service.addOperation(operation);
		}
	}
}
