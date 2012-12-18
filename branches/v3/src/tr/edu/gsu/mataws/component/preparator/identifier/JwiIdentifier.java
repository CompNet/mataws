package tr.edu.gsu.mataws.component.preparator.identifier;

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
import java.util.Iterator;
import java.util.List;

import tr.edu.gsu.mataws.data.IdentifiedWord;
import tr.edu.gsu.mataws.tools.semantics.JwiTools;

import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISenseEntry;
import edu.mit.jwi.item.ISenseKey;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.morph.WordnetStemmer;

/**
 * This class uses Jwi and WordNet to identify the 
 * synset corresponding to some word.
 *  
 * @author Vincent Labatut
 */
public class JwiIdentifier extends AbstractIdentifier<ISynset>
{
	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	/** POS ordered depending on their relavance regarding our context (parameter names) */
	private static final List<POS> POS_LIST = Arrays.asList
	(	POS.NOUN,
		POS.VERB,
		POS.ADJECTIVE,
		POS.ADVERB
	);
	
	@Override
	public void identify(List<IdentifiedWord<ISynset>> words)
	{	logger.log("Identifying the following words using Jaws:");
		for(IdentifiedWord<ISynset> word: words)
			logger.log(word.toString());
		logger.increaseOffset();
		// init
		IDictionary jwiObject = JwiTools.getAccess();
		WordnetStemmer stemmer = JwiTools.getStemmer();
		
		// process each string separately
		logger.log("Processing each string separately");
		logger.increaseOffset();
		for(IdentifiedWord<ISynset> word: words)
		{	logger.log("Word "+word);
			logger.increaseOffset();
			
			if(word.isComplete())
				logger.log("Word already complete");
			else
			{	boolean found = false;
				String original = word.getOriginal();
				 
				// process each pos one after the other, stop as soon as a synset is found
				logger.log("Processing each POS for this word");
				logger.increaseOffset();
				Iterator<POS> it = POS_LIST.iterator();
				while(it.hasNext() && !found)
				{	// get all stems associated to the string, for the current pos
					POS pos = it.next();
					List<String> stems = stemmer.findStems(original,pos);
					logger.log("POS="+pos);
					
					// find the synset associated to the most frequent meaning
					int maxScore = Integer.MAX_VALUE;
					ISynset selectedSynset = null;
					String selectedStem = null;
					for(String stem: stems)
					{	// get the corresponding entity (stem + pos)
						IIndexWord iiw = jwiObject.getIndexWord(stem,pos);
						// get all associated occurrences (index word + meaning)
						List<IWordID> wordIds = iiw.getWordIDs();
						for(IWordID iwi: wordIds)
						{	IWord w = jwiObject.getWord(iwi);
							// get the corresponding meaning
							ISenseKey sk = w.getSenseKey();
							ISenseEntry se = jwiObject.getSenseEntry(sk);
	//						int score = se.getSenseNumber(); // TODO not sure which one should be used, here
							int score = se.getTagCount();
							if(score>maxScore)
							{	selectedSynset = w.getSynset();
								selectedStem = stem;
							}
						}
					}
					
					if(selectedSynset!=null)
					{	found = true;
						word.setStem(selectedStem);
						word.setSynset(selectedSynset);
					}
				}
				logger.decreaseOffset();
			}
			logger.decreaseOffset();
		}
		logger.decreaseOffset();
		
		logger.decreaseOffset();
	}
}
