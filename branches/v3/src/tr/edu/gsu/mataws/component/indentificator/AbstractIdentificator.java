package tr.edu.gsu.mataws.component.indentificator;

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
import java.util.Map;

import tr.edu.gsu.mataws.component.indentificator.breaker.BreakerInterface;
import tr.edu.gsu.mataws.data.IdentifiedWord;
import tr.edu.gsu.mataws.tools.misc.MatawsWay;

/**
 * Abstract class of the identificator component.
 *  
 * @param <T> 
 *		Class used to represent a WordNet synset.
 *
 * @author Vincent Labatut
 */
public abstract class AbstractIdentificator<T>
{	
	/**
	 * Initializes all the necessary objects
	 * for this identificator.
	 */
	public AbstractIdentificator()
	{	initBreakers();
	}

	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	/**
	 * Takes an operation name already split in identified words,
	 * and tries to identify distinct parts in this name, corresponding
	 * to distinct parameters.
	 * 
	 * @param operationName
	 * 		A list of the words composing the operation name.
	 * @return
	 * 		A map representing the distinct parts identified in the operation name.
	 */
	public Map<MatawsWay,List<IdentifiedWord<T>>> identify(List<IdentifiedWord<T>> operationName)
	{	// apply the breakers
		Map<MatawsWay,List<IdentifiedWord<T>>> result = breakk(operationName);
		
		return result;
	}
	
	///////////////////////////////////////////////////////////
	//	BREAK						///////////////////////////
	///////////////////////////////////////////////////////////
	/** Sequence of breakers applied as is */
	protected final List<BreakerInterface<T>> breakers = new ArrayList<BreakerInterface<T>>();

	/**
	 * Initializes the sequence of breakers.
	 */
	protected abstract void initBreakers();

	/**
	 * Applies the sequence of breakers.
	 * 
	 * @param operationList
	 * 		List of words composing the operation name.
	 * @return
	 * 		The map representing the distinct parts identified in the operation name. 
	 */
	protected Map<MatawsWay,List<IdentifiedWord<T>>> breakk(List<IdentifiedWord<T>> operationList)
	{	Map<MatawsWay,List<IdentifiedWord<T>>> result = null;
		
		Iterator<BreakerInterface<T>> it = breakers.iterator();
		while(it.hasNext() && result==null)
		{	BreakerInterface<T> breaker = it.next();
			result = breaker.breakk(operationList);
		}
		
		return result;
	}
}
