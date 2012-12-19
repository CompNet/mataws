package tr.edu.gsu.mataws.component.reader.evaluations;

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

import tr.edu.gsu.mataws.data.stat.CollectionStats;
import tr.edu.gsu.mataws.tools.log.HierarchicalLogger;
import tr.edu.gsu.mataws.tools.log.HierarchicalLoggerManager;

/**
 * This class is used to read some evaluations of the 
 * annotation process, performed externally and manually.
 * 
 * @author Vincent Labatut
 */
public abstract class AbstractOtherReader
{
	/**
	 * Builds a component.
	 */
	public AbstractOtherReader()
	{	logger = HierarchicalLoggerManager.getHierarchicalLogger();
	}
	
	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	/** Logger */
	protected HierarchicalLogger logger;

	/**
	 * Loads the evaluation data and uses it
	 * to complete the specified statistics objects.
	 * 
	 * @param stats
	 *		The statistics object to complete.
	 * 
	 * @throws Exception 
	 * 		Some exception was met while reading the additional data. 
	 */
	public abstract void read(CollectionStats stats) throws Exception;
}
