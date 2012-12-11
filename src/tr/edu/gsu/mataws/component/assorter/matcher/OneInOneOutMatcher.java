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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.smu.tspell.wordnet.Synset;

import tr.edu.gsu.mataws.data.IdentifiedWord;
import tr.edu.gsu.mataws.data.MatawsParameter;
import tr.edu.gsu.sine.col.Way;

/**
 * Anlyzes the name of a modification method, using some predefined
 * patterns to identify parameter meanings.
 * <br/>
 * For example, the {@code "addStudentToDepartment"} method should have
 * two input parameters representing a student and a department.
 *   
 * @author Vincent Labatut
 */
public class OneInOneOutMatcher implements MatcherInterface<Synset>
{
	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	@Override
	public boolean match(Map<Way,List<IdentifiedWord<Synset>>> operationMap, List<MatawsParameter> parameters)
	{	boolean result = false;
		
		// verify the situation is appropriate for this matcher: exactly 2 parameters
		if(parameters.size()==2)
		{	// distinguish parameters
			MatawsParameter inParam = null;
			MatawsParameter outParam = null;
			for(int i=0;i<2;i++)
			{	MatawsParameter parameter = parameters.get(i);
				Way way = parameter.getSineParameter().getWay();
				if(way==Way.IN)
					inParam = parameter;
				else
					outParam = parameter;
			}
			
			// verify the situation is appropriate for this matcher: one in and one out parameters
			if(inParam!=null && outParam!=null)
			{	String inName = inParam.getName();
				String outName = outParam.getName();
				// verify the situation is appropriate for this matcher: two different parameters
				if(!inName.equals(outName))
				{	List<IdentifiedWord<Synset>> inList = operationMap.get(Way.IN);
					inParam.set
					List<IdentifiedWord<Synset>> outList = operationMap.get(Way.OUT);
					
				}
			}
			
			// si deux parametres : facile, on fait en fonction de in/out
			// si quatre paramètres (doublons in/out) : faut déterminer lequel est lequel
			//		1) s'il manque le concept pr les deux >> on ne peut rien faire
			//		2) s'il y a un concept pr l'un des deux >> distance avec les deux concepts trouvés ici ?
			//			(>> besoin de n'avoir qu'un seul mot rep dès le breaker, et non pas en remontant au contraster)
		}
		
		return result;
	}
}
