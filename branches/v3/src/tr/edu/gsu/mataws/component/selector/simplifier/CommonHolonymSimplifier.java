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
 * common holonym (unlike {@link DirectHolonymSimplifier},
 * which is looking for a direct holonymial relationship. 
 * A parameter allows specifying a limit to this search.
 * For instance, a limit of two means we look for holonyms of holonyms.
 * The common holoonym is added to the list, while both meronyms are removed.
 * <br>
 * Example: {@code "Client"}, <b>{@code "Handlebar"}</b>, <b>{@code "Pedal"}</b> -> {@code "Client"}, </b>{@code "Bicycle"}</b>
 *  
 * @author Vincent Labatut
 */
public class CommonHolonymSimplifier extends AbstractSimplifier<Synset>
{
	/**
	 * Builds a {@code CommonHolonymSimplifier} using the
	 * default distance limit of 2.
	 */
	public CommonHolonymSimplifier()
	{	super();
		
		this.limit = 2;
	}
	
	/**
	 * Builds a {@code CommonHolonymSimplifier} using the
	 * specified distance limit.
	 * 
	 * @param limit 
	 * 		Specified limit.
	 */
	public CommonHolonymSimplifier(int limit)
	{	super();
	
		this.limit = limit;
	}
	
	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	/** Limit used when looking for common hyolonyms */
	private int limit;
	
	@Override
	public boolean simplify(List<IdentifiedWord<Synset>> words)
	{	logger.log("Simplifying using common holonyms");
		logger.increaseOffset();
		boolean result = false;
		
		// log the inputs
		logger.log("Applying to the following words:");
		logger.increaseOffset();
		for(IdentifiedWord<Synset> word: words)
			logger.log(word.toString());
		logger.decreaseOffset();
		
		// get all the required holonyms
		IdentifiedWord<Synset> holonym = null;
		@SuppressWarnings("unchecked")
		IdentifiedWord<Synset> meronyms[] = new IdentifiedWord[2];
		Arrays.fill(meronyms,null);
		int i = 0;
		while(i<words.size()-1 && holonym!=null)
		{	int j = i + 1;
			IdentifiedWord<Synset> word1 = words.get(i);
			Synset synset1 = word1.getSynset();
			List<Synset> holonyms1 = JawsTools.getAllHolonyms(synset1,limit);
			while(j<words.size() && !result)
			{	IdentifiedWord<Synset> word2 = words.get(j);
				Synset synset2 = word2.getSynset();
				List<Synset> holonyms2 = JawsTools.getAllHolonyms(synset2,limit);
				holonyms2.retainAll(holonyms1);
				if(!holonyms2.isEmpty())
				{	// select the most appropriate hypernym: 
					// use the distance in the hyper/hyponymial graph
					int minDist = Integer.MAX_VALUE;
					Synset selectedHolonym = null;
					for(int k=0;k<holonyms2.size();k++)
					{	Synset h = holonyms2.get(k);
						int d1 = JawsTools.isHolonym(synset1,h,limit); //should not be negative
						int d2 = JawsTools.isHolonym(synset2,h,limit); //should not be negative
						int distance = d1 + d2;
						if(distance<minDist)
						{	minDist = distance;
							selectedHolonym = h;
						}
					}
					// create the word representing the hypernym
					meronyms[0] = word1;
					meronyms[1] = word2;
					String original = word1.getOriginal() + "vs. " + word2.getOriginal();
					String stem = JawsTools.getStem(selectedHolonym);
					holonym = new IdentifiedWord<Synset>(original,stem,selectedHolonym);
				}
				j++;
			}
			i++;
		}
		
		// update the word list
		logger.log("Updating word list");
		if(holonym!=null)
		{	result = true;
			words.remove(meronyms[0]);
			words.remove(meronyms[1]);
			words.add(holonym);
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
