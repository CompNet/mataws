package tr.edu.gsu.mataws.component.associator;

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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import tr.edu.gsu.mataws.component.associator.mapper.MapperInterface;
import tr.edu.gsu.mataws.component.selector.IdentifiedWord;

/**
 * Abstract class of the associator component.
 *  
* @param <T> 
 *		Class used to represent a WordNet synset.
 *
 * @author Vincent Labatut
 */
public abstract class AbstractAssociator<T>
{	
	/**
	 * Initializes all the necessary objects
	 * for this associator.
	 */
	public AbstractAssociator()
	{	initMappers();
	}

	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	/**
	 * Takes an identified word, and find the associated
	 * concepts, which takes the form of a string.
	 * 
	 * @param word
	 * 		Identified word.
	 * @return
	 * 		A string representing the associated concept, or {@code null} if
	 * 		no concept could be retrieved for the word.
	 */
	public String associate(IdentifiedWord<T> word)
	{	String result = null;
		
		result = map(word);
		
		return result;
	}
	
	///////////////////////////////////////////////////////////
	//	MAP									///////////////////
	///////////////////////////////////////////////////////////
	/** Sequence of mappers applied as is */
	protected final List<MapperInterface<T>> mappers = new ArrayList<MapperInterface<T>>();

	/**
	 * Initializes the sequence of mappers.
	 */
	protected abstract void initMappers();

	/**
	 * Applies the sequence of mappers.
	 * 
	 * @param word
	 * 		The identified word to be processed.
	 * @return
	 * 		Associated concept, or {@code null} if
	 * 		none could be found. 
	 */
	protected String map(IdentifiedWord<T> word)
	{	String result = null;
		
		Iterator<MapperInterface<T>> it = mappers.iterator();
		while(it.hasNext() && result==null)
		{	MapperInterface<T> mapper = it.next();
			result = mapper.map(word);
		}
		
		return result;
	}
}
