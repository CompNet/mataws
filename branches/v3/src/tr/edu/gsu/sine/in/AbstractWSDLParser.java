package tr.edu.gsu.sine.in;

import javax.xml.transform.TransformerException;

import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Provides facilities for parsing WSDL and derived languages, by the way of
 * XPath commands.
 */
public abstract class AbstractWSDLParser extends AbstractParser {

	/**
	 * True if the parser must discard empty parameters of complex type.
	 * 
	 * TODO: add this flag to command-line options.
	 */
	protected static final boolean discardEmptyParameters = false;
	
	/**
	 * Parses address location of the service described in the document.
	 * 
	 * @param d
	 *            the document describing the service
	 * @return the location URI of the service
	 * @throws TransformerException
	 */
	protected String parseServiceLocation(Document d)
			throws TransformerException {
		String xpath = buildXPath("service", "port", "address");
		NodeList addressNodes = XPathAPI.selectNodeList(d, xpath);

		if (addressNodes.getLength() == 0) {
			throw new TransformerException("XML path not found: "
					+ xpathToString(xpath));
		}
		
		// get the address element
		Element addressElem = (Element) addressNodes.item(0);
		
		// get the location attribute
		String location = addressElem.getAttribute("location");
		return location;
	}

	/**
	 * Parses the portType name of the service described in the document.
	 * 
	 * @param d
	 *            the document describing the service
	 * @return the portType name of the service
	 * @throws TransformerException
	 */
	protected String parsePortTypeName(Document d) throws TransformerException {
		String xpath = buildXPath("portType");
		NodeList portTypeNodes = XPathAPI.selectNodeList(d, xpath);
		
		if (portTypeNodes.getLength() == 0) {
			throw new TransformerException("XML path not found: "
					+ xpathToString(xpath));
		}
		
		// get the portType element
		Element portTypeElem = (Element) portTypeNodes.item(0);
		
		// get the portType name
		String name = portTypeElem.getAttribute("name");
		name = stripNameSpace(name);
		return name;
	}

	/**
	 * Parses the name attribute of the service described in the document.
	 * 
	 * @param d
	 *            the document describing the service
	 * @return the name of the service
	 * @throws TransformerException
	 */
	protected String parseServiceName(Document d) throws TransformerException {
		String xpath = buildXPath("service");
		NodeList serviceNodes = XPathAPI.selectNodeList(d, xpath);
		
		if (serviceNodes.getLength() == 0) {
			throw new TransformerException("XML path not found: "
					+ xpathToString(xpath));
		}
		
		// get the service element
		Element serviceElem = (Element) serviceNodes.item(0);
		
		// get the service name
		String name = serviceElem.getAttribute("name");
		name = stripNameSpace(name);
		return name;
	}
}
