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
 * Match previously identified parts of operation names
 * and parameters. Here, we suppose all parameters are 
 * both inputs and outputs. If only inputs or only outputs
 * could be identified in the operation name, and if the
 * numbers match, then each operation name part is used
 * to complete each parameter (both the input and output 
 * versions.) 
 *   
 * @author Vincent Labatut
 */
public class AllInOutMatcher extends AbstractMatcher<Synset>
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
		
		// compare input and output parameters
		boolean similar = true;
		Iterator<MatawsParameter> it1 = inParams.iterator();
		while(it1.hasNext() && similar)
		{	MatawsParameter parameter = it1.next();
			String name = parameter.getName();
			similar = outNames.contains(name);
		}
		Iterator<MatawsParameter> it2 = outParams.iterator();
		while(it2.hasNext() && similar)
		{	MatawsParameter parameter = it2.next();
			String name = parameter.getName();
			similar = inNames.contains(name);
		}
		
		// verify the situation is appropriate for this matcher: same parameters as inputs and outputs
		if(similar)
		{	// retrieve the operation part list 
			List<IdentifiedWord<Synset>> inList = operationMap.get(Way.IN);
			List<IdentifiedWord<Synset>> outList = operationMap.get(Way.OUT);
			List<IdentifiedWord<Synset>> list = null;
			if(inList.isEmpty() && !outList.isEmpty())
				list = outList;
			else if(!inList.isEmpty() && outList.isEmpty())
				list = inList;
			// verify the situation is appropriate for this matcher: only input or output parts were identified
			if(list!=null)
			{	// verify the situation is appropriate for this matcher: same number of parameters and parts
				if(list.size()==inParams.size())
				{	for(int i=0;i<list.size();i++)
					{	IdentifiedWord<Synset> word = list.get(i);
						MatawsParameter inParameter = inParams.get(i);
						if(inParameter.getRepresentativeWord()==null)
							inParameter.setRepresentativeWord(word);
						MatawsParameter outParameter = outParams.get(i);
						if(outParameter.getRepresentativeWord()==null)
							outParameter.setRepresentativeWord(word);
					}
				}
			}
		}
		
		return result;
	}
}
