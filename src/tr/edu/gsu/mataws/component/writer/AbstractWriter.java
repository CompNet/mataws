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

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import tr.edu.gsu.mataws.component.writer.descriptions.DescriptionWriterInterface;
import tr.edu.gsu.mataws.component.writer.statistics.StatisticsWriterInterface;
import tr.edu.gsu.mataws.data.MatawsParameter;

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
	 */
	public AbstractWriter()
	{	initDescriptionWriters();
		initStatisticsWriters();
	}

	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	/**
	 * Takes a list of words and selects the most
	 * relevant, leading to a single word.
	 * 
	 * @param subfolder
	 *		The folder containing the collection.
	 * @param parameters
	 * 		The list of annotated parameters. 
	 * @throws FileNotFoundException 
	 * 		Problem while writing the data.
	 */
	public void write(String subfolder, List<MatawsParameter> parameters) throws FileNotFoundException
	{	writeDescriptions(subfolder, parameters);
		writeStatistics(subfolder, parameters);
	}
	
	///////////////////////////////////////////////////////////
	//	DESCRIPTIONS						///////////////////
	///////////////////////////////////////////////////////////
	/** Sequence of description writers applied as is */
	protected final List<DescriptionWriterInterface> descriptionWriters = new ArrayList<DescriptionWriterInterface>();

	/**
	 * Initializes the sequence of description writers.
	 */
	protected abstract void initDescriptionWriters();

	/**
	 * Applies the sequence of description writers.
	 * 
	 * @param subfolder
	 *		The folder containing the collection.
	 * @param parameters
	 * 		The list of annotated parameters. 
	 * @throws FileNotFoundException 
	 * 		Problem while writing the data.
	 */
	private void writeDescriptions(String subfolder, List<MatawsParameter> parameters) throws FileNotFoundException
	{	for(DescriptionWriterInterface writer: descriptionWriters)
			writer.write(subfolder, parameters);
	}

	///////////////////////////////////////////////////////////
	//	STATISTICS							///////////////////
	///////////////////////////////////////////////////////////
	/** Sequence of statistics writers applied as is */
	protected final List<StatisticsWriterInterface> statisticsWriters = new ArrayList<StatisticsWriterInterface>();

	/**
	 * Initializes the sequence of statistics writers.
	 */
	protected abstract void initStatisticsWriters();

	/**
	 * Applies the sequence of statistics writers.
	 * 
	 * @param subfolder
	 *		The folder containing the collection.
	 * @param parameters
	 * 		The list of annotated parameters. 
	 * @throws FileNotFoundException 
	 * 		Problem while writing the data.
	 */
	private void writeStatistics(String subfolder, List<MatawsParameter> parameters) throws FileNotFoundException
	{	for(StatisticsWriterInterface writer: statisticsWriters)
			writer.write(subfolder, parameters);
	}
}
