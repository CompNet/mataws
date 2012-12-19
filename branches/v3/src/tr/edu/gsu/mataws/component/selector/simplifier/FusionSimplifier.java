package tr.edu.gsu.mataws.component.selector.simplifier;

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

import tr.edu.gsu.mataws.data.parameter.IdentifiedWord;
import tr.edu.gsu.mataws.tools.semantics.JawsTools;

/**
 * Checks if any combination of consecutive words could
 * correspond to an expression stored in WordNet. In this
 * case, the whole series of words is replaced by the
 * word retrieved from WordNet.
 * <br>
 * Example: <b>{@code "Postal"}</b>, <b>{@code "Code"}</b>, {@code "user"} -> </b>{@code "Postcode"}</b>, {@code "user"}
 *  
 * @author Vincent Labatut
 */
public class FusionSimplifier extends AbstractSimplifier<Synset>
{
	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	/**
	 * Tries to merge the words from the specified list, by groups of 
	 * {@code n} consecutive words, and to retrieve the corresponding synset 
	 * in WordNet. If this operation is successfull, then the retrieved 
	 * {@link IdentifiedWord} is substituted in the input list.
	 * 
	 * @param words
	 * 		The list of words to be merged.
	 * @param n 
	 * 		Number of consecutive words to merge.
	 * @return
	 * 		{@code true} iff a substitution could be performed.
	 */
	private boolean checkFusion(List<IdentifiedWord<Synset>> words, int n)
	{	logger.increaseOffset();
		boolean result = false;
		
		// process each group of n words
		int i = 0;
		IdentifiedWord<Synset> merge = null;
		while(i<=words.size()-n && merge==null)
		{	// get all possible concatenations
			List<IdentifiedWord<Synset>> list = new ArrayList<IdentifiedWord<Synset>>();
			for(int j=i;j<i+n;j++)
				list.add(words.get(j));
			List<String> concatenations = generateMerges(list);
			
			// look them up in WordNet
			Iterator<String> it = concatenations.iterator();
			while(it.hasNext() && merge==null)
			{	String concatenation = it.next();
				merge = JawsTools.getIdentifiedWord(concatenation);
			}
			
			i++;
		}
		
		// if we cound find a concatenation, replace in the input list
		if(merge!=null)
		{	i--;
			result = true;
			for(int j=i;j<i+n;j++)
				words.remove(i);
			words.add(i,merge);
		}
		
		return result;
	}

	/**
	 * Takes a list of {@link IdentifiedWord} and returns all possible concatenations,
	 * considering both original strings and stems are considered, with a preference
	 * given to the former.
	 * 
	 * @param words
	 * 		The word to be concatenated.
	 * @return
	 * 		A list of all possible concatenations.
	 */
	private List<String> generateMerges(List<IdentifiedWord<Synset>> words)
	{	logger.increaseOffset();
		List<String> result = new ArrayList<String>();
		
		if(!words.isEmpty())
		{	IdentifiedWord<Synset> word = words.get(0);
			String original = word.getOriginal();
			String stem = word.getStem();
			
			// only ine word: simply take the original string and stem
			if(words.size()==1)
			{	result.add(original);
				if(stem!=null)
					result.add(stem);
			}
			
			// more than one word: first process the strings for the rest
			// of the list, then concatenate with the first word
			else if(words.size()>1)
			{	List<IdentifiedWord<Synset>> rest = new ArrayList<IdentifiedWord<Synset>>(words);
				rest.remove(0);
				List<String> strings = generateMerges(rest);
				for(String string: strings)
				{	String temp = original + " " + string;
					result.add(temp);
				}
				if(stem!=null)
				{	for(String string: strings)
					{	String temp = stem + " " + string;
						result.add(temp);
					}
				}
			}
		}
		
		return result;
	}

	@Override
	public boolean simplify(List<IdentifiedWord<Synset>> words)
	{	logger.log("Simplifying by fusion");
		logger.increaseOffset();
		boolean result = false;
		
		// log the inputs
		logger.log("Applying to the following words:");
		logger.increaseOffset();
		for(IdentifiedWord<Synset> word: words)
			logger.log(word.toString());
		logger.decreaseOffset();
		
		// try merging consecutive words,
		// starting with the largest number of words
		// and then smaller and smaller concatenations,
		// until one is found in WordNet
		int n = words.size();
		while(n>1 && !result)
		{	result = checkFusion(words,n);
			n--;
		}
		
		// log the outputs
		logger.log("Resulting words:");
		logger.increaseOffset();
		for(IdentifiedWord<Synset> word: words)
			logger.log(word.toString());
		logger.decreaseOffset();
		
		logger.decreaseOffset();
		return result;
	}
}
