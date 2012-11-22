package tr.edu.gsu.mataws.component.selector.identifier;

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

import tr.edu.gsu.mataws.component.selector.IdentifiedWord;

/**
 * Interface for classes in charge of identifying the 
 * WordNet synset associated to some word.
 *  
 * @param <T> 
 *		Class used to represent a WordNet synset.
 *  
 * @author Vincent Labatut
 */
public interface IdentifierInterface<T>
{
	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	/**
	 * Takes a list of {@link IdentifiedWord} objects and 
	 * updates them. If everything goes fine, each original 
	 * {@code String} should then be associated to its stem 
	 * and synset in WordNet.
	 * 
	 * @param words
	 * 		The list of words to be identified. 
	 * 			
	 */
	public void identify(List<IdentifiedWord<T>> words);
}
