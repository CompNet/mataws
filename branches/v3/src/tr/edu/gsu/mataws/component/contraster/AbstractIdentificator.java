package tr.edu.gsu.mataws.component.contraster;

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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import tr.edu.gsu.mataws.component.contraster.matcher.MatcherInterface;
import tr.edu.gsu.mataws.component.indentificator.breaker.BreakerInterface;
import tr.edu.gsu.mataws.data.IdentifiedWord;
import tr.edu.gsu.mataws.data.MatawsParameter;
import tr.edu.gsu.sine.col.Way;

/**
 * Abstract class of the identificator component.
 *  
 * @param <T> 
 *		Class used to represent a WordNet synset.
 *
 * @author Vincent Labatut
 */
public abstract class AbstractIdentificator<T>
{	
	/**
	 * Initializes all the necessary objects
	 * for this identificator.
	 */
	public AbstractIdentificator()
	{	initMatchers();
	}

	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	/**
	 * Takes an operation (including its name and parameters), 
	 * and associates a concept to each parameter, using comparisons
	 * and patterns in operation names and parameters.
	 * 
	 * @param operationName
	 * 		A list of the words composing the operation name.
	 * @param parameters
	 * 		A list of parameters, some of which can be annotated.
	 * @return
	 * 		{@code true} iff at least one parameter could be annotated.
	 */
	public boolean identify(List<IdentifiedWord<T>> operationName, List<MatawsParameter> parameters)
	{	// apply the breakers
		boolean result = breakk(operationName,parameters);
		
		return result;
	}
	
	///////////////////////////////////////////////////////////
	//	MATCH						///////////////////////////
	///////////////////////////////////////////////////////////
	/** Sequence of matchers applied as is */
	protected final List<MatcherInterface<T>> matchers = new ArrayList<MatcherInterface<T>>();

	/**
	 * Initializes the sequence of matchers.
	 */
	protected abstract void initMatchers();

	/**
	 * Applies the sequence of matchers.
	 * 
	 * @param operationMap
	 * 		Maps of words composing the operation name.
	 * @param parameters
	 * 		The Mataws parameters, to be possibly modified depending on the success
	 * 		of the annotation process.
	 * @return
	 * 		{@code true} iff one parameter could be annotated. 
	 */
	protected boolean match(Map<Way,List<IdentifiedWord<T>>> operationMap, List<MatawsParameter> parameters)
	{	boolean result = false;
		
		Iterator<MatcherInterface<T>> it = matchers.iterator();
		while(it.hasNext() && !result)
		{	MatcherInterface<T> matcher = it.next();
			result = matcher.match(operationMap,parameters) || result;
		}
		
		return result;
	}
}
