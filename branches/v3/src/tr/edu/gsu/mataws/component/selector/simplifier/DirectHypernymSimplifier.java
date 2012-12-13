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

import java.util.List;

import edu.smu.tspell.wordnet.Synset;

import tr.edu.gsu.mataws.data.IdentifiedWord;
import tr.edu.gsu.mataws.tools.semantics.JawsTools;

/**
 * Looks for a direct hyper/hyponymial relationship between
 * two words in the list, by opposition to looking for a common 
 * hypernym. A parameter allows specifying a limit to these search.
 * For instance, a limit of two means we look for hypernyms of hypernyms.
 * The concerned hyponym is kept, while the hypernym is removed.
 * <br>
 * Example: {@code "Client"}, <b>{@code "Interest"}</b>, <b>{@code "Percentage"}</b> -> {@code "Client"}, </b>{@code "Percentage"}</b>
 *  
 * @author Vincent Labatut
 */
public class DirectHypernymSimplifier extends AbstractSimplifier<Synset>
{
	/**
	 * Builds a {@code DirectHypernymSimplifier} using the
	 * default distance limit of 2.
	 */
	public DirectHypernymSimplifier()
	{	super();
	
		this.limit = 2;
	}
	
	/**
	 * Builds a {@code DirectHypernymSimplifier} using the
	 * specified distance limit.
	 * 
	 * @param limit 
	 * 		Specified limit.
	 */
	public DirectHypernymSimplifier(int limit)
	{	super();
	
		this.limit = limit;
	}
	
	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	/** Limit used when looking for hypernyms */
	private int limit;
	
	@Override
	public boolean simplify(List<IdentifiedWord<Synset>> words)
	{	logger.increaseOffset();
		boolean result = false;
		
		// look for a hypernymial relationship
		IdentifiedWord<Synset> hypernym = null;
		int i = 0;
		while(i<words.size()-1 && hypernym!=null)
		{	int j = i + 1;
			IdentifiedWord<Synset> word1 = words.get(i);
			Synset synset1 = word1.getSynset();
			while(j<words.size() && !result)
			{	IdentifiedWord<Synset> word2 = words.get(j);
				Synset synset2 = word2.getSynset();
				// checking the first way
				int distance = JawsTools.isHypernym(synset1, synset2, limit);
				if(distance!=-1)
					hypernym = word1;
				else
				{	// checking the other way round
					distance = JawsTools.isHypernym(synset2, synset1, limit);
					if(distance!=-1)
						hypernym = word2;
				}
				j++;
			}
			i++;
		}
		
		// remove the hypernym from the list
		if(hypernym!=null)
		{	result = true;
			words.remove(hypernym);
		}
		
		logger.decreaseOffset();
		return result;
	}
}
