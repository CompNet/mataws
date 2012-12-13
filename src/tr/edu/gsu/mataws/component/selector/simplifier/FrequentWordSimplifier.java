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

import com.whitemagicsoftware.wordsplit.TextSegmenter;

import edu.smu.tspell.wordnet.Synset;

import tr.edu.gsu.mataws.data.IdentifiedWord;
import tr.edu.gsu.mataws.tools.strings.WordSplitTools;

/**
 * Keeps the most frequent word amongst the remaining one.
 * This simplifier is viewed as a last chance selection:
 * all the other approaches failed to simplify the list,
 * so we just do the last possible action. Note we use
 * the lexical frequency of the word, which is not provided
 * by WordNet.
 *  
 * @author Vincent Labatut
 */
public class FrequentWordSimplifier extends AbstractSimplifier<Synset>
{
	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	@Override
	public boolean simplify(List<IdentifiedWord<Synset>> words)
	{	boolean result = false;
		TextSegmenter wordSplit = WordSplitTools.getAccess();
		
		// get the remaining word with highest frequency
		IdentifiedWord<Synset> frequentWord = null;
		double maxFreq = Integer.MIN_VALUE;
		for(IdentifiedWord<Synset> word: words)
		{	String string = word.getStem();
			Double freq = wordSplit.getFrequency(string);
			if(freq==null)
			{	string = word.getOriginal();
				freq = wordSplit.getFrequency(string);
			}
			if(freq!=null && freq>maxFreq)
			{	maxFreq = freq;
				frequentWord = word;
			}
		}
		
		// change the word list accordingly
		if(frequentWord!=null)
		{	words.clear();
			words.add(frequentWord);
			result = true;
		}
		
		return result;
	}
}
