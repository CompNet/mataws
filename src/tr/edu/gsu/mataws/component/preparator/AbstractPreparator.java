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

import tr.edu.gsu.mataws.component.preparator.filter.AbstractFilter;
import tr.edu.gsu.mataws.component.preparator.identifier.AbstractIdentifier;
import tr.edu.gsu.mataws.component.preparator.normalizer.AbstractNormalizer;
import tr.edu.gsu.mataws.component.preparator.splitter.AbstractSplitter;
import tr.edu.gsu.mataws.data.parameter.IdentifiedWord;
import tr.edu.gsu.mataws.tools.log.HierarchicalLogger;
import tr.edu.gsu.mataws.tools.log.HierarchicalLoggerManager;

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
	{	logger = HierarchicalLoggerManager.getHierarchicalLogger();
	
		// init string-related preprocessing
		initSplitters();
		initNormalizers();
		initFilters();
		
		// init word-related preprocessing
		initIdentifiers();
	}

	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	/** Logger */
	private HierarchicalLogger logger;

	/**
	 * Takes a string and prepares it, which 
	 * results in a list of identified words.
	 * 
	 * @param string
	 * 		Original string.
	 * @return
	 * 		The list of identified words resulting from its preprocessing.
	 */
	public List<IdentifiedWord<T>> preparate(String string)
	{	logger.log("Preparing string "+string);
		logger.increaseOffset();
		
		// init
		List<String> strings = new ArrayList<String>();
		strings.add(string);
		
		// clean strings
		strings = split(strings);
		strings = normalize(strings);
		strings = filter(strings);
		
		// retrieve synsets
		List<IdentifiedWord<T>> result = identify(strings);
		
		logger.decreaseOffset();
		return result;
	}
	
	///////////////////////////////////////////////////////////
	//	SPLIT								///////////////////
	///////////////////////////////////////////////////////////
	/** Sequence of splitters applied as is */
	protected final List<AbstractSplitter> splitters = new ArrayList<AbstractSplitter>();

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
	{	logger.log("Splitting strings");
		logger.increaseOffset();
		
		List<String> result = new ArrayList<String>();
		for(AbstractSplitter splitter: splitters)
		{	List<String> temp = splitter.split(strings);
			result.addAll(temp);
		}
		
		logger.decreaseOffset();
		return result;
	}
	
	///////////////////////////////////////////////////////////
	//	NORMALIZATION						///////////////////
	///////////////////////////////////////////////////////////
	/** Sequence of normalizers applied as is */
	protected final List<AbstractNormalizer> normalizers = new ArrayList<AbstractNormalizer>();

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
	{	logger.log("Normalizing strings");
		logger.increaseOffset();
		List<String> result = new ArrayList<String>();
		
		for(AbstractNormalizer normalizer: normalizers)
		{	List<String> temp = normalizer.normalize(strings);
			result.addAll(temp);
		}
		
		logger.decreaseOffset();
		return result;
	}
	
	///////////////////////////////////////////////////////////
	//	FILTERING							///////////////////
	///////////////////////////////////////////////////////////
	/** Sequence of filters applied as is */
	protected final List<AbstractFilter> filters = new ArrayList<AbstractFilter>();

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
	{	logger.log("Preparing strings");
		logger.increaseOffset();
		List<String> result = new ArrayList<String>();
		
		for(AbstractFilter filter: filters)
		{	List<String> temp = filter.filter(strings);
			result.addAll(temp);
		}
		
		logger.decreaseOffset();
		return result;
	}

	///////////////////////////////////////////////////////////
	//	IDENTIFICATION						///////////////////
	///////////////////////////////////////////////////////////
	/** Sequence of identifiers applied as is */
	protected final List<AbstractIdentifier<T>> identifiers = new ArrayList<AbstractIdentifier<T>>();

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
	{	logger.log("Identifying strings");
		logger.increaseOffset();
		
		// init
		List<IdentifiedWord<T>> result = new ArrayList<IdentifiedWord<T>>();
		for(String string: strings)
		{	IdentifiedWord<T> word = new IdentifiedWord<T>(string);
			result.add(word);
		}
		
		// identify
		for(AbstractIdentifier<T> identifier: identifiers)
			identifier.identify(result);
		
		logger.decreaseOffset();
		return result;
	}
}
