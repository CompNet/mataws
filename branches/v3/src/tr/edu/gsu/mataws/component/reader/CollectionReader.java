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

import tr.edu.gsu.mataws.component.reader.descriptions.AbstractDescriptionReader;
import tr.edu.gsu.mataws.component.reader.descriptions.WsdlDescriptionReader;
import tr.edu.gsu.sine.col.Collection;

/**
 * Default class for reading the collection.
 * 
 * @author Vincent Labatut
 */
public class CollectionReader extends AbstractReader
{	
	/**
	 * Builds a standard reader for the
	 * specified subfolder (can be {@code null}
	 * if one wants to read the whole input
	 * folder).
	 * 
	 * @param subfolder
	 * 		The targetted subfolder, or {@code null} to
	 * 		process the whole input folder.
	 */
	public CollectionReader(String subfolder)
	{	super();
		
		this.subfolder = subfolder;
	}
	
	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	/** Reader used to get the collection of WS descriptions */
	private AbstractDescriptionReader reader;
	/** The folder containing the collection (in case it is necessary to the other readers */
	private String subfolder;
	/** Collection read */
	private Collection collection;

	@Override	
	protected void initReader()
	{	reader = new WsdlDescriptionReader();
	}

	@Override
	public void read() throws Exception
	{	logger.log("Reading the collection in folder "+subfolder);
		logger.increaseOffset();
	
		collection = reader.readCollection(subfolder);
		
		logger.decreaseOffset();
	}
	
	/**
	 * Returns the collection (previously
	 * read).
	 * 
	 * @return
	 * 		The read collection.
	 */
	public Collection getCollection()
	{	return collection;
	}
}
