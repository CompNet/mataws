package tr.edu.gsu.mataws.component.contraster;

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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import tr.edu.gsu.mataws.component.contraster.breaker.BreakerInterface;
import tr.edu.gsu.mataws.component.preparator.AbstractPreparator;
import tr.edu.gsu.mataws.data.IdentifiedWord;
import tr.edu.gsu.mataws.data.MatawsParameter;
import tr.edu.gsu.sine.col.Operation;
import tr.edu.gsu.sine.col.Parameter;

/**
 * Abstract class of the associator component.
 *  
 * @param <T> 
 *		Class used to represent a WordNet synset.
 *
 * @author Vincent Labatut
 */
public abstract class AbstractContraster<T>
{	
	/**
	 * Initializes all the necessary objects
	 * for this contraster.
	 */
	public AbstractContraster()
	{	initBreakers();
	}

	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	/**
	 * Takes an operation (including its name and parameters), 
	 * and associates a concept to each parameter, using comparisons
	 * and patterns in operation names and parameters..
	 * 
	 * @param operation
	 * 		The operation to process.
	 * @return
	 * 		A list of parameters, some of which can be annotated.
	 */
	public List<MatawsParameter> contrast(Operation operation)
	{	List<MatawsParameter> result = new ArrayList<MatawsParameter>();
		
		// get the list of parameters
		List<Parameter> parameters = operation.getParameters();
		for(Parameter parameter: parameters)
		{	MatawsParameter p = new MatawsParameter(parameter);
			result.add(p);
		}
		
		// prepare the operation name
		List<IdentifiedWord<T>> operationList = preparate(operation);
		
		// apply the breakers
		breakk(operationList,result);
		
		return result;
	}
	
	///////////////////////////////////////////////////////////
	//	PREPARATOR							///////////////////
	///////////////////////////////////////////////////////////
	/** Preparator component used to split the operation name */
	protected AbstractPreparator<T> preparator;
	
	/**
	 * Initializes the preparator.
	 */
	protected abstract void initPreparator();
	
	/**
	 * Applies the preparator and splits the operation
	 * name into several substrigs.
	 * 
	 * @param operation
	 * 		The operation to process.
	 * @return
	 * 		The list of words resulting from the split.
	 */
	private List<IdentifiedWord<T>> preparate(Operation operation)
	{	String opName = operation.getName();
		List<IdentifiedWord<T>> result = preparator.preparate(opName);
		return result;
	}
	
	///////////////////////////////////////////////////////////
	//	MAP									///////////////////
	///////////////////////////////////////////////////////////
	/** Sequence of breakers applied as is */
	protected final List<BreakerInterface<T>> breakers = new ArrayList<BreakerInterface<T>>();

	/**
	 * Initializes the sequence of breakers.
	 */
	protected abstract void initBreakers();

	/**
	 * Applies the sequence of breakers.
	 * 
	 * @param operationList
	 * 		List of words composing the operation name.
	 * @param parameters
	 * 		The Mataws parameters, to be possibly modified depending on the success
	 * 		of the annotation process.
	 * @return
	 * 		{@code true} iff one parameter could be annotated. 
	 */
	protected boolean breakk(List<IdentifiedWord<T>> operationList, List<MatawsParameter> parameters)
	{	boolean result = false;
		
		Iterator<BreakerInterface<T>> it = breakers.iterator();
		while(it.hasNext() && !result)
		{	BreakerInterface<T> breaker = it.next();
			result = breaker.breakk(operationList,parameters);
		}
		
		return result;
	}
}
