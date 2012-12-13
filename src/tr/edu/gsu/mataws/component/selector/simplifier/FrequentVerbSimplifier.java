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
import java.util.List;

import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.SynsetType;

import tr.edu.gsu.mataws.data.IdentifiedWord;

/**
 * Keeps the word corresponding to the most frequent verb.
 * The assumption here is we have no nouns (should have been
 * tested before) left. The remaining words might contain
 * several verbs, which we consider as more important, semantically
 * speaking, than adjectives and adverbs. Amongst those verbs,
 * we just select the most frequent one.
 *  
 * @author Vincent Labatut
 */
public class FrequentVerbSimplifier extends AbstractSimplifier<Synset>
{
	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	@Override
	public boolean simplify(List<IdentifiedWord<Synset>> words)
	{	boolean result = false;
		
		// look for verbs
		List<IdentifiedWord<Synset>> verbs = new ArrayList<IdentifiedWord<Synset>>();
		for(IdentifiedWord<Synset> word: words)
		{	Synset synset = word.getSynset();
			SynsetType type = synset.getType();
			if(type==SynsetType.VERB)
				verbs.add(word);
		}
		
		// we want to select the most frequent one.
		// but WordNet does not provide this information:
		// only which meaning of a single word is the most frequent.
		// since we have no way of discriminating the relevance of
		// the various verbs, we decide to choose the verb whose
		// meaning is the most frequent (hence, certain) amongst the
		// other meaning this word can have.
		IdentifiedWord<Synset> frequentVerb = null;
		int maxFreq = Integer.MIN_VALUE;
		for(IdentifiedWord<Synset> verb: verbs)
		{	Synset synset = verb.getSynset();
			String form = verb.getStem();
			if(form==null)
				form = verb.getOriginal();
			int freq = synset.getTagCount(form);
			if(freq>maxFreq)
			{	maxFreq = freq;
				frequentVerb = verb;
			}
		}
		
		// change the word list accordingly (i.e. no change if there's no verb)
		if(frequentVerb!=null)
		{	words.clear();
			words.add(frequentVerb);
			result = true;
		}
		
		return result;
	}
}
