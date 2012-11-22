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

import tr.edu.gsu.mataws.component.preprocessor.DefaultPreprocessor;
import tr.edu.gsu.mataws.component.selector.DefaultSelector;
import tr.edu.gsu.mataws.data.AbstractMatawsParameter;

/**
 * This class is in charge for processing
 * parameter and type names. It first preprocess
 * them, then apply a selector, and possibly
 * an associator to get the concept associated
 * to the original name.
 * 
 * @author Vincent Labatut
 */
public abstract class AbstractNameProcessor
{	
	/**
	 * Builds a name processor.
	 */
	public AbstractNameProcessor()
	{	preprocessor = new DefaultPreprocessor();
		selector = new DefaultSelector();
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
	protected enum Mode
	{	/** Process the parameter name */
		PARAMETER,
		/** Process the data type name */
		TYPE;
	}
	
	///////////////////////////////////////////////////////////
	//	PROCESS							///////////////////////
	///////////////////////////////////////////////////////////
	/** Preprocessor in charge of the first step */
	protected static DefaultPreprocessor preprocessor;
	/** Selector in charge of the second step */
	protected static DefaultSelector selector;

	/**
	 * Applies the name process procedure to the specified parameter,
	 * using its parameter name and possibly data type name.
	 * <br/>
	 * The method returns a {@code boolean} indicating whether or not
	 * the parameter could be successfully processed.
	 * 
	 * @param parameter
	 * 		The parameter to process.
	 * @return
	 * 		{@code true} iff the name could be successfully processed.
	 */
	public boolean process(AbstractMatawsParameter parameter)
	{	boolean result = process(parameter,Mode.PARAMETER);
		if(!result)
			process(parameter,Mode.TYPE);
		return result;
	}
	
	/**
	 * Applies the name process procedure to the specified parameter.
	 * Depending on the mode parameter, the process will be applied
	 * either to the parameter name, or to its data type name.
	 * <br/>
	 * The method returns a {@code boolean} indicating whether or not
	 * the name could be successfully processed.
	 * 
	 * @param parameter
	 * 		The parameter to process.
	 * @param mode
	 * 		Mode of the processing (parameter name or data type name).
	 * @return
	 * 		{@code true} iff the name could be successfully processed.
	 */
	protected abstract boolean process(AbstractMatawsParameter parameter, Mode mode);
}
