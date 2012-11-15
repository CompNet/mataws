package tr.edu.gsu.mataws.tools;

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

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import uk.ac.shef.wit.simmetrics.similaritymetrics.Levenshtein;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.morph.WordnetStemmer;

/**
 * This class contains various methods and variables used 
 * all over the software when accessing WordNet through JWI.
 * 
 * Notes:
 * - IIndexWord = unique form (lemma + pos)
 * - IWord = instance of unique form (IIndexWord + synset)
 * - ISynset = synset (meaning + set of IWord)
 * 
 * @author Vincent Labatut
 */
public class JwiTools
{	
	/** Object allowing accessing WordNet through the Jwi library */
	private static IDictionary access = null;
	/** Object able to retrieve stems */
	private static WordnetStemmer stemmer = null;
	
	/**
	 * Initializes the Jwi library once and for all
	 */
	private static void init()
	{	URL url = null;
		try
		{	url = new URL("file", null, FileTools.WORDNET_FOLDER);
			access = new Dictionary(url);
			access.open();
			stemmer = new WordnetStemmer(access);
		} 
		catch(MalformedURLException e)
		{	// problem while accessing the dictionary
			e.printStackTrace();
		}
		catch (IOException e)
		{	// problem while reading the dictionary
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the object allowing accessing WordNet
	 * through the Jwi library. Initializes this object
	 * if necessary.
	 * 
	 * @return
	 * 		The object granting access to the Jwi library.
	 */
	public static IDictionary getAccess()
	{	if(access==null)
			init();
		return access;
	}

	/**
	 * Retrieves the stem of the specified word
	 * from WordNet, even when the POS in unknown.
	 * If no stem is found, then the original
	 * string is returned.
	 * 
	 * @param string
	 * 		String to be process.
	 * @return
	 * 		The stem of the specified String.
	 */
	public static String getStem(String string)
	{	// lookup the stems for each type
		List<String> stems = new ArrayList<String>();
		for(POS pos: POS.values())
		{	List<String> proposedStems = stemmer.findStems(string,pos);
			for(String stem: proposedStems)
			{	if(!stems.contains(stem))
					stems.add(stem);
			}
		}
		
		// select the most appropriate stem. we'd like to select
		// the stem associated to the most frequent synset, but
		// this information is not available. so let us use the
		// one syntactically closest to the original word
		float maxSim = Float.MIN_VALUE;
		String selectedStem = null;
		Levenshtein similarity = SimmetricsTools.getLevenshteinSimilarity();
		for(String stem: stems)
		{	float d = similarity.getSimilarity(string,stem);
			if(d>maxSim)
				selectedStem = stem;
		}
		
		// set final result
		String result;
		if(selectedStem == null)
			result = string;
		else
			result = selectedStem;
		return result;
	}
}
