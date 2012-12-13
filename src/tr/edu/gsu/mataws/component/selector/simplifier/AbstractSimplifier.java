package tr.edu.gsu.mataws.component.selector.simplifier;

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

import tr.edu.gsu.mataws.data.IdentifiedWord;

/**
 * Interface for classes in charge of retaining only relevant strings.
 *  
 * @param <T>
 * 		Class used to represent the synsets. 
 *  
 * @author Vincent Labatut
 */
public abstract class AbstractSimplifier<T>
{
	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	/**
	 * Takes a list of words and tries to simplify it,
	 * i.e. to reduce its size while minimizing the information
	 * loss caused by this operation.
	 * <br/>
	 * The returned boolean indicates whether or not the
	 * simplifier could actually simplify the original list. 
	 * 
	 * @param words
	 * 		The list of strings to be simplified. 
	 * @return
	 * 		{@code true} iff the simplifier could simplify the list.
	 * 			
	 */
	public abstract boolean simplify(List<IdentifiedWord<T>> words);
}
