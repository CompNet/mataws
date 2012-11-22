package tr.edu.gsu.mataws.component.core.associator.mapper;

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

import com.articulate.sigma.WordNet;

import tr.edu.gsu.mataws.component.core.selector.IdentifiedWord;
import tr.edu.gsu.mataws.tools.SigmaTools;

import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.SynsetType;
import edu.smu.tspell.wordnet.impl.file.ReferenceSynset;

/**
 * Uses Sigma to retrieve the SUMO concept
 * associated to the received WordNet synset.
 * <br/>
 * Example: {@code "Password"} -> {@code "LinguisticExpression"}
 *   
 * @author Vincent Labatut
 */
public class SigmaMapper implements MapperInterface<Synset>
{
	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	@Override
	public String map(IdentifiedWord<Synset> word)
	{	String result = null;
		
		// if the synset if available, directly retrieve the associated sumo concept
		if(word.getSynset()!=null)
		{	// build the sigma key for this word
			ReferenceSynset synset = (ReferenceSynset)word.getSynset();
			int wordnetId = synset.getOffset();
			String prefix = null;
			SynsetType type = synset.getType();
			if(type==SynsetType.NOUN)
				prefix = "1";
			else if(type==SynsetType.VERB)
				prefix = "2";
			else if(type==SynsetType.ADJECTIVE || type==SynsetType.ADJECTIVE_SATELLITE)
				prefix = "3";
			else if(type==SynsetType.ADVERB)
				prefix = "4";
			String sigmaKey = prefix + wordnetId;
			
			// retrieve the concept from sigma
			WordNet sigmaObject = SigmaTools.getAccess();
			result = sigmaObject.getSUMOMapping(sigmaKey);
			
			// clean it
			if(result!=null)
			{	// remove the "&%" prefix
				result = result.substring(2);
				// remove the suffix
				List<Character> suffixes = Arrays.asList('=','+','@',':','[',']');
				List<Character> negSuffixes = Arrays.asList(':','[',']');
				Character suffix = result.charAt(result.length()-1);
				while(result!=null && suffixes.contains(suffix))
				{	// we cannot handle negative suffixes
					if(negSuffixes.contains(suffix))
						result = null;
					// otherwise, we just remove the suffix to get a clean concept
					else
					{	result = result.substring(0,result.length()-1);
						suffix = result.charAt(result.length()-1);
					}
				}
				// TODO the suffixes could be used to have more information regarding
				// the quality of the associated concept:
				// 	- '='	the synset and the concept have an equivalent meaning
				// 	- '+'	the concept subsumes the synset meaning
				// 	- '@'	the synset is an instance of the concept
				// 	- ':'	the synset and the concept have opposite meanings
				// 	- '['	the concept subsumes the negation of the synset meaning 
				// 	- ']'	the negation of the synset is an instance of the concept
			}
		}
		
		// if we could not get a concept from the synset, 
		// then we try using the stem or original string
		if(result==null)
		{	// set the string
			String string = word.getStem();
			if(string==null)
				string = word.getOriginal();
			
			// retrieve the concept from sigma
			WordNet sigmaObject = SigmaTools.getAccess();
			List<Integer> posList = Arrays.asList
			(	WordNet.NOUN,
				WordNet.VERB,
				WordNet.ADVERB,
				WordNet.ADJECTIVE,
				WordNet.ADJECTIVE_SATELLITE
			);
			Iterator<Integer> it = posList.iterator();
			while(it.hasNext() && result==null)
			{	int pos = it.next();
				result = sigmaObject.getSUMOterm(string,pos);
			
			}
		}
		
		return result;
	}
}
