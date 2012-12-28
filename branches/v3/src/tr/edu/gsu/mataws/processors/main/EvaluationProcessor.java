package tr.edu.gsu.mataws.processors.main;

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

import tr.edu.gsu.mataws.component.reader.EvaluationReader;
import tr.edu.gsu.mataws.component.writer.AbstractWriter;
import tr.edu.gsu.mataws.component.writer.DefaultWriter;
import tr.edu.gsu.mataws.data.stat.CollectionStats;
import tr.edu.gsu.mataws.tools.log.HierarchicalLogger;
import tr.edu.gsu.mataws.tools.log.HierarchicalLoggerManager;

/**
 * This class handles the general algorithm for
 * the evaluation of the annotations.
 * 
 * @author Vincent Labatut
 */
public class EvaluationProcessor
{	
	/**
	 * Builds a standard evaluation processor.
	 * 
	 * @throws Exception
	 * 		Problem while accessing the collection files. 
	 */
	public EvaluationProcessor() throws Exception
	{	logger = HierarchicalLoggerManager.getHierarchicalLogger();
		
		reader = new EvaluationReader();
writer = new DefaultWriter(); // TODO
	}
	
	///////////////////////////////////////////////////////////
	//	PROCESS							///////////////////////
	///////////////////////////////////////////////////////////
	/** Logger */
	protected HierarchicalLogger logger;
	/** Component in charge of loading the collection */
	private EvaluationReader reader;
	/** Component in charge of recording the results of the evaluation process */
	private AbstractWriter writer;
	
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
	 * @throws Exception 
	 * 		Problem while accessing the collection files. 
	 */
	public void process() throws Exception
	{	logger.log("Starting to process the evaluations");
		logger.increaseOffset();
		
		// load the evaluation
		reader.read();
		CollectionStats collectionStats = reader.getStats();
		
		// process the results
		
		// record the results
//		writer.write(subfolder, parameters); TODO
		
		logger.decreaseOffset();
		logger.log("Process over for the evaluations");
	}
}
