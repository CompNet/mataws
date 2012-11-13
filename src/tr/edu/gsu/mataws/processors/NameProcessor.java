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

import tr.edu.gsu.mataws.data.MatawsParameter;

/**
 * @author Vincent Labatut
 */
public class NameProcessor
{	
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
		List<String> strings = null;
		// TODO call to Preprocessor
		if(!strings.isEmpty())
		{	
			// select representative word
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
