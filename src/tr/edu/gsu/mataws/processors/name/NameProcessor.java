package tr.edu.gsu.mataws.processors.name;

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

import edu.smu.tspell.wordnet.Synset;

import tr.edu.gsu.mataws.component.associator.DefaultAssociator;
import tr.edu.gsu.mataws.data.AbstractMatawsParameter;
import tr.edu.gsu.mataws.data.IdentifiedWord;

/**
 * This class is in charge for processing
 * parameter and type names. It first preprocess
 * them, then apply a selector, and finally
 * an associator to get the concept associated
 * to the original name.
 * 
 * @author Vincent Labatut
 */
public class NameProcessor extends AbstractNameProcessor
{	
	/**
	 * Builds a name processor.
	 */
	public NameProcessor()
	{	super();
		associator = new DefaultAssociator();
	}
	
	///////////////////////////////////////////////////////////
	//	PROCESS							///////////////////////
	///////////////////////////////////////////////////////////
	/** Associator in charge of the third step */
	private static DefaultAssociator associator;
	
	@Override
	protected boolean process(AbstractMatawsParameter parameter, Mode mode)
	{	logger.increaseOffset();
		boolean result = false;
	
		// init initial string
		String string;
		if(mode==Mode.PARAMETER)
			string = parameter.getName();
		else
			string = parameter.getTypeName();
	
		// perform preprocessing
		List<IdentifiedWord<Synset>> words = preprocessor.preparate(string);
		if(!words.isEmpty())
		{	// select representative word
			IdentifiedWord<Synset> representativeWord = selector.select(words);
			if(representativeWord!=null)
			{	parameter.setRepresentativeWord(representativeWord);
				// associate concept
				String concept = associator.associate(representativeWord);
				if(concept!=null)
				{	result = true;
					parameter.setConcept(concept);
				}
			}
		}
		
		logger.decreaseOffset();
		return result;
	}
}
