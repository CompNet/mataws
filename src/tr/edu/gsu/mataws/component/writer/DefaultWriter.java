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

import tr.edu.gsu.mataws.component.writer.descriptions.AbstractDescriptionWriter;
import tr.edu.gsu.mataws.component.writer.descriptions.OwlsDescriptionWriter;
import tr.edu.gsu.mataws.component.writer.statistics.StatisticsWriterInterface;
import tr.edu.gsu.mataws.tools.semantics.SigmaTools;

/**
 * Default class for writing the collection.
 * 
 * @author Vincent Labatut
 */
public class DefaultWriter extends AbstractWriter
{
	/**
	 * Builds a default writer.
	 * 
	 * @throws Exception 
	 * 		Problem while accessing the files.
	 */
	public DefaultWriter() throws Exception
	{	super();
	}

	///////////////////////////////////////////////////////////
	//	DESCRIPTIONS						///////////////////
	///////////////////////////////////////////////////////////
	@Override
	protected void initDescriptionWriters() throws Exception
	{	AbstractDescriptionWriter writer;
		
		writer = new OwlsDescriptionWriter(SigmaTools.URI);
		descriptionWriters.add(writer);
	}

	///////////////////////////////////////////////////////////
	//	STATISTICS							///////////////////
	///////////////////////////////////////////////////////////
	@Override
	protected void initStatisticsWriters()
	{	StatisticsWriterInterface writer;
		
		// TODO Auto-generated method stub
	}	
}
