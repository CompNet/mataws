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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import tr.edu.gsu.mataws.data.parameter.MatawsParameter;
import tr.edu.gsu.mataws.tools.misc.CollectionTools;
import tr.edu.gsu.sine.col.Parameter;

/**
 * Represents the statistcs processed on the collection.
 * It might require some manual processing, especially for
 * the the qualitative evaluation.
 * 
 * @author Vincent Labatut
 */
public class CollectionStats
{	
	/**
	 * Builds an empty collection stat object. It is destined
	 * to be filled using the content of a file.
	 */
	public CollectionStats()
	{	// nothing special to do in this case
	}
	
	/**
	 * Builds a full collection stat object.
	 * It is destined to be used on an existing
	 * parameter list.
	 * 
	 * @param parameters
	 * 		List of actual parameters.
	 */
	public CollectionStats(List<MatawsParameter> parameters)
	{	// init
		List<MatawsParameter> uniques = new ArrayList<MatawsParameter>();
		
		// process each actual parameter
		for(MatawsParameter parameter: parameters)
		{	Parameter p1 = parameter.getSineParameter();
		
			// check if a similar one has already been processed 
			int index = 0;
			boolean found = false;
			while(index<uniques.size() && !found)
			{	MatawsParameter temp = uniques.get(index);
				Parameter p2 = temp.getSineParameter();
				found = CollectionTools.areEqualParameters(p1, p2, compareTypes, compareOperations);
				index++;
			}
			// if it is the case, then the number of occurrences is increased
			if(found)
			{	index--;
				ParameterStats ps = parameterUniques.get(index);
				int occurrences = ps.getOccurrences();
				ps.setOccurrences(occurrences+1);
			}
			
			// otherwise, the corresponding stat object is created
			else
			{	ParameterStats paramStats = new ParameterStats(parameter);
				parameterUniques.add(paramStats);
				uniques.add(parameter);
			}
		}
		
		// initializes the other lists
		initUniqueFromInstanceParam();
		initWordFromParams();
	}
	
	///////////////////////////////////////////////////////////
	//	COMPARISON MODE					///////////////////////
	///////////////////////////////////////////////////////////
	/** Determines if the comparisons should take data types into account */
	private boolean compareTypes = true;
	/** Determines if the comparisons should take operation names into account */
	private boolean compareOperations = true;
	
	/**
	 * Changes the way parameters are compared,
	 * so that types are taken into account.
	 * 
	 * @param compareTypes
	 * 		Indicates whether types should be considered.
	 */
	public void setCompareTypes(boolean compareTypes)
	{	this.compareTypes = compareTypes;
	}

	/**
	 * Changes the way parameters are compared,
	 * so that operations are taken into account.
	 * 
	 * @param compareOperations
	 * 		Indicates whether operations should be considered.
	 */
	public void setCompareOperations(boolean compareOperations)
	{	this.compareOperations = compareOperations;
	}

	///////////////////////////////////////////////////////////
	//	PARAMETERS						///////////////////////
	///////////////////////////////////////////////////////////
	/** List of parameter instances statistics */ 
	private List<ParameterStats> parameterInstances = new ArrayList<ParameterStats>();
	/** List of unique parameters statistics */ 
	private List<ParameterStats> parameterUniques = new ArrayList<ParameterStats>();
	/**
	 * Returns the list of parameter instances
	 * statistics for the whole collection.
	 * 
	 * @return
	 * 		A list of parameter statistics.
	 */
	public List<ParameterStats> getParameterInstanceStats()
	{	return parameterInstances;
	}

	/**
	 * Returns the list of unique parameter 
	 * statistics for the whole collection.
	 * 
	 * @return
	 * 		A list of parameter statistics.
	 */
	public List<ParameterStats> getParameterUniqueStats()
	{	return parameterUniques;
	}
	
	/**
	 * Initializes the list of unique parameter statistics
	 * using the (aleady initialized) list of 
	 * parameter instance statistics.
	 */
	public void initUniqueFromInstanceParam()
	{	for(ParameterStats param: parameterInstances)
		{	int occurrences = param.getOccurrences();
			for(int i=0;i<occurrences;i++)
			{	ParameterStats ps = param.copy();
				ps.setOccurrences(1);
				parameterUniques.add(ps);
			}
		}
	}
	
