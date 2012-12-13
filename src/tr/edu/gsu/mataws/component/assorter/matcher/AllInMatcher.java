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
import java.util.List;
import java.util.Map;

import edu.smu.tspell.wordnet.Synset;

import tr.edu.gsu.mataws.data.IdentifiedWord;
import tr.edu.gsu.mataws.data.MatawsParameter;
import tr.edu.gsu.sine.col.Way;

/**
 * Match previously identified parts of operation names
 * and parameters. Here, we suppose all parameters are 
 * inputs. If all the parts previously identified in the
 * operation name are also inputs, and if the number
 * match, then the parts are used to complete the parameters.
 *   
 * @author Vincent Labatut
 */
public class AllInMatcher extends AbstractMatcher<Synset>
{
	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	@Override
	public boolean match(Map<Way,List<IdentifiedWord<Synset>>> operationMap, List<MatawsParameter> parameters)
	{	boolean result = false;
		
		// distinguish parameters
		List<MatawsParameter> inParams = new ArrayList<MatawsParameter>();
		List<MatawsParameter> outParams = new ArrayList<MatawsParameter>();
		List<String> inNames = new ArrayList<String>();
		List<String> outNames = new ArrayList<String>();
		for(MatawsParameter parameter: parameters)
		{	Way way = parameter.getSineParameter().getWay();
			String name = parameter.getName();
			if(way==Way.IN)
			{	inParams.add(parameter);
				inNames.add(name);
			}
			else
			{	outParams.add(parameter);
				outNames.add(name);
			}
		}

		// verify the situation is appropriate for this matcher: all are inputs, none are outputs
		if(outParams.isEmpty() && !inParams.isEmpty())
		{	// verify the situation is appropriate for this matcher: the number of input parameters match
			List<IdentifiedWord<Synset>> inList = operationMap.get(Way.IN);
			if(inList.size()==inParams.size())
			{	// process each parameter
				for(int i=0;i<inList.size();i++)
				{	IdentifiedWord<Synset> word = inList.get(i);
					MatawsParameter parameter = inParams.get(i);
					if(parameter.getRepresentativeWord()==null)
						parameter.setRepresentativeWord(word);
				}
			}
		}
		
		return result;
	}
}
