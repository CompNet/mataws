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

import java.util.Arrays;
import java.util.List;

import edu.smu.tspell.wordnet.Synset;

import tr.edu.gsu.mataws.data.parameter.IdentifiedWord;
import tr.edu.gsu.mataws.tools.semantics.JawsTools;

/**
 * Considers every pair of words in the list, and looks for a 
 * common hypernym (unlike {@link DirectHypernymSimplifier},
 * which is looking for a direct hypernymial relationship. 
 * A parameter allows specifying a limit to this search.
 * For instance, a limit of two means we look for hypernyms of hypernyms.
 * The common hypernym is added to the list, while both hyponyms are removed.
 * <br>
 * Example: {@code "Client"}, <b>{@code "Interest"}</b>, <b>{@code "Rate"}</b> -> {@code "Client"}, </b>{@code "Proportion"}</b>
 *  
 * @author Vincent Labatut
 */
public class CommonHypernymSimplifier extends AbstractSimplifier<Synset>
{
	/**
	 * Builds a {@code CommonHypernymSimplifier} using the
	 * default distance limit of 2.
	 */
	public CommonHypernymSimplifier()
	{	super();
	
		this.limit = 2;
	}
	
	/**
	 * Builds a {@code CommonHypernymSimplifier} using the
	 * specified distance limit.
	 * 
	 * @param limit 
	 * 		Specified limit.
	 */
	public CommonHypernymSimplifier(int limit)
	{	super();
	
		this.limit = limit;
	}
	
	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	/** Limit used when looking for common hypernyms */
	private int limit;
	
	@Override
	public boolean simplify(List<IdentifiedWord<Synset>> words)
	{	logger.log("Simplifying using common hypernyms");
		logger.increaseOffset();
		boolean result = false;
		
		// log the inputs
		logger.log("Applying to the following words:");
		logger.increaseOffset();
		for(IdentifiedWord<Synset> word: words)
			logger.log(word.toString());
		logger.decreaseOffset();
		
		// get all the required hypernyms
		IdentifiedWord<Synset> hypernym = null;
		@SuppressWarnings("unchecked")
		IdentifiedWord<Synset> hyponyms[] = new IdentifiedWord[2];
		Arrays.fill(hyponyms,null);
		int i = 0;
		while(i<words.size()-1 && hypernym!=null)
		{	int j = i + 1;
			IdentifiedWord<Synset> word1 = words.get(i);
			Synset synset1 = word1.getSynset();
			List<Synset> hypernyms1 = JawsTools.getAllHypernyms(synset1,limit);
			while(j<words.size() && !result)
			{	IdentifiedWord<Synset> word2 = words.get(j);
				Synset synset2 = word2.getSynset();
				List<Synset> hypernyms2 = JawsTools.getAllHypernyms(synset2,limit);
				hypernyms2.retainAll(hypernyms1);
				if(!hypernyms2.isEmpty())
				{	// select the most appropriate hypernym: 
					// use the distance in the hyper/hyponymial graph
					int minDist = Integer.MAX_VALUE;
					Synset selectedHypernym = null;
					for(int k=0;k<hypernyms2.size();k++)
					{	Synset h = hypernyms2.get(k);
						int d1 = JawsTools.isHypernym(synset1,h,limit); //should not be negative
						int d2 = JawsTools.isHypernym(synset2,h,limit); //should not be negative
						int distance = d1 + d2;
						if(distance<minDist)
						{	minDist = distance;
							selectedHypernym = h;
						}
					}
					// create the word representing the hypernym
					hyponyms[0] = word1;
					hyponyms[1] = word2;
					String original = word1.getOriginal() + "vs. " + word2.getOriginal();
					String stem = JawsTools.getStem(selectedHypernym);
					hypernym = new IdentifiedWord<Synset>(original,stem,selectedHypernym);
				}
				j++;
			}
			i++;
		}
		
		// update the word list
		logger.log("Updating word list");
		if(hypernym!=null)
		{	result = true;
			words.remove(hyponyms[0]);
			words.remove(hyponyms[1]);
			words.add(hypernym);
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
