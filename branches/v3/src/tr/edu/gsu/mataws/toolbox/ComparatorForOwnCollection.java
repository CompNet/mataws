package tr.edu.gsu.mataws.toolbox;

/*
 * Mataws - Multimodal Automatic Tool for the Annotation of Web Services
 * Copyright 2010 Cihan Aksoy and Koray Mançuhan
 * Copyright 2011 Cihan Aksoy
 * Copyright 2012 Cihan Aksoy and Vincent Labatut
 * 
 * This file is part of Mataws - Multimodal Automatic Tool for the Annotation of Web Services.
 * 
 * Mataws - Multimodal Automatic Tool for the Annotation of Web Services is 
 * free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 * 
 * Mataws - Multimodal Automatic Tool for the Annotation of Web Services 
 * is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Mataws - Multimodal Automatic Tool for the Annotation of Web Services.
 * If not, see <http://www.gnu.org/licenses/>.
 * 
 */

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import tr.edu.gsu.sine.col.Operation;
import tr.edu.gsu.sine.col.Parameter;
import tr.edu.gsu.sine.col.Service;
import tr.edu.gsu.sine.col.Way;
import tr.edu.gsu.sine.in.WSDLParser;

/**
 * This class is for comparing the description of a collection if the collection
 * has the same description(s) or not.
 * 
 * @author J
 * 
 */
public class ComparatorForOwnCollection {

	private static DocumentBuilderFactory docFactory;
	private static DocumentBuilder docBuilder;

	private static HashMap<Service, String> serviceList;
	private static HashMap<Service, String> cloneServiceList;

	private static WSDLParser wsdlParser;

	public static void main(String[] args) throws IOException,
			ParserConfigurationException, SAXException {

		docFactory = DocumentBuilderFactory.newInstance();
		docBuilder = docFactory.newDocumentBuilder();

		serviceList = new HashMap<Service, String>();

		wsdlParser = new WSDLParser();

		findAllDescPaths(args[0]);

		// now iterate over services
		Iterator it = serviceList.entrySet().iterator();
		while (it.hasNext()) {

			Map.Entry pairs = (Map.Entry) it.next();
			Service service = (Service) pairs.getKey();

			cloneServiceList = (HashMap<Service, String>) serviceList.clone();
			cloneServiceList.remove(service);

			List<Operation> ops = service.getOperations();
			List<Parameter> inParameters = service.getParameters(Way.IN);
			List<Parameter> outParameters = service.getParameters(Way.OUT);

			Iterator itClone = cloneServiceList.entrySet().iterator();
			while (itClone.hasNext()) {

				Map.Entry pairsClone = (Map.Entry) itClone.next();
				Service serviceClone = (Service) pairsClone.getKey();

				List<Operation> opsClone = serviceClone.getOperations();
				List<Parameter> inParametersClone = serviceClone
						.getParameters(Way.IN);
				List<Parameter> outParametersClone = serviceClone
						.getParameters(Way.OUT);

				int t = 0;

				if ((ops.size() == opsClone.size())
						&& (inParameters.size() == inParametersClone.size())
						&& (outParameters.size() == outParametersClone.size())) {
					if (service.getName().equals(serviceClone.getName())) {
						for (Operation operation : ops) {
							if (opsClone.contains(operation))
								t++;
							else
								break;
						}
					}
				}
				if (t == ops.size())
					System.out.println("these descriptions should be same: "
							+ service.getName() + " in " + pairs.getValue()
							+ " and " + serviceClone.getName() + " in "
							+ pairsClone.getValue());
			}
		}
	}

	private static void findAllDescPaths(String path) throws IOException,
			SAXException {

		File f = new File(path);

		String[] array = f.list();

		for (String string : array) {

			File file = new File(path + File.separator + string);

			if (file.isFile()) {

				if (file.getName().endsWith(".wsdl")) {

					System.out.println(file.getCanonicalPath());

					Document d = docBuilder.parse(file);
					Service s = wsdlParser.parse(d);
					serviceList.put(s, file.getCanonicalPath());
				}
			} else {
				findAllDescPaths(file.getCanonicalPath());
			}
		}
	}
}
