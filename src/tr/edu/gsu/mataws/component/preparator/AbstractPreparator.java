package tr.edu.gsu.mataws.component.preparator;

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
import java.util.List;

import tr.edu.gsu.mataws.component.preparator.filter.FilterInterface;
import tr.edu.gsu.mataws.component.preparator.identifier.IdentifierInterface;
import tr.edu.gsu.mataws.component.preparator.normalizer.NormalizerInterface;
import tr.edu.gsu.mataws.component.preparator.splitter.SplitterInterface;
import tr.edu.gsu.mataws.data.IdentifiedWord;

/**
 * Abstract class of the preparator component.
 * <br/>
 * Other preparators can be designed by using
 * different combinations of splitters, normalizers
 * filters and identifiers, and/or different splitters, 
 * normalizers, filters and identifiers.
 * 
 * @param <T> 
 *		Class used to represent a WordNet synset.
 *  
 * @author Koray Mancuhan
 * @author Cihan Aksoy
 * @author Vincent Labatut
 */
public abstract class AbstractPreparator<T>
{	
	/**
	 * Initializes all the necessary objects
	 * for this preparator.
	 */
	public AbstractPreparator()
	{	// init string-related preprocessing
		initSplitters();
		initNormalizers();
		initFilters();
		
		// init word-related preprocessing
		initIdentifiers();
	}

	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	/**
	 * Takes a string and prepare it, which 
	 * results in a list of identified words.
	 * 
	 * @param string
	 * 		Original string.
	 * @return
	 * 		The list of identified words resulting from its preprocessing.
	 */
	public List<IdentifiedWord<T>> preprocess(String string)
	{	// init
		List<String> strings = new ArrayList<String>();
		strings.add(string);
		
		// clean strings
		strings = split(strings);
		strings = normalize(strings);
		strings = filter(strings);
		
		// retrieve synsets
		List<IdentifiedWord<T>> result = identify(strings);
		
		return result;
	}
	
	///////////////////////////////////////////////////////////
	//	SPLIT								///////////////////
	///////////////////////////////////////////////////////////
	/** Sequence of splitters applied as is */
	protected final List<SplitterInterface> splitters = new ArrayList<SplitterInterface>();

	/**
	 * Initializes the sequence of splitters.
	 */
	protected abstract void initSplitters();

	/**
	 * Applies the sequence of splitters.
	 * 
	 * @param strings
	 * 		List of strings to be processed.
	 * @return
	 * 		Sequence of strings resulting from the split. 
	 */
	protected List<String> split(List<String> strings)
	{	List<String> result = new ArrayList<String>();
		for(SplitterInterface splitter: splitters)
		{	List<String> temp = splitter.split(strings);
			result.addAll(temp);
		}
		return result;
	}
	
	///////////////////////////////////////////////////////////
	//	NORMALIZATION						///////////////////
	///////////////////////////////////////////////////////////
	/** Sequence of normalizers applied as is */
	protected final List<NormalizerInterface> normalizers = new ArrayList<NormalizerInterface>();

	/**
	 * Initializes the sequence of normalizers.
	 */
	protected abstract void initNormalizers();

	/**
	 * Applies the sequence of normalizers.
	 * 
	 * @param strings
	 * 		List of strings to be processed.
	 * @return
	 * 		Sequence of strings resulting from the normalization. 
	 */
	protected List<String> normalize(List<String> strings)
	{	List<String> result = new ArrayList<String>();
		for(NormalizerInterface normalizer: normalizers)
		{	List<String> temp = normalizer.normalize(strings);
			result.addAll(temp);
		}
		return result;
	}
	
	///////////////////////////////////////////////////////////
	//	FILTERING							///////////////////
	///////////////////////////////////////////////////////////
	/** Sequence of filters applied as is */
	protected final List<FilterInterface> filters = new ArrayList<FilterInterface>();

	/**
	 * Initializes the sequence of filters.
	 */
	protected abstract void initFilters();
	
	/**
	 * Applies the sequence of filters.
	 * 
	 * @param strings
	 * 		List of strings to be processed.
	 * @return
	 * 		Sequence of strings resulting from the filtering. 
	 */
	protected List<String> filter(List<String> strings)
	{	List<String> result = new ArrayList<String>();
		for(FilterInterface filter: filters)
		{	List<String> temp = filter.filter(strings);
			result.addAll(temp);
		}
		return result;
	}

	///////////////////////////////////////////////////////////
	//	IDENTIFICATION						///////////////////
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
	 * @param strings
	 * 		List of strings to be processed.
	 * @return
	 * 		List of identified words resulting from the processing.
	 */
	protected List<IdentifiedWord<T>> identify(List<String> strings)
	{	// init
		List<IdentifiedWord<T>> result = new ArrayList<IdentifiedWord<T>>();
		for(String string: strings)
		{	IdentifiedWord<T> word = new IdentifiedWord<T>(string);
			result.add(word);
		}
		
		// identify
		for(IdentifierInterface<T> identifier: identifiers)
			identifier.identify(result);
		
		return result;
	}
}
