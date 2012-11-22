package tr.edu.gsu.mataws.processors;

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

import tr.edu.gsu.mataws.component.preprocessor.DefaultPreprocessor;
import tr.edu.gsu.mataws.component.selector.DefaultSelector;
import tr.edu.gsu.mataws.data.AbstractMatawsParameter;
import tr.edu.gsu.mataws.data.IdentifiedWord;
import tr.edu.gsu.mataws.data.MatawsSubParameter;

/**
 * This processor is able to receive a subparameter
 * (i.e. a field from a complex XSD data type) and
 * retrieve one or several {@link IdentifiedWord} representing
 * it. Unlike in the {@link NameProcessor}, no associator is
 * required at this stage, because synsets are sufficients (no
 * concept is needed yet).
 * 
 * @author Vincent Labatut
 */
public class SubparameterProcessor
{	
	/**
	 * Builds a subparameter processor,
	 * based on defaults processors.
	 * 
	 */
	public SubparameterProcessor()
	{	preprocessor = new DefaultPreprocessor();
		selector = new DefaultSelector();
	}
	
	///////////////////////////////////////////////////////////
	//	PROCESS							///////////////////////
	///////////////////////////////////////////////////////////
	/** Preprocessor in charge of the first step */
	private static DefaultPreprocessor preprocessor;
	/** Selector in charge of the second step */
	private static DefaultSelector selector;

	/**
	 * Process a subparameter in order to extract its
	 * representative word, or {@code null} if none
	 * can be found.
	 * <br/>
	 * The processor first tries to use the parameter
	 * name, then its data type name, then its children
	 * (if it has a complext XSD data type).
	 * 
	 * @param subparameter
	 * 		The subparameter to process.
	 * @return
	 * 		A representative word, or {@code null} if none could be found.
	 */
	public static IdentifiedWord<Synset> process(AbstractMatawsParameter subparameter)
	{	// init
		IdentifiedWord<Synset> result = null;
		
		// first, try to take advantage of the subparameter name,
		// and possibly of its data type name
		String string[] = {subparameter.getName(),subparameter.getTypeName()};
		int i = 0;
		while(i<string.length && result==null)
		{	// perform preprocessing
			List<String> strings = preprocessor.preprocess(string[i]);
			if(!strings.isEmpty())
			{	// select representative word
				IdentifiedWord<Synset> representativeWord = selector.select(strings);
				if(representativeWord!=null)
				{	subparameter.setRepresentativeWord(representativeWord);
					result = representativeWord;
				}
			}
			i++;
		}
		
		// if it is unconclusive, then we take advantage of the data type itself
		if(result==null)
		{	// apply the same process to all children
			List<MatawsSubParameter> children = subparameter.getChildren();
			List<IdentifiedWord<Synset>> words = new ArrayList<IdentifiedWord<Synset>>();
			for(MatawsSubParameter child: children)
			{	IdentifiedWord<Synset> word = process(child);
				if(word!=null)
					words.add(word);
			}
			
			// simplifies the resulting list of words
			if(!words.isEmpty())
			{	selector.simplify(words);
				if(!words.isEmpty())
					result = words.get(0);
			}
		}
		
		return result;
	}
}
