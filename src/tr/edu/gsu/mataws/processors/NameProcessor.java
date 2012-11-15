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

import java.util.List;

import tr.edu.gsu.mataws.component.core.preprocessor.AbstractPreprocessor;
import tr.edu.gsu.mataws.component.core.preprocessor.DefaultPreprocessor;
import tr.edu.gsu.mataws.data.MatawsParameter;

/**
 * This class is in charge for processing
 * parameter and type names. It first preprocess
 * them, then apply a selector, and finally
 * an associator to get the concept associated
 * to the original name.
 * 
 * @author Vincent Labatut
 */
public class NameProcessor
{	
	/**
	 * Construit un processeur de nom.
	 * 
	 */
	public NameProcessor()
	{	preprocessor = new DefaultPreprocessor();
		
		
	}
	
	///////////////////////////////////////////////////////////
	//	MODE								///////////////////
	///////////////////////////////////////////////////////////
	/**
	 * Represents the functioning mode
	 * of this processing. The focus is either
	 * on the parameter name, or data type name.
	 * 
	 * @author Vincent Labatut
	 */
	public enum Mode
	{	/** Process the parameter name */
		PARAMETER,
		/** Process the data type name */
		TYPE;
	}
	
	///////////////////////////////////////////////////////////
	//	PROCESS							///////////////////////
	///////////////////////////////////////////////////////////
	/** Preprocessor in charge of the first step */
	private static AbstractPreprocessor preprocessor;
	
	// mode=parameter or type
	// returns true if one concept could be retrieved
	public static boolean process(MatawsParameter parameter, Mode mode)
	{	boolean result = false;
	
		// init initial string
		String string;
		if(mode==Mode.PARAMETER)
			string = parameter.getName();
		else
			string = parameter.getTypeName();
	
		// perform preprocessing
		List<String> strings = preprocessor.preprocess(string);
		if(!strings.isEmpty())
		{	// select representative word
			String representativeWord = null;
			// TODO call to Selector
			if(representativeWord!=null)
			{	parameter.setRepresentativeWord(representativeWord);
				// associate concept
				String concept = null;
				// TODO call to Associator
				if(concept!=null)
				{	result = true;
					parameter.setRepresentativeWord(representativeWord);
				}
			}
		}
		
		return result;
	}
}
