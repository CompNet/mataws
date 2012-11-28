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

import tr.edu.gsu.mataws.component.reader.AbstractReader;
import tr.edu.gsu.mataws.component.reader.DefaultReader;
import tr.edu.gsu.mataws.data.MatawsParameter;
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
	{	reader = new DefaultReader();
		operationProcessor = new OperationProcessor();
	}
	
	///////////////////////////////////////////////////////////
	//	PROCESS							///////////////////////
	///////////////////////////////////////////////////////////
	/** Component in charge of loading the collection */
	private AbstractReader reader;
	/** Processor used to take advantage of the operation details */
	private OperationProcessor operationProcessor;
	
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
	{	// load the collection
		Collection collection = reader.read(subfolder);
		
		// process each operation separately
		SortedSet<Operation> operations = collection.getOperations();
		List<MatawsParameter> parameters = new ArrayList<MatawsParameter>();
		for(Operation operation: operations)
		{	List<MatawsParameter> temp = operationProcessor.process(operation);
			parameters.addAll(temp);
		}
		
		// record the collection and stats
		// TODO
	}
}
