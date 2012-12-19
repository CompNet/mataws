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

import tr.edu.gsu.mataws.component.reader.descriptions.WsdlDescriptionReader;
import tr.edu.gsu.mataws.component.reader.evaluations.EvaluatedParameterReader;
import tr.edu.gsu.mataws.data.stat.CollectionStats;

/**
 * Default class for reading the collection.
 * 
 * @author Vincent Labatut
 */
public class DefaultReader extends AbstractReader
{	
	public DefaultReader(boolean readStats)
	{	super();
		
		
	}
	
	///////////////////////////////////////////////////////////
	//	DESCRIPTIONS						///////////////////
	///////////////////////////////////////////////////////////
	/**
	 * Inits the reader used to get the collection
	 */
	protected void initDescriptionReader()
	{	descriptionReader = new WsdlDescriptionReader();
	}
	
	///////////////////////////////////////////////////////////
	//	OTHERS								///////////////////
	///////////////////////////////////////////////////////////
	private boolean readStats;
	private EvaluatedParameterReader otherReader;
	private CollectionStats stats;
	
	@Override
	protected void initOtherReaders()
	{	if(readStats)
			otherReader = new EvaluatedParameterReader();
	}

	@Override
	protected void readOthers() throws Exception
	{	// reading the evaluated annotations, in order to process some statistics
		if(readStats)
		{	otherReader.read(subfolder);
			stats = otherReader.getStats();
		}
	}
}
