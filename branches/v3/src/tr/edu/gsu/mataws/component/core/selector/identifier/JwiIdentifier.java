package tr.edu.gsu.mataws.component.core.selector.identifier;

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
import java.util.Iterator;
import java.util.List;

import tr.edu.gsu.mataws.tools.JwiTools;

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
public class JwiIdentifier implements IdentifierInterface<ISynset>
{
	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	
	@Override
	public List<IdentifiedWord<ISynset>> identify(List<String> strings)
	{	// init
		List<IdentifiedWord<ISynset>> result = new ArrayList<IdentifiedWord<ISynset>>(); 
		IDictionary jwiObject = JwiTools.getAccess();
		WordnetStemmer stemmer = JwiTools.getStemmer();
		
		// order the pos depending on their relavance regarding our context (parameter names)
		List<POS> posList = Arrays.asList
		(	POS.NOUN,
			POS.VERB,
			POS.ADJECTIVE,
			POS.ADVERB
		);
		
		// process each string separately
		for(String string: strings)
		{	IdentifiedWord<ISynset> iw = null;
			 
			// process each pos one after the other, stop as soon as a synset is found
			Iterator<POS> it = posList.iterator();
			while(it.hasNext() && iw==null)
			{	// get all stems associated to the string, for the current pos
				POS pos = it.next();
				List<String> stems = stemmer.findStems(string,pos);
				
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
				
				// build identified word
				iw = new IdentifiedWord<ISynset>(string, selectedStem, selectedSynset);
			}
			
			// add to result list
			if(iw==null)
				iw = new IdentifiedWord<ISynset>(string,null,null);
			result.add(iw);
		}
		
		return result;
	}
}
