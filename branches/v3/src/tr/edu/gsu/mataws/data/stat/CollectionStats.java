package tr.edu.gsu.mataws.data.stat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

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

/**
 * 
 * @author Vincent Labatut
 */
public class CollectionStats
{	
	///////////////////////////////////////////////////////////
	//	PARAMETERS						///////////////////////
	///////////////////////////////////////////////////////////
	/** List of parameter statistics */ 
	private List<ParameterStats> parameterStats = new ArrayList<ParameterStats>();
	
	/**
	 * Returns the list of parameter statistics
	 * for the whole collection.
	 * 
	 * @return
	 * 		A list of parameter statistics.
	 */
	public List<ParameterStats> getParameterStats()
	{	return parameterStats;
	}

	///////////////////////////////////////////////////////////
	//	WORDS						///////////////////////////
	///////////////////////////////////////////////////////////
	/** List of word statistics */
	private List<WordStats> wordStats = new ArrayList<WordStats>();
	
	/**
	 * Returns the list of word statistics
	 * for the whole collection.
	 * 
	 * @return
	 * 		A list of word statistics.
	 */
	public List<WordStats> getWordStats()
	{	return wordStats;
	}
	
	/**
	 * Initializes the list of word statistics
	 * using the (aleady initialized) list of 
	 * parameter statistics.
	 */
	public void initWordFromParams()
	{	// group parameters by representative word
		Map<String,List<ParameterStats>> map = new HashMap<String, List<ParameterStats>>();
		for(ParameterStats parameterStat: parameterStats)
		{	String repWord = parameterStat.getRepresentativeWord();
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
			
/**
 * TODO
 * pb avec l'unicité des paramètres : devrait prendre en compte le nom de l'opération,
 * puisque celui ci est maintenant utilisé aussi.
 * (>> mais ça va faire bcp de params uniques, du coup)			
 */
			wordStats.add(wordStat);
		}
	}
}
