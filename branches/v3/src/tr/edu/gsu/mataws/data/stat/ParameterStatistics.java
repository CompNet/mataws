package tr.edu.gsu.mataws.data.stat;

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

import tr.edu.gsu.mataws.data.parameter.MatawsParameter;
import tr.edu.gsu.sine.col.Operation;
import tr.edu.gsu.sine.col.Parameter;
import tr.edu.gsu.sine.col.Service;

/**
 * Represents the statistics processed for the
 * parameters of an annotated collection.
 * 
 * @author Vincent Labatut
 */
public class ParameterStatistics
{	
	/**
	 * Builds a new statistics object, without any values.
	 * 
	 * @param parameters
	 * 		List of parameters.
	 */
	public ParameterStatistics(List<MatawsParameter> parameters)
	{	
	}
	
	///////////////////////////////////////////////////////////
	//	FIELDS								///////////////////
	///////////////////////////////////////////////////////////
	public enum Field
	{	NAME{},
		TYPE_NAME,
		SUBPARAMETERS,
		REPRESENTATIVE_WORD,
		CONCEPT,
		P_VS_W,
		W_VS_C,
		P_VS_C
	}
	
	
	///////////////////////////////////////////////////////////
	//	DATA								///////////////////
	///////////////////////////////////////////////////////////
	/** The sine operation object containing the parameter */ 
	private Map<Map<MatawsParameter,Object>>
	
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
