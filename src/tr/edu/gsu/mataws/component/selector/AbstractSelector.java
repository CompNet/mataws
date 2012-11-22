package tr.edu.gsu.mataws.component.selector;

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

import tr.edu.gsu.mataws.component.selector.simplifier.SimplifierInterface;
import tr.edu.gsu.mataws.data.IdentifiedWord;

/**
 * Abstract class of the selector component.
 * <br/>
 * Other selectors can be designed by using
 * different combinations of simplifiers, 
 * and/or different simplifiers.
 * 
 * @param <T> 
 *		Class used to represent a WordNet synset.
 *  
 * @author Vincent Labatut
 */
public abstract class AbstractSelector<T>
{	
	/**
	 * Initializes all the necessary objects
	 * for this selector.
	 */
	public AbstractSelector()
	{	initSimplifiers();
	}

	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	/**
	 * Takes a list of words and selects the most
	 * relevant, leading to a single word.
	 * 
	 * @param words
	 * 		The list of identified words to process.
	 * @return
	 * 		The resulting {@link IdentifiedWord}, or {@code null} 
	 * 		if the selection was inconclusive.
	 */
	public IdentifiedWord<T> select(List<IdentifiedWord<T>> words)
	{	// simplification
		simplify(words);
		
		// result
		IdentifiedWord<T> result = null;
		if(words.size()>1)
			throw new IllegalArgumentException("Problem during selection: final word list should not contain more than one word.");
		else if(!words.isEmpty())
			result = words.get(0);
		
		return result;
	}
	
	///////////////////////////////////////////////////////////
	//	SIMPLIFICATION						///////////////////
	///////////////////////////////////////////////////////////
	/** Sequence of simplifiers applied as is */
	protected final List<SimplifierInterface<T>> simplifiers = new ArrayList<SimplifierInterface<T>>();

	/**
	 * Initializes the sequence of simplifiers.
	 */
	protected abstract void initSimplifiers();

	/**
	 * Applies the sequence of simplifiers.
	 * After this process, there should remain
	 * only either one word, or none at all.
	 * 
	 * @param words
	 * 		List of words to be processed.
	 */
	public void simplify(List<IdentifiedWord<T>> words)
	{	// repeat the whole process as long as at least one simplifier is successful
		boolean effect;
		do
		{	effect = false;
			Iterator<SimplifierInterface<T>> it = simplifiers.iterator();
			while(it.hasNext() && words.size()>1)
			{	SimplifierInterface<T> simplifier = it.next();
				effect = simplifier.simplify(words) || effect;
			}
		}
		while(effect);
	}
}
