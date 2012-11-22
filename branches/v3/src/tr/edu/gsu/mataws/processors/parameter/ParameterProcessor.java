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

import tr.edu.gsu.mataws.data.MatawsParameter;
import tr.edu.gsu.mataws.processors.name.NameProcessor;

/**
 * This processor is able to receive a parameter and
 * retrieve the associated concept. It takes advantage
 * of its name, then the name of its data type, then
 * the structure of its data type.
 * 
 * @author Vincent Labatut
 */
public class ParameterProcessor
{	
	/**
	 * Builds a standard parameter processor.
	 */
	public ParameterProcessor()
	{	nameProcessor = new NameProcessor();
		typeProcessor = new TypeProcessor();
		subParameterProcessor = new SubParameterProcessor(typeProcessor);
	}
	
	///////////////////////////////////////////////////////////
	//	PROCESS							///////////////////////
	///////////////////////////////////////////////////////////
	/** Processor used to treat the parameter and data type names */
	private NameProcessor nameProcessor;
	/** Processor used to treat the data type structure */
	private TypeProcessor typeProcessor;
	/** Processor used to treat the fields constituting the data type structure */
	private SubParameterProcessor subParameterProcessor;

	/**
	 * Process a parameter in order to extract its concept.
	 * <br/>
	 * The processor first tries to use the parameter
	 * name, then its data type name, then its children
	 * (if it has a complex XSD data type).
	 * 
	 * @param parameter
	 * 		The parameter to process.
	 * @return
	 * 		{@code true} iff a concept could be found for the parameter.
	 */
	public boolean process(MatawsParameter parameter)
	{	// first, try to take advantage of the subparameter name,
		// and possibly of its data type name
		boolean result = nameProcessor.process(parameter);
		
		// if it is unconclusive, then we take advantage of the data type itself
		if(!result)
			result = typeProcessor.process(parameter);

		return result;
	}
}
