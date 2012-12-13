package tr.edu.gsu.mataws.component.assorter.matcher;

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
import java.util.Map;

import tr.edu.gsu.mataws.component.indentificator.breaker.AbstractBreaker;
import tr.edu.gsu.mataws.data.IdentifiedWord;
import tr.edu.gsu.mataws.data.MatawsParameter;
import tr.edu.gsu.sine.col.Way;

/**
 * Interface for classes in charge of matching 
 * parts of operation names and parameters.
 * Used when the parameter names and types do
 * not convey enough information to allow a
 * more direct annotation.
 * 
 * @param <T> 
 *		Class used to represent a WordNet synset.
 * 
 * @author Vincent Labatut
 */
public abstract class AbstractMatcher<T>
{
	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	/**
	 * Takes a map coming from a {@link AbstractBreaker}, i.e.
	 * associating identified words to an operation name,
	 * and tries to match those words to the parameters.
	 * <br/>
	 * Depending on how the matching process went, parameters
	 * in the received list are updated.
	 * 
	 * @param operationMap
	 * 		The mapping of identified words to parts of the operation name. 
	 * @param parameters
	 * 		The parameters to annotate. 
	 * @return
	 * 		{@code true} iff the method could match at least one parameter.
	 */
	public abstract boolean match(Map<Way,List<IdentifiedWord<T>>> operationMap, List<MatawsParameter> parameters);
}
