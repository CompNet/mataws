package tr.edu.gsu.mataws.component.core.selector;

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

import tr.edu.gsu.mataws.component.core.selector.identifier.IdentifierInterface;
import tr.edu.gsu.mataws.component.core.selector.simplifier.SimplifierInterface;

/**
 * General interface of the selector component.
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
	{	initIdentifiers();
		initSimplifiers();
	}

	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	/**
	 * Takes a list of strings and selects it, 
	 * which generally results in a single word.
	 * 
	 * @param strings
	 * 		The list of strings to process.
	 * @return
	 * 		The resulting {@link IdentifiedWord}, or {@code null} if the selection was inconclusive.
	 */
	public IdentifiedWord<T> select(List<String> strings)
	{	// init
		List<IdentifiedWord<T>> temp = new ArrayList<IdentifiedWord<T>>();
		for(String string: strings)
		{	IdentifiedWord<T> word = new IdentifiedWord<T>(string);
			temp.add(word);
		}
		
		// identification
		identify(temp);
		
		// simplification
		simplify(temp);
		
		// result
		IdentifiedWord<T> result = null;
		if(temp.size()>1)
			throw new IllegalArgumentException("Problem during selection: final word list should not contain more than one word.");
		else if(!temp.isEmpty())
			result = temp.get(0);
		
		return result;
	}
	
	///////////////////////////////////////////////////////////
	//	IDENTIFY							///////////////////
	///////////////////////////////////////////////////////////
	/** Sequence of identifiers applied as is */
	protected final List<IdentifierInterface<T>> identifiers = new ArrayList<IdentifierInterface<T>>();

	/**
	 * Initializes the sequence of identifiers.
	 */
	protected abstract void initIdentifiers();

	/**
	 * Applies the sequence of identifiers.
	 * 
	 * @param words
	 * 		List of words to be processed.
	 */
	protected void identify(List<IdentifiedWord<T>> words)
	{	for(IdentifierInterface<T> identifier: identifiers)
			identifier.identify(words);
	}
	
	///////////////////////////////////////////////////////////
	//	NORMALIZATION						///////////////////
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
	protected void simplify(List<IdentifiedWord<T>> words)
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
