package tr.edu.gsu.mataws.component.indentificator.breaker;

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

import java.util.List;
import java.util.Map;

import tr.edu.gsu.mataws.data.IdentifiedWord;
import tr.edu.gsu.mataws.tools.log.HierarchicalLogger;
import tr.edu.gsu.mataws.tools.log.HierarchicalLoggerManager;
import tr.edu.gsu.sine.col.Way;

/**
 * Interface for classes in charge of identifying  
 * parts of operation names which can be used to
 * describe their parameters, eg. {@code getIdForName}.
 * 
 * @param <T> 
 *		Class used to represent a WordNet synset.
 * 
 * @author Vincent Labatut
 */
public abstract class AbstractBreaker<T>
{
	/**
	 * Builds a component.
	 */
	public AbstractBreaker()
	{	logger = HierarchicalLoggerManager.getHierarchicalLogger();
	}
	
	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	/** Logger */
	protected HierarchicalLogger logger;

	/**
	 * Takes an operation and analyses its name, in order to
	 * extract information regarding its parameters.
	 * 
	 * @param operationList
	 * 		The list of words composing the operation name. 
	 * @return
	 * 		A map of the identified parts, or null if none could be identified.
	 */
	public abstract Map<Way,List<List<IdentifiedWord<T>>>> breakk(List<IdentifiedWord<T>> operationList);
}
