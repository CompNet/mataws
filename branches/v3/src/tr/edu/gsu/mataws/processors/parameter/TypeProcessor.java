package tr.edu.gsu.mataws.processors.parameter;

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

import edu.smu.tspell.wordnet.Synset;

import tr.edu.gsu.mataws.component.selector.DefaultSelector;
import tr.edu.gsu.mataws.data.parameter.AbstractMatawsParameter;
import tr.edu.gsu.mataws.data.parameter.IdentifiedWord;
import tr.edu.gsu.mataws.data.parameter.MatawsSubParameter;
import tr.edu.gsu.mataws.tools.log.HierarchicalLogger;
import tr.edu.gsu.mataws.tools.log.HierarchicalLoggerManager;

/**
 * This class is in charge for processing the
 * structure of data types. It takes a parameter
 * as input (its names have supposedly been tested
 * before). It explores the structure of its data type,
 * provided it is a complex XSD type. It possibly
 * retrieves several representative words, which
 * are combined by means of a selector component.
 * 
 * @author Vincent Labatut
 */
public class TypeProcessor
{	
	/**
	 * Builds a standard type processor.
	 */
	public TypeProcessor()
	{	logger = HierarchicalLoggerManager.getHierarchicalLogger();
	
		subParameterProcessor = new SubParameterProcessor(this);
		selector = new DefaultSelector();
	}
	
	///////////////////////////////////////////////////////////
	//	PROCESS							///////////////////////
	///////////////////////////////////////////////////////////
	/** Logger */
	private HierarchicalLogger logger;
	/** Processor used to treat all child parameters */
	private SubParameterProcessor subParameterProcessor;
	/** Selector used to combine the resulting words */
	private DefaultSelector selector;
	
	/**
	 * Receives a parameter and takes advantage of its
	 * data type structure to extract a series of
	 * representative words. Those are then combined
	 * using a Selector, in order to tget a single 
	 * representative word. Finally, the parameter object
	 * is updated in order to contain this word.
	 * 
	 * @param parameter
	 * 		The parameter to process.
	 * @return
	 * 		{@code true} iff the processor could retrieve a representative 
	 * 		word for this parameter, thanks to its data type structure. 
	 */
	public boolean process(AbstractMatawsParameter parameter)
	{	logger.log("Process parameter type for "+parameter.getName()+"("+parameter.getTypeName()+")");
		logger.increaseOffset();
		boolean result = false;
		
		// process each children individually
		List<MatawsSubParameter> children = parameter.getChildren();
		List<IdentifiedWord<Synset>> words = new ArrayList<IdentifiedWord<Synset>>();
		logger.log("Process each subparameter individually");
		logger.increaseOffset();
		for(MatawsSubParameter child: children)
		{	// apply standard subparameter processing
			boolean res = subParameterProcessor.process(child);
			if(res)
			{	@SuppressWarnings("unchecked")
				IdentifiedWord<Synset> word = (IdentifiedWord<Synset>)child.getRepresentativeWord();
				words.add(word);
			}
		}
		logger.decreaseOffset();
		
		// simplifies the resulting list of words
		if(words.isEmpty())
			logger.log("Word list is empty, so no need for selection");
		else
		{	IdentifiedWord<Synset> representativeWord = selector.select(words);
			if(representativeWord!=null)
			{	result = true;
				// update the parameter
				parameter.setRepresentativeWord(representativeWord);
			}
		}
		
		logger.decreaseOffset();
		return result;
	}
}