	///////////////////////////////////////////////////////////
	//	WORDS						///////////////////////////
	///////////////////////////////////////////////////////////
	/** List of word instances statistics */
	private List<WordStats> wordInstances = new ArrayList<WordStats>();
	/** List of unique words statistics */
	private List<WordStats> wordUniques = new ArrayList<WordStats>();
	
	/**
	 * Returns the list of word instances
	 * statistics for the whole collection.
	 * 
	 * @return
	 * 		A list of word statistics.
	 */
	public List<WordStats> getWordInstanceStats()
	{	return wordInstances;
	}
	
	/**
	 * Returns the list of unique word
	 * statistics for the whole collection.
	 * 
	 * @return
	 * 		A list of word statistics.
	 */
	public List<WordStats> getWordUniqueStats()
	{	return wordUniques;
	}
	
	/**
	 * Initializes the list of word statistics
	 * using the (aleady initialized) list of 
	 * parameter statistics.
	 */
	public void initWordFromParams()
	{	// create one unique word for each unique parameter
		for(ParameterStats parameterStat: parameterUniques)
		{	WordStats wordStat = new WordStats();
			
			// word
			String word = parameterStat.getRepresentativeWord();
			wordStat.setWord(word);
			
			// concept
			String concept = parameterStat.getConcept();
			wordStat.setConcept(concept);

			// occurrences
			int occurrences = parameterStat.getOccurrences();
			wordStat.setOccurrences(occurrences);
			
			// annotated
			boolean annotated = parameterStat.isAnnotated();
			wordStat.setAnnotated(annotated);
			
			// grades
			float pVsW = parameterStat.getPvsW();
			float wVsC = parameterStat.getWvsC();
			float pVsC = parameterStat.getPvsC();
			wordStat.setPvsW(pVsW);
			wordStat.setWvsC(wVsC);
			wordStat.setPvsC(pVsC);
		}
		
		// group parameters by representative word
		Map<String,List<ParameterStats>> map = new HashMap<String, List<ParameterStats>>();
		for(ParameterStats parameterStat: parameterInstances)
		{	String repWord = parameterStat.getRepresentativeWord();
			
			// complete map
			List<ParameterStats> list = map.get(repWord);
			if(list==null)
			{	list = new ArrayList<ParameterStats>();
				map.put(repWord, list);
			}
			list.add(parameterStat);
		}
		
		// insert in the word statistic list
		TreeSet<String> words =  new TreeSet<String>(map.keySet());
		for(String word: words)
		{	WordStats wordStat = new WordStats();
			List<ParameterStats> list = map.get(word);
			ParameterStats first = list.get(0);
			
			// word
			wordStat.setWord(word);
			
			// concept
			String concept = first.getConcept();
			wordStat.setConcept(concept);
for(ParameterStats s: list)
{	if(!s.getConcept().equals(concept))
		System.err.println("All those params should have the same concept");
}
			// occurrences
			int occurrences = 0;
			for(ParameterStats s: list)
			{	int temp = s.getOccurrences();
				occurrences = occurrences + temp;
			}
			wordStat.setOccurrences(occurrences);
			
			// annotated
			boolean annotated = first.isAnnotated();
			wordStat.setAnnotated(annotated);
			
			// grades
			float totalPvsW = 0;
			float totalWvsC = 0;
			float totalPvsC = 0;
			for(ParameterStats s: list)
			{	int nbr = s.getOccurrences();
				float pVsW = s.getPvsW();
				float wVsC = s.getWvsC();
				float pVsC = s.getPvsC();
				totalPvsW = totalPvsW + pVsW*nbr;
				totalWvsC = totalWvsC + wVsC*nbr;
				totalPvsC = totalPvsC + pVsC*nbr;
			}
			totalPvsW = totalPvsW / occurrences;
			totalWvsC = totalWvsC / occurrences;
			totalPvsC = totalPvsC / occurrences;
			wordStat.setPvsW(totalPvsW);
			wordStat.setWvsC(totalWvsC);
			wordStat.setPvsC(totalPvsC);
			
			wordInstances.add(wordStat);
		}
	}

	///////////////////////////////////////////////////////////
	//	QUANTITATIVE EVALUATION		///////////////////////////
	///////////////////////////////////////////////////////////
	/** Proportion of annotated unique parameters */
	private float paramUniqueAverageScore = 0;
	/** Proportion of annotated parameter instances */
	private float paramInstanceAverageScore = 0;
	/** Proportion of annotated unique parameters */
	private float wordUniqueAverageScore = 0;
	/** Proportion of annotated parameter instances */
	private float wordInstanceAverageScore = 0;
}
