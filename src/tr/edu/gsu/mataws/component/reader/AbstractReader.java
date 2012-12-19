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
import tr.edu.gsu.mataws.tools.log.HierarchicalLogger;
import tr.edu.gsu.mataws.tools.log.HierarchicalLoggerManager;
import tr.edu.gsu.sine.col.Collection;

/**
 * Abstract class of the reader component.
 * <br/>
 * Can be configured using various types of readers.
 * 
 * @author Vincent Labatut
 */
public abstract class AbstractReader
{	
	/**
	 * Initializes all the necessary objects
	 * for this reader.
	 */
	public AbstractReader()
	{	logger = HierarchicalLoggerManager.getHierarchicalLogger();
	
		initDescriptionReader();
		initOtherReaders();
	}

	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	/** Logger */
	private HierarchicalLogger logger;
	/** Collection read (possibly used by other readers) */
	protected Collection collection;
	
	/**
	 * Reads the collection and possibly other data.
	 * 
	 * @param subfolder 
	 * 		The folder containing the collection, or {@code null}
	 * 		to process the whole content of the input folder.
	 * @return 
	 * 		The read collection.
	 * @throws Exception
	 * 		Problem while reading the collection. 
	 */
	public Collection read(String subfolder) throws Exception
	{	logger.log("Reading folder "+subfolder);
		logger.increaseOffset();
	
		this.subfolder = subfolder;
		collection = descriptionReader.readCollection(subfolder);
		
		readOthers();
		
		logger.decreaseOffset();
		return collection;
	}

	///////////////////////////////////////////////////////////
	//	DESCRIPTIONS						///////////////////
	///////////////////////////////////////////////////////////
	/** Reader used to get the collection of WS descriptions */
	protected AbstractDescriptionReader descriptionReader;
	/** The folder containing the collection (in case it is necessary to the other readers */
	protected String subfolder;

	/**
	 * Inits the reader used to get the collection.
	 */
	protected abstract void initDescriptionReader();
	
	///////////////////////////////////////////////////////////
	//	OTHERS								///////////////////
	///////////////////////////////////////////////////////////
	/**
	 * Initializes the readers required for additional processing.
	 */
	protected abstract void initOtherReaders();

	/**
	 * Reads the additional data (might requires
	 * defining additional fields, and methods to
	 * access them).
	 * 
	 * @throws Exception
	 * 		Problem while reading the collection. 
	 */
	protected abstract void readOthers() throws Exception;
}
