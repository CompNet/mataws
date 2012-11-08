package tr.edu.gsu.mataws.transformer.impl;

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
import java.io.FileOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.mindswap.owl.OWLFactory;
import org.mindswap.owl.OWLOntology;
import org.mindswap.owls.vocabulary.OWLS;
import org.mindswap.wsdl.WSDLOperation;
import org.mindswap.wsdl.WSDLParameter;
import org.mindswap.wsdl.WSDLService;
import org.mindswap.wsdl.WSDLTranslator;

import tr.edu.gsu.mataws.statistics.StatisticsUtil;
import tr.edu.gsu.mataws.transformer.Transformer;

/**
 * This class is used to transform a wsdl file to owl-s file.
 * 
 * @author Cihan Aksoy
 * 
 */
public class OWLSTransformerImpl implements Transformer {

	private StatisticsUtil statistics;
	private Map<String, String> parameterAnnotationMap;
	private String prefix = "#";

	/**
	 * Constructs an instance of this class.
	 */
	public OWLSTransformerImpl() {
		statistics = StatisticsUtil.getInstance();
		parameterAnnotationMap = statistics.getParameterAnnotationMap();
	}

	@Override
	public void transform(String sourceFile, String destFolder) {

		WSDLService s = null;
		List<WSDLOperation> ops = new ArrayList<WSDLOperation>();

		File sFile = new File(sourceFile);

		// wsdl loading
		try {
			s = WSDLService.createService(sFile.toURI().toString());
			ops = s.getOperations();
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (WSDLOperation wsdlOperation : ops) {
			if (wsdlOperation == null) {
				System.out.println("WSDL operation is null !!");
			} else {
				String serviceName = wsdlOperation.getName();
				String name = serviceName.replaceAll(" ", "_");

				File file = new File(destFolder + File.separator + name
						+ ".owl");

				URI ontURI = null;
				try {
					ontURI = new URI("http://www.gsu.edu.tr/service.owl");
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}

				OWLOntology ont = OWLFactory.createKB().createOntology(ontURI);
				OWLS.addOWLSImports(ont);

				WSDLTranslator t = new WSDLTranslator(ont, wsdlOperation, name);
				t.setServiceName(serviceName);
				t.setTextDescription(wsdlOperation.getDescription());

				// inputs
				Vector<WSDLParameter> inputs = wsdlOperation.getInputs();
				for (WSDLParameter wsdlParameter : inputs) {
					String paramName = wsdlParameter.getName();
					String[] paramNameTemp = paramName.split("#");
					String concept = null;
					String paramType = null;

					// since wsdlParameter type doesn't give the parameter
					// itself, we find a concept for a param using its name
					concept = parameterAnnotationMap.get(paramNameTemp[1]);

					if (concept != null) {
						if (!concept.equals("NoMatch"))
							paramType = prefix + concept;
						else
							paramType = prefix + "NoMatch";
					} else
						paramType = prefix + "NoMatch";

					URI paramTypeURI = null;
					try {
						paramTypeURI = new URI(paramType);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String xsltTransformation = "xsltTransformation";

					t.addInput(wsdlParameter, paramName, paramTypeURI,
							xsltTransformation);
				}

				// outputs
				Vector<WSDLParameter> outputs = wsdlOperation.getOutputs();
				for (WSDLParameter wsdlParameter : outputs) {
					String paramName = wsdlParameter.getName();
					String[] paramNameTemp = paramName.split("#");
					String concept = null;
					String paramType = null;

					concept = parameterAnnotationMap.get(paramNameTemp[1]);

					if (concept != null) {
						if (!concept.equals("NoMatch"))
							paramType = prefix + concept;
						else
							paramType = prefix + "NoMatch";
					} else
						paramType = prefix + "NoMatch";

					URI paramTypeURI = null;
					try {
						paramTypeURI = new URI(paramType);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String xsltTransformation = "xsltTransformation";

					t.addOutput(wsdlParameter, paramName, paramTypeURI,
							xsltTransformation);
				}

				try {
					FileOutputStream fos = new FileOutputStream(file);
					t.writeOWLS(fos);
					fos.close();
					System.out.println("Saved to " + file.toURI());
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}

	}

}
