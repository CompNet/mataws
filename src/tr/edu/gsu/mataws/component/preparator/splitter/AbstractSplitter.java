package tr.edu.gsu.mataws.component.preparator.splitter;

/*
 * Mataws - Multimodal Automatic Tool for the Annotation of Web Services
 * Copyright 2010 Cihan Aksoy and Koray Man�uhan
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

import java.util.List;

import tr.edu.gsu.mataws.tools.log.HierarchicalLogger;
import tr.edu.gsu.mataws.tools.log.HierarchicalLoggerManager;

/**
 * Interface for classes in charge of splitting strings.
 *  
 * @author Koray Mancuhan
 * @author Cihan Aksoy
 * @author Vincent Labatut
 */
public abstract class AbstractSplitter
{
	/**
	 * Builds a component.
	 */
	public AbstractSplitter()
	{	logger = HierarchicalLoggerManager.getHierarchicalLogger();
	}
	
	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	/** Logger */
	protected HierarchicalLogger logger;

	/**
	 * Takes a list of strings and returns a list of their components 
	 * resulting from the split implemented by this object.
	 * The list can be empty, if the original strings contain
	 * only noise, or if the original list is empty.
	 * 
	 * @param strings
	 * 		The list of strings to be split. 
	 * @return
	 * 		A list of substrings resulting from the split.
	 * 			
	 */
	public abstract List<String> split(List<String> strings);
}
