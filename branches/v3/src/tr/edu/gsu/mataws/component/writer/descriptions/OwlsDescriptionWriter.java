package tr.edu.gsu.mataws.component.writer.descriptions;

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
import java.io.FilenameFilter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
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

import tr.edu.gsu.mataws.data.parameter.MatawsParameter;
import tr.edu.gsu.mataws.tools.misc.FileTools;

/**
 * This class is used to write the data resulting from the annotation
 * process, as a collection of OWL-S files.
 * 
 * @author Koray Mancuhan
 * @author Cihan Aksoy
 * @author Vincent Labatut
 */
public class OwlsDescriptionWriter extends AbstractDescriptionWriter
{	
	/**
	 * Creates a new description writer,
	 * using the specified URI ontology
	 * when creating OWL-S files.
	 * 
	 * @param ontologyUri
	 * 		URI of the ontology.
	 * 
	 * @throws URISyntaxException 
	 * 		Problem with the specified URI.
	 */
	public OwlsDescriptionWriter(String ontologyUri) throws URISyntaxException
	{	super();
	
		// TODO this uri might actually be related to the WS itself
		URI uri = new URI(ontologyUri);
		OWLOntology ontology = OWLFactory.createKB().createOntology(uri);
		OWLS.addOWLSImports(ontology);
	}
	
	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	/** Concept separator */
	private final static String SEPARATOR = "#";
	/** Ontology  */
	private OWLOntology ontology = null;
	
	@Override
	public void write(String subfolder, List<MatawsParameter> parameters) throws Exception
	{	logger.log("Writting OWL-S collection "+subfolder);
		logger.increaseOffset();
	
		// init paths
		logger.log("Setting paths");
		String inputPath = FileTools.INPUT_FOLDER;
		String outputPath = FileTools.COLLECTION_FOLDER;
		if(subfolder!=null)
		{	inputPath = inputPath + File.separator + subfolder;
			outputPath = outputPath + File.separator + subfolder;
		}
		File inFolder = new File(inputPath);
		File outFolder = new File(outputPath);
		
		// init parameter map
		logger.log("Initializing parameter map ("+parameters.size()+" parameters)");
		Map<String,String> parameterMap = new HashMap<String, String>();
		for(MatawsParameter parameter: parameters)
		{	String name = parameter.getSineParameter().getUniqueName();
			String concept = parameter.getConcept();
			parameterMap.put(name, concept);
		}
		
		// process the input folder content
		logger.log("Starting writting");
		if(!outFolder.exists())
			outFolder.mkdir();
		writeFolder(inFolder, outFolder, parameterMap);

		logger.log("Writting finished for the collection");
		logger.decreaseOffset();
	}

	/**
	 * Processes an input folder: the corresponding output
	 * folder with corresponding output files are generated.
	 * 
	 * @param inFolder
	 * 		Folder to be read.
	 * @param outFolder
	 * 		Folder to be written.
	 * @param parameters
	 * 		Annotated parameters.
	 * @throws Exception 
	 * 		Problem while accessing the files.
	 */
	private void writeFolder(File inFolder, File outFolder, Map<String,String> parameters) throws Exception
	{	logger.log("Writting folder "+inFolder.getName());
		logger.increaseOffset();
	
		// browse the input folder
		logger.log("Retriving its content");
		FilenameFilter filter = new FilenameFilter()
		{	@Override
			public boolean accept(File dir, String name)
			{	String s[] = name.split(".");
				boolean result = s.length>1 && s[s.length-1].equalsIgnoreCase("wsdl");
				return result;
			}
		};
		File[] folderContent = inFolder.listFiles(filter);
		
		// process separately each one of its files/folders
		logger.log("Process each file/folder it contains ("+folderContent.length+" in total)");
		logger.increaseOffset();
		for(File inFile: folderContent)
		{	// if it's a folder: recursive process
			if(inFile.isDirectory())
			{	String name = inFile.getName();
				String path = outFolder.getPath() + File.separator + name;
				File outFile = new File(path);
				if(!outFile.exists())
					outFile.mkdir();
				writeFolder(inFile,outFile,parameters);
			}
			
			// if it's a file, read it
			else
			{	String name = inFile.getName();
				int index = name.lastIndexOf(".");
				name = name.substring(0,index) + ".owl";
				String path = outFolder.getPath() + File.separator + name;
				File outFile = new File(path);
				writeFile(inFile,outFile,parameters);
			}
		}
		logger.decreaseOffset();
		
		logger.log("Writting finished for folder "+inFolder.getName());
		logger.decreaseOffset();
	}
	
	/**
	 * Processes an input file: the corresponding WS description
	 * is created in the output folder.
	 * 
	 * @param inFile
	 * 		File to be read.
	 * @param outFile
	 * 		File to be read.
	 * @param parameterMap
	 * 		Annotated parameters.
	 * @throws Exception 
	 * 		Problem while accessing the files.
	 */
	private void writeFile(File inFile, File outFile, Map<String,String> parameterMap) throws Exception
	{	logger.log("Writting file "+inFile.getName());
		logger.increaseOffset();
	
		// load wsdl file
		logger.log("Loading the WSDL file");
		WSDLService service = WSDLService.createService(inFile.toURI());
		List<WSDLOperation> operations = service.getOperations();

		logger.log("Processing each operation");
		logger.increaseOffset();
		for(WSDLOperation operation : operations)
		{	logger.log("Process operation "+operation.getName());
			String serviceName = operation.getName(); //TODO why not service?
			String name = serviceName.replaceAll(" ", "_"); //TODO why this?

			// init translator
			WSDLTranslator translator = new WSDLTranslator(ontology, operation, name);
			translator.setServiceName(serviceName);
			translator.setTextDescription(operation.getDescription());

			// annotate inputs
			Vector<WSDLParameter> inputs = operation.getInputs();
			for(WSDLParameter parameter : inputs)
			{	String fullParameterName = parameter.getName();
//TODO Sine's unique name should actually be built using this name (and its operation's and ws')			
				int index = fullParameterName.lastIndexOf(SEPARATOR);
				String parameterName = fullParameterName.substring(index+1,fullParameterName.length());
				String concept = parameterMap.get(parameterName);
				if(concept==null)
					concept = NO_CONCEPT;
				String paramType = SEPARATOR + concept;

				URI paramTypeURI = new URI(paramType);
				String xsltTransformation = "xsltTransformation";
				translator.addInput(parameter, parameterName, paramTypeURI, xsltTransformation);
			}

			// annotate outputs
			Vector<WSDLParameter> outputs = operation.getOutputs();
			for(WSDLParameter parameter : outputs)
			{	String fullParameterName = parameter.getName();
				int index = fullParameterName.lastIndexOf(SEPARATOR);
				String parameterName = fullParameterName.substring(index+1,fullParameterName.length());
				String concept = parameterMap.get(parameterName);
				if(concept==null)
					concept = NO_CONCEPT;
				String paramType = SEPARATOR + concept;

				URI paramTypeURI = new URI(paramType);
				String xsltTransformation = "xsltTransformation";
				translator.addOutput(parameter, parameterName, paramTypeURI, xsltTransformation);
			}
			
			// record output file
			FileOutputStream fos = new FileOutputStream(outFile);
			translator.writeOWLS(fos);
			fos.close();
		}
		logger.decreaseOffset();
		
		logger.log("Writting finished for file "+inFile.getName());
		logger.decreaseOffset();
	}
}
