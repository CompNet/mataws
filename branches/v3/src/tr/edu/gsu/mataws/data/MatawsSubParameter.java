package tr.edu.gsu.mataws.data;

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
public class MatawsSubParameter extends AbstractMatawsParameter
{	
	/**
	 */
	public MatawsSubParameter(Parameter parameter, AbstractMatawsParameter parent)
	{	super(parameter);
		
		// init this object fields
		this.parent = parent;
		
	}
	
	///////////////////////////////////////////////////////////
	//	PARENT								///////////////////
	///////////////////////////////////////////////////////////
	/** The parameter containing this subparameter */ 
	private AbstractMatawsParameter parent;
	
	/**
	 * Returns the parameter containing this subparameter.
	 * 
	 * @return
	 * 		A {@link MatawsParameter} object containing this subparameter.
	 */
	public AbstractMatawsParameter getParent()
	{	return parent;
	}
}
