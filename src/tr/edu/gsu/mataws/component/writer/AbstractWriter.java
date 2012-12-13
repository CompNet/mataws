package tr.edu.gsu.mataws.component.writer;

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

import java.util.ArrayList;
import java.util.List;

import tr.edu.gsu.mataws.component.writer.descriptions.AbstractDescriptionWriter;
import tr.edu.gsu.mataws.component.writer.statistics.StatisticsWriterInterface;
import tr.edu.gsu.mataws.data.MatawsParameter;
import tr.edu.gsu.mataws.tools.log.HierarchicalLogger;
import tr.edu.gsu.mataws.tools.log.HierarchicalLoggerManager;

/**
 * Abstract class of the writer component.
 * <br/>
 * Other writers can be designed by using
 * different combinations of description 
 * or statistics writes.
 * 
 * @author Vincent Labatut
 */
public abstract class AbstractWriter
{	
	/**
	 * Initializes all the necessary objects
	 * for this selector.
	 * 
	 * @throws Exception 
	 * 		Problem while accessing the files.
	 */
	public AbstractWriter() throws Exception
	{	logger = HierarchicalLoggerManager.getHierarchicalLogger();
	
		initDescriptionWriters();
		initStatisticsWriters();
	}

	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	/** Logger */
	private HierarchicalLogger logger;

	/**
	 * Takes a list of words and selects the most
	 * relevant, leading to a single word.
	 * 
	 * @param subfolder
	 *		The folder containing the collection.
	 * @param parameters
	 * 		The list of annotated parameters.
	 * 
	 * @throws Exception 
	 * 		Problem while accessing the files.
	 */
	public void write(String subfolder, List<MatawsParameter> parameters) throws Exception
	{	logger.increaseOffset();
		
		writeDescriptions(subfolder, parameters);
		writeStatistics(subfolder, parameters);

		logger.decreaseOffset();
	}
	
	///////////////////////////////////////////////////////////
	//	DESCRIPTIONS						///////////////////
	///////////////////////////////////////////////////////////
	/** Sequence of description writers applied as is */
	protected final List<AbstractDescriptionWriter> descriptionWriters = new ArrayList<AbstractDescriptionWriter>();

	/**
	 * Initializes the sequence of description writers.
	 * 
	 * @throws Exception 
	 * 		Problem while accessing the files.
	 */
	protected abstract void initDescriptionWriters() throws Exception;

	/**
	 * Applies the sequence of description writers.
	 * 
	 * @param subfolder
	 *		The folder containing the collection.
	 * @param parameters
	 * 		The list of annotated parameters.
	 * 
	 * @throws Exception 
	 * 		Problem while accessing the files.
	 */
	private void writeDescriptions(String subfolder, List<MatawsParameter> parameters) throws Exception
	{	logger.increaseOffset();
	
		for(AbstractDescriptionWriter writer: descriptionWriters)
			writer.write(subfolder, parameters);

		logger.decreaseOffset();
	}

	///////////////////////////////////////////////////////////
	//	STATISTICS							///////////////////
	///////////////////////////////////////////////////////////
	/** Sequence of statistics writers applied as is */
	protected final List<StatisticsWriterInterface> statisticsWriters = new ArrayList<StatisticsWriterInterface>();

	/**
	 * Initializes the sequence of statistics writers.
	 * 
	 * @throws Exception 
	 * 		Problem while accessing the files.
	 */
	protected abstract void initStatisticsWriters() throws Exception;

	/**
	 * Applies the sequence of statistics writers.
	 * 
	 * @param subfolder
	 *		The folder containing the collection.
	 * @param parameters
	 * 		The list of annotated parameters. 
	 * 
	 * @throws Exception 
	 * 		Problem while accessing the files.
	 */
	private void writeStatistics(String subfolder, List<MatawsParameter> parameters) throws Exception
	{	logger.increaseOffset();
	
		for(StatisticsWriterInterface writer: statisticsWriters)
			writer.write(subfolder, parameters);
		
		logger.decreaseOffset();
	}
}
