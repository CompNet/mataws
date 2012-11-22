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

import tr.edu.gsu.mataws.data.IdentifiedWord;
import tr.edu.gsu.mataws.data.MatawsParameter;
import tr.edu.gsu.mataws.data.MatawsSubParameter;

/**
 * @author Vincent Labatut
 */
public class TypeProcessor
{	
	public TypeProcessor()
	{	subParameterProcessor = new SubParameterProcessor(this);
		
	}
	
	///////////////////////////////////////////////////////////
	//	PROCESS							///////////////////////
	///////////////////////////////////////////////////////////
	/** Processor used to treat all child parameters */
	private SubParameterProcessor subParameterProcessor;
	/** Selector used to combine the resulting words */
	private 
	
	public void process(MatawsParameter parameter)
	{	// process each children individually
		List<MatawsSubParameter> children = parameter.getChildren();
		List<IdentifiedWord<Synset>> words = new ArrayList<IdentifiedWord<Synset>>();
		for(MatawsSubParameter child: children)
		{	// apply standard subparameter processing
			IdentifiedWord<Synset> word = subParameterProcessor.process(child);
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
}
