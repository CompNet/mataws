package tr.edu.gsu.mataws.tools;

/*
 * Mataws - Multimodal Automatic Tool for the Annotation of Web Services
 * Copyright 2010 Cihan Aksoy and Koray Man�uhan
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

import uk.ac.shef.wit.simmetrics.similaritymetrics.Levenshtein;

import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.SynsetType;
import edu.smu.tspell.wordnet.WordNetDatabase;

/**
 * This class contains various methods and variables used 
 * all over the software when accessing WordNet through JAWS.
 * 
 * @author Vincent Labatut
 */
public class JawsTools
{	
	/** Object allowing accessing WordNet through the Jaws library */
	private static WordNetDatabase access = null;
	
	/**
	 * Initializes the Jaws library once and for all
	 */
	private static void init()
	{	System.setProperty("wordnet.database.dir",FileTools.WORDNET_FOLDER);
		access = WordNetDatabase.getFileInstance();
	}
	
	/**
	 * Returns the object allowing accessing WordNet
	 * through the Jaws library. Initializes this object
	 * if necessary.
	 * 
	 * @return
	 * 		The object granting access to the Jaws library.
	 */
	public static WordNetDatabase getAccess()
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
	{	// get all synsets associated to the string
		Synset[] synsets = access.getSynsets(string);
		// get their corresponding synset types
		List<SynsetType> types = new ArrayList<SynsetType>();
		for(Synset synset: synsets)
		{	SynsetType type = synset.getType();
			if(!types.contains(type))
				types.add(type);
		}
		
		// lookup the stems for each type
		List<String> stems = new ArrayList<String>();
		for(SynsetType type: types)
		{	String proposedStems[] = access.getBaseFormCandidates(string,type);
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