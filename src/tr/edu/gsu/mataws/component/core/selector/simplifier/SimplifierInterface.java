package tr.edu.gsu.mataws.component.core.selector.simplifier;

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

import tr.edu.gsu.mataws.component.core.selector.IdentifiedWord;

/**
 * Interface for classes in charge of retaining only relevant strings.
 *  
 * @param <T>
 * 		Class used to represent the synsets. 
 *  
 * @author Vincent Labatut
 */
public interface SimplifierInterface<T>
{
	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	/**
	 * Takes a list of words and tries to simplify it,
	 * i.e. to reduce its size while minimizing the information
	 * loss caused by this operation.
	 * 
	 * @param words
	 * 		The list of strings to be simplified. 
	 * @return
	 * 		A simplified list of words
	 * 			
	 */
	public List<IdentifiedWord<T>> simplify(List<IdentifiedWord<T>> words);
}
