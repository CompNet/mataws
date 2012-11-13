package tr.edu.gsu.mataws.data;

/*
 * Mataws - Multimodal Automatic Tool for the Annotation of Web Services
 * Copyright 2010 Cihan Aksoy and Koray Man�uhan
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

import tr.edu.gsu.sine.col.Operation;
import tr.edu.gsu.sine.col.Parameter;
import tr.edu.gsu.sine.col.Service;

/**
 * This class represents a parameter, in order
 * Mataws to perform specific processes.
 * 
 * @author Vincent Labatut
 * TODO note: message are ignored
 */
public class MatawsParameter extends AbstractMatawsParameter
{	
	/**
	 * Builds a new parameter, with connections
	 * to the corresponding objects generated by Sine.
	 * 
	 * @param parameter
	 * 		Sine object representing this parameter.
	 * @param operation
	 * 		Sine object representing the operation containing this parameter.
	 * @param service
	 * 		Sine object representing the service containing this parameter.
	 */
	public MatawsParameter(Parameter parameter, Operation operation, Service service)
	{	super(parameter);
		sineOperation = operation;
		sineService = service;
	}
	
	///////////////////////////////////////////////////////////
	//	OPERATION							///////////////////
	///////////////////////////////////////////////////////////
	/** The sine operation object containing the parameter */ 
	private Operation sineOperation;
	
	/**
	 * Returns the Sine object containing
	 * this parameter.
	 * 
	 * @return
	 * 		A Sine {@link Operation} object.
	 */
	public Operation getSineOperation() {
		return sineOperation;
	}

	///////////////////////////////////////////////////////////
	//	SERVICE								///////////////////
	///////////////////////////////////////////////////////////
	/** The sine service object containing the parameter */ 
	private Service sineService;
	
	/**
	 * Returns the Sine object containing
	 * this parameter.
	 * 
	 * @return
	 * 		A Sine {@link Service} object.
	 */
	public Service getSineService() {
		return sineService;
	}
}
