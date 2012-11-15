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

import tr.edu.gsu.mataws.tools.JawsTools;

import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.SynsetType;
import edu.smu.tspell.wordnet.WordNetDatabase;

/**
 * This class uses Jaws and WordNet to identify the 
 * synset corresponding to some word.
 *  
 * @author Vincent Labatut
 */
public class JawsIdentifier implements IdentifierInterface<Synset>
{
	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	@Override
	public List<IdentifiedWord<Synset>> identify(List<String> strings)
	{	// init
		List<IdentifiedWord<Synset>> result = new ArrayList<IdentifiedWord<Synset>>(); 
		WordNetDatabase jawsObject = JawsTools.getAccess();
		
		// order the pos depending on their relavance regarding our context (parameter names)
		List<SynsetType> posList = Arrays.asList
		(	SynsetType.NOUN,
			SynsetType.VERB,
			SynsetType.ADJECTIVE,
			SynsetType.ADJECTIVE_SATELLITE,
			SynsetType.ADVERB
		);
		
		// process each string separately
		for(String string: strings)
		{	IdentifiedWord<Synset> iw = null;
			 
			// process each pos one after the other, stop as soon as a synset is found
			Iterator<SynsetType> it = posList.iterator();
			while(it.hasNext() && iw==null)
			{	// get all synsets associated to the string for the current pos
				SynsetType pos = it.next();
				Synset synsets[] = jawsObject.getSynsets(string,pos);
				
				// get all stems associated to this word and pos
				String proposedStems[] = jawsObject.getBaseFormCandidates(string,pos);
				
				// get the appropriate stem for each synset
				String stems[] = new String[synsets.length];
				Arrays.fill(stems, null);
				for(int i=0;i<synsets.length;i++)
				{	Synset synset = synsets[i];
					String synsetStems[] = synset.getWordForms();
					int j = 0;
					while(j<proposedStems.length && stems[i]==null)
					{	int k = 0;
						String proposedStem = proposedStems[j];
						while(k<synsetStems.length && stems[i]==null)
						{	String synsetStem = synsetStems[k];
							if(proposedStem.equals(synsetStem)) // should we ignore case, here ?
								stems[i] = synsetStem;
							k++;
						}
						j++;
					}
				}
				
				// select the synset with highest frequency
				Synset selectedSynset = null;
				String selectedStem = null;
				int maxScore = Integer.MAX_VALUE;
				for(int i=0;i<synsets.length;i++)
				{	Synset synset = synsets[i];
					String stem = stems[i];
					int score = synset.getTagCount(stem);
					if(score>maxScore)
					{	maxScore = score;
						selectedSynset = synset;
						selectedStem = stem;
					}
				}
				
				iw = new IdentifiedWord<Synset>(string, selectedStem, selectedSynset);
			}
			
			// add to result list
			if(iw==null)
				iw = new IdentifiedWord<Synset>(string,null,null);
			result.add(iw);
		}
		
		return result;
	}
}
