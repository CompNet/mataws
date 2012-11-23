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

import tr.edu.gsu.mataws.data.MatawsParameter;
import tr.edu.gsu.mataws.processors.parameter.ParameterProcessor;
import tr.edu.gsu.sine.col.Collection;
import tr.edu.gsu.sine.col.Operation;

/**
 * This class takes advantage of an operation name to
 * analyze the names of its parameter. Some operation names
 * have a certain form, which gives a hint about the meaning
 * of its parameters. 
 * <br/>
 * For instance, for an operation whose name is 
 * {@code celsiusToFahrenheit} and parameters are {@code inParam}
 * and {@code outParam}, we can suppose the first parameter 
 * represents Celsisus degrees while the second is Fahrenheit degrees.
 * 
 * @author Vincent Labatut
 */
public class OperationProcessor
{	
	/**
	 * Builds a standard operation processor.
	 */
	public OperationProcessor()
	{	
	}
	
	///////////////////////////////////////////////////////////
	//	PROCESS							///////////////////////
	///////////////////////////////////////////////////////////
	public List<MatawsParameter> process(Operation operation)
	{	List<MatawsParameter> result = new ArrayList<MatawsParameter>();
		
		// process the operation name
		
		// return results
		return result;
	}
}
