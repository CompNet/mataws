package tr.edu.gsu.mataws.component.core.selector.simplifier;

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

import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.WordNetDatabase;

import tr.edu.gsu.mataws.component.core.selector.IdentifiedWord;
import tr.edu.gsu.mataws.tools.JawsTools;

/**
 * Checks if any combination of consecutive words could
 * correspond to an expression stored in WordNet. In this
 * case, the whole series of words is replaced by the
 * stem of the word retrieved from WordNet.
 *  
 * @param <T>
 * 		Class used to represent the synsets. 
 *  
 * @author Vincent Labatut
 */
public class FusionSimplifier<T> implements SimplifierInterface<T>
{
	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	@Override
	public boolean simplify(List<IdentifiedWord<T>> words)
	{	WordNetDatabase jawsObject = JawsTools.getAccess();
		boolean result = false;
		
		// process all appropriate combinations of indices
		List<List<Integer>> indices = initIndexList(words.size());
		List<List<Integer>> combinations = generateAllCombis(indices);
		
		// lookup the corresponding combinations of strings in WordNet
		List<String> stringList = new ArrayList<String>();
		List<Synset[]> synsetList = new ArrayList<Synset[]>();
		List<List<Integer>> indexList = new ArrayList<List<Integer>>();
		while(combinations.size()>0)
		{	// get the next combination
			List<Integer> combi = combinations.get(0);
			combinations.remove(0);
			// retrieve the corresponding string
			String string = buildStringFromIndices(words, combi);
			// check if WordNet knows it 
			Synset[] synsets = jawsObject.getSynsets(string);
			if(synsets!=null && synsets.length>0)
			{	// remove all combinations containing these indices
				clearIndices(combinations,combi);
				// update variables
				stringList.add(string);
				synsetList.add(synsets);
				indexList.add(combi);
				result = true;
			}
		}
		
		// process the mergeable words
		for(int i=0;i<synsetList.size();i++)
		{	Synset[] synset = synsetList.get(i);
			List<Integer> index = indexList.get(i);
			String string = stringList.get(i);
			
		}
		
		return result;
	}		
	
	/**
	 * Builds a list of lists, each one containing
	 * an integer value.
	 *  
	 * @param size
	 * 		Number of sublists.
	 * @return
	 * 		A list of integer lists.
	 */
	private List<List<Integer>> initIndexList(int size)
	{	List<List<Integer>> result = new ArrayList<List<Integer>>();
		for(int i=0;i<size;i++)
		{	List<Integer> list = new ArrayList<Integer>();
			list.add(i);
			result.add(list);
		}
		return result;
	}
	
	/**
	 * Generates a list containing all combinations
	 * of two consecutive strings from the the original list.
	 * 
	 * @param indices
	 * 		List of original strings.
	 * @return
	 * 		List of concatenated strings.
	 */
	private List<List<Integer>> generateAllCombis(List<List<Integer>> indices)
	{	List<List<Integer>> result = new ArrayList<List<Integer>>();
//TODO not tested		
		if(indices.size()==1)
			result.add(indices.get(0));
		
		else if(indices.size()>1)
		{	List<List<Integer>> tempList = new ArrayList<List<Integer>>();
			for(int i=0;i<indices.size()-1;i++)
			{	List<Integer> str0 = indices.get(i);
				List<Integer> str1 = indices.get(i+1);
				List<Integer> tempStr = new ArrayList<Integer>();
				tempStr.addAll(str0);
				tempStr.addAll(str1);
				tempList.add(tempStr);
			}
			List<List<Integer>> tempList2 = generateAllCombis(tempList);
			result.addAll(tempList2);
			result.addAll(indices);
		}
		
		return result;
	}
	
	/**
	 * Combines the words in the specified list, according
	 * to the specified list of indices, in order to generate
	 * a string.
	 * 
	 * @param words
	 * 		Original words.
	 * @param indices
	 * 		Indices of the words to concatenate.
	 * @return
	 * 		String resulting from the concatenation.
	 */
	private String buildStringFromIndices(List<IdentifiedWord<T>> words, List<Integer> indices)
	{	String result = "";
		for(int i: indices)
			result = result + " " + words.get(i).getOriginal();
		result = result.substring(0,result.length()-1);
		return result;
	}
	
	/**
	 * Removes from {@code list} all lists containing at least one value
	 * from {@code indices}.
	 * 
	 * @param list
	 * 		Original list of index lists.
	 * @param indices
	 * 		The indices to remove.
	 */
	private void clearIndices(List<List<Integer>> list, List<Integer> indices)
	{	Iterator<List<Integer>> it = list.iterator();
		while(it.hasNext())
		{	List<Integer> temp = it.next();
			List<Integer> tempCp = new ArrayList<Integer>(temp);
			tempCp.retainAll(indices);
			if(!tempCp.isEmpty())
				it.remove();
		}
	}
}
