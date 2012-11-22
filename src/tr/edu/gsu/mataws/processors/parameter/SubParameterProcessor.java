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

import tr.edu.gsu.mataws.component.preparator.DefaultPreprocessor;
import tr.edu.gsu.mataws.component.selector.DefaultSelector;
import tr.edu.gsu.mataws.data.AbstractMatawsParameter;
import tr.edu.gsu.mataws.data.IdentifiedWord;
import tr.edu.gsu.mataws.data.MatawsSubParameter;
import tr.edu.gsu.mataws.processors.name.NameProcessor;
import tr.edu.gsu.mataws.processors.name.SubNameProcessor;

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
public class SubParameterProcessor
{	
	/**
	 * Builds a subparameter processor,
	 * based on defaults processors.
	 * 
	 */
	public SubParameterProcessor(TypeProcessor typeProcessor)
	{	nameProcessor = new SubNameProcessor();
		this.typeProcessor = typeProcessor;
	}
	
	///////////////////////////////////////////////////////////
	//	PROCESS							///////////////////////
	///////////////////////////////////////////////////////////
	/** Processor used to treat the parameter and data type names */
	private SubNameProcessor nameProcessor;
	/** Processor used to treat the data type structure */
	private TypeProcessor typeProcessor;
	
	/**
	 * Process a subparameter in order to extract its
	 * representative word, or {@code null} if none
	 * can be found.
	 * <br/>
	 * The processor first tries to use the parameter
	 * name, then its data type name, then its children
	 * (if it has a complext XSD data type).
	 * 
	 * @param subParameter
	 * 		The subparameter to process.
	 * @return
	 * 		A representative word, or {@code null} if none could be found.
	 */
	@SuppressWarnings("unchecked")
	public IdentifiedWord<Synset> process(MatawsSubParameter subParameter)
	{	// init
		IdentifiedWord<Synset> result = null;
		
		// first, try to take advantage of the subparameter name,
		// and possibly of its data type name
		boolean res = nameProcessor.process(subParameter);
		if(res)
			result = (IdentifiedWord<Synset>)subParameter.getRepresentativeWord();
		
		// if it is unconclusive, then we take advantage of the data type itself
		else
		{	typeProcessor.process(subParameter);
			
		}
		
		return result;
	}
}
