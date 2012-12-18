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

import edu.smu.tspell.wordnet.Synset;

import tr.edu.gsu.mataws.data.IdentifiedWord;
import tr.edu.gsu.mataws.data.MatawsParameter;
import tr.edu.gsu.sine.col.Way;

/**
 * Match previously identified parts of operation names
 * and parameters. Here, we suppose there are only two
 * parameters, one is the input and the other the output.
 * If two parts (one input, one output) were also retrieved
 * from the operation name, then those are used to complete
 * the parameters. 
 *   
 * @author Vincent Labatut
 */
public class OneInOneOutMatcher extends AbstractMatcher<Synset>
{
	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	@Override
	public boolean match(Map<Way,List<IdentifiedWord<Synset>>> operationMap, List<MatawsParameter> parameters)
	{	logger.log("Matching operation parts and parameters supposing there is one single input and one single output");
		logger.increaseOffset();
		boolean result = false;
		
		// verify the situation is appropriate for this matcher: exactly 2 parameters
		if(parameters.size()==2)
		{	// display the operation parts
			logger.log("Previously detected operation parts:");
			logger.increaseOffset();
			for(Way way: Way.values())
			{	logger.log("Way: "+way);
				List<IdentifiedWord<Synset>> list = operationMap.get(way);
				logger.increaseOffset();
				for(IdentifiedWord<Synset> word: list)
					logger.log(word.toString());
				logger.decreaseOffset();
			}
			logger.decreaseOffset();
			
			// distinguish parameters
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
			{	// display parameters
				logger.log("Identifying in and out parameters :");
				logger.increaseOffset();
				logger.log("Input parameter: "+inParam.getTypeName()+" "+inParam.getName());
				logger.log("Output parameter: "+outParam.getTypeName()+" "+outParam.getName());
				logger.decreaseOffset();
				
				String inName = inParam.getName();
				String outName = outParam.getName();
				// verify the situation is appropriate for this matcher: two different parameters
				if(!inName.equals(outName))
				{	logger.log("Updating parameters");
					// TODO here we could try to compare the new words and the possibly previously retrieved ones (when processing parameters independtly)
					// process the input parameter
					List<IdentifiedWord<Synset>> inList = operationMap.get(Way.IN);
					if(!inList.isEmpty())
					{	IdentifiedWord<Synset> inWord = inList.get(0);
						if(inParam.getRepresentativeWord()==null)
							inParam.setRepresentativeWord(inWord);
					}
					// process the output parameter
					List<IdentifiedWord<Synset>> outList = operationMap.get(Way.OUT);
					if(!outList.isEmpty())
					{	IdentifiedWord<Synset> outWord = outList.get(0);
						if(outParam.getRepresentativeWord()==null)
							outParam.setRepresentativeWord(outWord);
					}
				}
				else
					logger.log("Both input and output parameters are the same >> stopping here");
			}
			else
				logger.log("The distribution of input/output parameters is not compatible with this matcher >> stopping here");
		}
		else
			logger.log("The number of parameters is not compatible with this matcher >> stopping here");
				
		logger.decreaseOffset();
		return result;
	}
}
