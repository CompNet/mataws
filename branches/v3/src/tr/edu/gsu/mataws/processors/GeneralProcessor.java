package tr.edu.gsu.mataws.processors;

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

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import tr.edu.gsu.mataws.component.reader.CollectionReader;
import tr.edu.gsu.mataws.component.reader.WsdlCollectionReader;
import tr.edu.gsu.mataws.data.MatawsParameter;
import tr.edu.gsu.mataws.processors.parameter.ParameterProcessor;
import tr.edu.gsu.sine.col.Collection;
import tr.edu.gsu.sine.col.Operation;

/**
 * This class handles the general algorithm for
 * the annotation of a whole collection of WS descriptions.
 * 
 * @author Vincent Labatut
 */
public class GeneralProcessor
{	
	/**
	 * Builds a standard general processor.
	 */
	public GeneralProcessor()
	{	collectionReader = new WsdlCollectionReader();
		operationProcessor = new OperationProcessor();
		parameterProcessor = new ParameterProcessor();	
	}
	
	///////////////////////////////////////////////////////////
	//	PROCESS							///////////////////////
	///////////////////////////////////////////////////////////
	/** Component in charge of loading the collection */
	private CollectionReader collectionReader;
	/** Processor used to take advantage of the operation details */
	private OperationProcessor operationProcessor;
	/** Processor used to annotate the rest of the parameter */
	private ParameterProcessor parameterProcessor;
	
	/**
	 * Implements the general algorithm for the 
	 * annotation process. Will read the collection,
	 * then annotate it, and finally record the new
	 * description files.
	 * 
	 * @param subfolder
	 * 		Subfolder containing the collection, or {@code null} to
	 * 		process the whole input folder.
	 * 
	 * @throws FileNotFoundException 
	 * 		Problem while reading the collection or recording the collection
	 * 		or statistics.
	 */
	public void process(String subfolder) throws FileNotFoundException
	{	// init
		List<MatawsParameter> remainingParameters = new ArrayList<MatawsParameter>();
		List<MatawsParameter> processedParameters = new ArrayList<MatawsParameter>();

		// load the collection
		Collection collection = collectionReader.readCollection(subfolder);
		
		// process each operation separately
		SortedSet<Operation> operations = collection.getOperations();
		for(Operation operation: operations)
		{	List<MatawsParameter> parameters = operationProcessor.process(operation);
			for(MatawsParameter parameter: parameters)
			{	if(parameter.getConcept()==null)
					remainingParameters.add(parameter);
				else
					processedParameters.add(parameter);
			}
		}
		
		// process each non-annotated parameter separately
		for(MatawsParameter parameter: remainingParameters)
		{	parameterProcessor.process(parameter);
			processedParameters.add(parameter);
		}
	
		// record the collection and stats
		// TODO
	}
}
