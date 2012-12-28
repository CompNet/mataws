package tr.edu.gsu.mataws.component.reader;

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

import tr.edu.gsu.mataws.component.reader.evaluations.EvaluatedParameterReader;
import tr.edu.gsu.mataws.data.stat.CollectionStats;

/**
 * Default class for reading the manual evaluation.
 * 
 * @author Vincent Labatut
 */
public class EvaluationReader extends AbstractReader
{	
	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	/** Reader used to get the evaluations */
	private EvaluatedParameterReader reader;
	/** Statistics */
	private CollectionStats stats;

	/**
	 * Inits the reader used to get the collection
	 */
	protected void initReader()
	{	reader = new EvaluatedParameterReader();
	}
	
	@Override
	public void read() throws Exception
	{	logger.log("Reading the evaluation file");
		logger.increaseOffset();
	
		stats = new CollectionStats();
		reader.read(stats);

		logger.decreaseOffset();
	}
	
	/**
	 * Returns the read starts.
	 * 
	 * @return
	 * 		Statistics object.
	 */
	public CollectionStats getStats()
	{	return stats;
	}
}
