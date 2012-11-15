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
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

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
 * @author Koray Mancuhan
 * @author Cihan Aksoy
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
		
		// build the list of strings only
		List<List<Integer>> indices = new ArrayList<List<Integer>>();
		for(int i=0;i<words.size();i++)
		{	List<Integer> list = new ArrayList<Integer>();
			list.add(i);
			indices.add(list);
		}
		
		// process all combinations of indices
		List<List<Integer>> combinations = generateAllCombis(indices);
		
		// lookup the corresponding combinations of strings in WordNet
		for()
		jawsObject.getSynsets(string);
		
		public List<String> onlyOneRepresenter(List<String> preprocessedResult){
			List<String> result = new ArrayList<String>();
			String temp = onlyOneRepresenterCore(preprocessedResult);

			if(temp!=null){
				result.add(temp);
				return result;
			}
			else if(preprocessedResult.size() > 2){
				List<String> tempList = new ArrayList<String>();
				for(int i=preprocessedResult.size()-1;i>1;i--){
					for(int j = 0;j<preprocessedResult.size()-i+1;j++){
						tempList = new ArrayList<String>();
						for(int k = 0; k<i;k++){
							tempList.add(preprocessedResult.get(k+j));
						}
						temp = onlyOneRepresenterCore(tempList);
						if(temp!=null){
							result = new ArrayList<String>(preprocessedResult);
							String first = tempList.get(0);
							int index = preprocessedResult.indexOf(first);
							result.add(index, temp);
							for (String string : tempList) {
								result.remove(string);
							}
							return result;
						}
					}
				}
			}
			return null;
		}
		
		public String onlyOneRepresenterCore(List<String> preprocessedResult){
			
			StringBuilder sb = new StringBuilder();
			StringBuilder sb2 = new StringBuilder();
			
			for (String string : preprocessedResult) {
				if(string.length()!=0)
					sb.append(string.charAt(0));
			}
			
			String abbrev = sb.toString().toUpperCase(Locale.ENGLISH);
			
			for (String string : preprocessedResult) {
				sb2.append(string);
				sb2.append(" ");
			}
			String compoundWord = sb2.toString();
			compoundWord = compoundWord.substring(0, compoundWord.length()-1);
			
			Synset[] synsets = wd.getSynsets(compoundWord);
			if(synsets.length != 0){
				for (Synset synset : synsets) {
					String[] strings = synset.getWordForms();
					
					for (String string : strings) {
						String[] splitted = string.split(" ");
						if(splitted.length == 1){
							if(!splitted[0].equals(abbrev))
								return splitted[0];
						}
					}
				}
			}
			return null;
		}
		
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
		{	result.addAll(indices);
			List<List<Integer>> tempList = new ArrayList<List<Integer>>();
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
		}
		
		return result;
	}
}
