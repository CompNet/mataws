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

import tr.edu.gsu.mataws.data.AbstractMatawsParameter;
import tr.edu.gsu.mataws.data.IdentifiedWord;

/**
 * This class is in charge for processing
 * subparameter and type names. It first preprocess
 * them, and then apply a selector, to get a 
 * single representative word associated
 * to the original name. So the difference with
 * the full parameter {@link NameProcessor} is
 * the fact we do not need an associator here.
 * 
 * @author Vincent Labatut
 */
public class SubNameProcessor extends AbstractNameProcessor
{	
	///////////////////////////////////////////////////////////
	//	PROCESS							///////////////////////
	///////////////////////////////////////////////////////////
	@Override
	protected boolean process(AbstractMatawsParameter parameter, Mode mode)
	{	boolean result = false;
	
		// init initial string
		String string;
		if(mode==Mode.PARAMETER)
		{	string = parameter.getName();
			logger.log("Process the parameter name "+string);
		}
		else
		{	string = parameter.getTypeName();
			logger.log("Process the parameter data type name "+string);
		}
		logger.increaseOffset();
		
		// perform preprocessing
		List<IdentifiedWord<Synset>> words = preprocessor.preparate(string);
		if(words.isEmpty())
			logger.log("Word list is empty, so no need for selection");
		else
		{	// select representative word
			IdentifiedWord<Synset> representativeWord = selector.select(words);
			if(representativeWord!=null)
			{	parameter.setRepresentativeWord(representativeWord);
				result = true;
			}
		}
		
		logger.decreaseOffset();
		return result;
	}
}
