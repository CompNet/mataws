package tr.edu.gsu.mataws.component.core.preprocessor.normalizer;

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
import edu.smu.tspell.wordnet.WordNetDatabase;

import tr.edu.gsu.mataws.tools.JawsTools;
import uk.ac.shef.wit.simmetrics.similaritymetrics.Levenshtein;

/**
 * Replace a word by its stem (or lemma form).
 * Plural nouns are replaced by the singular version,
 * conjugated verbs are replaced by the infinitive form, etc.
 *   
 * @author Koray Mancuhan
 * @author Cihan Aksoy
 * @author Vincent Labatut
 */
public class StemNormalizer implements NormalizerInterface
{	
	/**
	 * Builds and initialize a stem normalizer
	 * relying on Jaws to access WordNet.
	 * (Could be easily extended to other
	 * libraries or lexicons). 
	 */
	public StemNormalizer()
	{	initJaws();
	}
	
	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	/** Object allowing accessing WordNet through Jaws */
	private WordNetDatabase jawsObject = null;
	/** Levenshtein similarity used to compare stems */
	private Levenshtein similarity = null;
	
	/**
	 * Init the object granting access to WordNet
	 * through the Jaws library.
	 */
	private void initJaws()
	{	jawsObject = JawsTools.getJawsObject();
		similarity = new Levenshtein();
	}
	
	@Override
	public List<String> normalize(List<String> strings)
	{	List<String> result = new ArrayList<String>(); 
		
		for(String string: strings)
		{	// get all synsets associated to the string
			Synset[] synsets = jawsObject.getSynsets(string);
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
			{	String proposedStem[] = jawsObject.getBaseFormCandidates(string,type);
				for(String stem: proposedStem)
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
			for(String stem: stems)
			{	float d = similarity.getSimilarity(string,stem);
				if(d>maxSim)
					selectedStem = stem;
			}
			
			// add to result list
			if(selectedStem == null)
				result.add(string);
			else
				result.add(selectedStem);
		}
		
		return result;
	}
}
