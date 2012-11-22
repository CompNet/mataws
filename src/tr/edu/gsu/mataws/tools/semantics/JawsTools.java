package tr.edu.gsu.mataws.tools.semantics;

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
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.whitemagicsoftware.wordsplit.TextSegmenter;

import tr.edu.gsu.mataws.data.IdentifiedWord;
import tr.edu.gsu.mataws.tools.misc.FileTools;
import tr.edu.gsu.mataws.tools.strings.SimmetricsTools;
import tr.edu.gsu.mataws.tools.strings.WordSplitTools;
import uk.ac.shef.wit.simmetrics.similaritymetrics.Levenshtein;

import edu.smu.tspell.wordnet.NounSynset;
import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.SynsetType;
import edu.smu.tspell.wordnet.VerbSynset;
import edu.smu.tspell.wordnet.WordNetDatabase;

/**
 * This class contains various methods and variables used 
 * all over the software when accessing WordNet through JAWS.
 * 
 * @author Vincent Labatut
 */
public class JawsTools
{	
	///////////////////////////////////////////////////////////
	//	ACCESS								///////////////////
	///////////////////////////////////////////////////////////
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

	///////////////////////////////////////////////////////////
	//	STEMS								///////////////////
	///////////////////////////////////////////////////////////
	/**
	 * Retrieves the stem of the specified word
	 * from WordNet, even when the POS is unknown.
	 * If no stem is found, then the original
	 * string is returned.
	 * 
	 * @param string
	 * 		String to be process.
	 * @return
	 * 		The stem of the specified String.
	 */
	public static String getStem(String string)
	{	String result = null;
		
		IdentifiedWord<Synset> temp = getIdentifiedWord(string);
		if(temp!=null)
			result = temp.getStem();
		if(result==null)
			result = string;
		
		return result;
	}
	
	/**
	 * Retrieves the most likely stem for the 
	 * specified Synset, from WordNet.
	 * 
	 * @param synset
	 * 		Synset to be process.
	 * @return
	 * 		The most likely stem for the the specified Synset.
	 */
	public static String getStem(Synset synset)
	{	String result = null;
		TextSegmenter wordSplitObject = WordSplitTools.getAccess();
		
		String stems[] = synset.getWordForms();
		double maxFrequency = Integer.MIN_VALUE;
		for(String stem: stems)
		{	Double freq = wordSplitObject.getFrequency(stem);
			if(freq!=null && freq>maxFrequency)
			{	maxFrequency = freq;
				result = stem;
			}
		}
		
		return result;
	}
	
	/**
	 * Retrieves the stem and synset for the specified
	 * word, even when the POS is unknown. If no 
	 * correspondance is found, the method returns 
	 * {@code null}.
	 * 
	 * @param string
	 * 		The expression to be lookedup
	 * @return
	 * 		The identified synset and stem, or {@code null} if none was found.
	 */
	public static IdentifiedWord<Synset> getIdentifiedWord(String string)
	{	IdentifiedWord<Synset> result = null;
		// get all synsets associated to the string
		Synset[] synsets = access.getSynsets(string);
		
		// provided there's at least one synset in WN
		if(synsets.length>0)
		{	// get their corresponding synset types
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
			Levenshtein similarity = SimmetricsTools.getLevenshteinSimilarity();
			String selectedStem = null;
			for(String stem: stems)
			{	float d = similarity.getSimilarity(string,stem);
				if(d>maxSim)
				{	maxSim = d;
					selectedStem = stem;
				}
			}
			
			// get the corresponding synset. if several ones are
			// compatible, then nouns are taken in priority, followed
			// by verbs, adverbs and adjectives.
			Synset selectedSynset = null;
			for(Synset synset: synsets)
			{	List<String> wordForms = Arrays.asList(synset.getWordForms());
				if(wordForms.contains(selectedStem))
				{	if(selectedSynset==null)
						selectedSynset = synset;
					else
					{	SynsetType type0 = selectedSynset.getType();
						if(type0!=SynsetType.NOUN)
						{	SynsetType type = synset.getType();
							if(type0==SynsetType.VERB)
							{	if(type==SynsetType.NOUN)
									selectedSynset = synset;
							}
							else if(type0==SynsetType.ADVERB)
							{	if(type==SynsetType.NOUN || type==SynsetType.VERB)
									selectedSynset = synset;
							}
							else if(type0==SynsetType.ADJECTIVE)
							{	if(type==SynsetType.NOUN || type==SynsetType.VERB || type==SynsetType.ADVERB)
									selectedSynset = synset;
							}
							else if(type0==SynsetType.ADJECTIVE_SATELLITE)
							{	if(type==SynsetType.NOUN || type==SynsetType.VERB || type==SynsetType.ADVERB || type==SynsetType.ADJECTIVE)
									selectedSynset = synset;
							}
						}
					}
				}
			}
			
			// possibly builds the result
			if(selectedStem!=null)
				result = new IdentifiedWord<Synset>(string, selectedStem, selectedSynset);
		}
		
		return result;
	}
	
	///////////////////////////////////////////////////////////
	//	HYPER/HYPONYMS						///////////////////
	///////////////////////////////////////////////////////////
	/**
	 * Checks if the first word is a hypernym of the second one.
	 * The method looks for a path in the synset network, with
	 * a maximal length of {@code limit} (can be equal to the limit).
	 * <br/>
	 * If such a path exists, the method returns the distance between
	 * the synsets on this path. Otherwise, the value {@code -1} is 
	 * returned.
	 * 
	 * @param hypernym
	 * 		The supposed hypernym.
	 * @param hyponym
	 * 		The supposed hyponym.
	 * @param limit
	 * 		Maximal distance between the synsets.
	 * @return
	 * 		The distance between the synsets, or {@code -1} if no path exists.
	 */
	public static int isHypernym(Synset hypernym, Synset hyponym, int limit)
	{	int result = -1;
		int distance = 0;
		
		if(hypernym.equals(hyponym))
			result = distance;
		else
		{	Set<Synset> toProcess = new TreeSet<Synset>();
			toProcess.add(hyponym);
			while(result<0 && distance<limit)
			{	distance++;
				
				// get all needed hypernyms
				Set<Synset> hypernyms = new TreeSet<Synset>();
				for(Synset synset: toProcess)
				{	List<Synset> temp = getHypernyms(synset);
					hypernyms.addAll(temp);
				}
				
				// check if they contain the searched synset
				if(hypernyms.contains(hypernym))
					result = distance;
				// otherwise, go up the next level
				else
					toProcess = hypernyms;
			}
		}
		
		return result;
	}
	
	/**
	 * This methods take a synset and retrieves from
	 * WordNet the list of associated hypernyms.
	 * <br/>
	 * Note: this is defined only for nouns and verbs.
	 * <br/>
	 * TODO It seems possible to do some equivalent process
	 * for adjectives and adverbs, by going through the
	 * notion of pertainym, i.e. word from which the adverb
	 * or adjective is derived: if it's a noun or verb,
	 * it has itself hypernyms.
	 *  
	 * @param synset
	 * 		The concerned synset.
	 * @return
	 * 		The list of its hypernyms.
	 */
	public static List<Synset> getHypernyms(Synset synset)
	{	// init
		Set<Synset> result0 = new TreeSet<Synset>();
		SynsetType type = synset.getType();
		
		// process nouns
		if(type==SynsetType.NOUN)
		{	NounSynset nounSynset = (NounSynset)synset;
			List<NounSynset> hypernyms;
			// general hypernyms
			hypernyms = Arrays.asList(nounSynset.getHypernyms());
			result0.addAll(hypernyms);
			// instance hypernyms
			hypernyms = Arrays.asList(nounSynset.getInstanceHypernyms());
			result0.addAll(hypernyms);
		}
		
		// process verbs
		else if(type==SynsetType.VERB)
		{	VerbSynset verbSynset = (VerbSynset)synset;
			List<VerbSynset> hypernyms = Arrays.asList(verbSynset.getHypernyms());
			result0.addAll(hypernyms);
		}
		
		// set result
		List<Synset> result = new ArrayList<Synset>();
		result.addAll(result0);
		return result;
	}
	
	/**
	 * This methods take a synset and retrieves from
	 * WordNet the list of associated hypernyms, up to
	 * the specified distance.
	 * <br/>
	 * Note: this is defined only for nouns and verbs.
	 * <br/>
	 * TODO It seems possible to do some equivalent process
	 * for adjectives and adverbs, cf. {@link #getHypernyms}.
	 *  
	 * @param hyponym
	 * 		The concerned synset.
	 * @param limit
	 * 		The search limit (in terms of distance on the hyper/hyponymial graph).
	 * @return
	 * 		The list of all its hypernyms.
	 */
	public static List<Synset> getAllHypernyms(Synset hyponym, int limit)
	{	Set<Synset> result0 = new TreeSet<Synset>();
		
		int distance = 0;
		Set<Synset> toProcess = new TreeSet<Synset>();
		Set<Synset> processed = new TreeSet<Synset>();
		toProcess.add(hyponym);
		while(distance<limit)
		{	distance++;
			
			// get all direct hypernyms
			Set<Synset> hypernyms = new TreeSet<Synset>();
			for(Synset synset: toProcess)
			{	List<Synset> temp = getHypernyms(synset);
				hypernyms.addAll(temp);
			}
			
			// update sets
			result0.addAll(hypernyms);
			processed.addAll(toProcess);
			hypernyms.removeAll(processed);
			toProcess = hypernyms;
		}
		
		// create the result list
		List<Synset> result = new ArrayList<Synset>();
		result.addAll(result0);
		return result;
	}

	///////////////////////////////////////////////////////////
	//	HOLO/MERONYMS						///////////////////
	///////////////////////////////////////////////////////////
	/**
	 * Checks if the first word is a holonym of the second one.
	 * The method looks for a path in the synset network, with
	 * a maximal length of {@code limit} (can be equal to the limit).
	 * <br/>
	 * If such a path exists, the method returns the distance between
	 * the synsets on this path. Otherwise, the value {@code -1} is 
	 * returned.
	 * 
	 * @param holonym
	 * 		The supposed holonym.
	 * @param meronym
	 * 		The supposed meronym.
	 * @param limit
	 * 		Maximal distance between the synsets.
	 * @return
	 * 		The distance between the synsets, or {@code -1} if no path exists.
	 */
	public static int isHolonym(Synset holonym, Synset meronym, int limit)
	{	int result = -1;
		int distance = 0;
		
		if(holonym.equals(meronym))
			result = distance;
		else
		{	Set<Synset> toProcess = new TreeSet<Synset>();
			toProcess.add(meronym);
			while(result<0 && distance<limit)
			{	distance++;
				
				// get all needed holonyms
				Set<Synset> holonyms = new TreeSet<Synset>();
				for(Synset synset: toProcess)
				{	List<Synset> temp = getHolonyms(synset);
					holonyms.addAll(temp);
				}
				
				// check if they contain the searched synset
				if(holonyms.contains(holonym))
					result = distance;
				// otherwise, go up the next level
				else
					toProcess = holonyms;
			}
		}
		
		return result;
	}

	/**
	 * This methods take a synset and retrieves from
	 * WordNet the list of associated holonyms.
	 * <br/>
	 * Note: this is defined only for nouns.
	 * <br/>
	 * TODO It seems possible to do some equivalent process
	 * for adjectives and adverbs, by going through the
	 * notion of pertainym, i.e. word from which the adverb
	 * or adjective is derived: if it's a noun,
	 * it has itself hypernyms.
	 *  
	 * @param synset
	 * 		The concerned synset.
	 * @return
	 * 		The list of its holonyms.
	 */
	public static List<Synset> getHolonyms(Synset synset)
	{	// init
		Set<Synset> result0 = new TreeSet<Synset>();
		SynsetType type = synset.getType();
		
		// process nouns
		if(type==SynsetType.NOUN)
		{	NounSynset nounSynset = (NounSynset)synset;
			List<NounSynset> holonyms;
			// member holonyms
			holonyms = Arrays.asList(nounSynset.getMemberHolonyms());
			result0.addAll(holonyms);
			// part holonyms
			holonyms = Arrays.asList(nounSynset.getPartHolonyms());
			result0.addAll(holonyms);
			// substance holonyms
			holonyms = Arrays.asList(nounSynset.getSubstanceHolonyms());
			result0.addAll(holonyms);
		}
		
		//set result
		List<Synset> result = new ArrayList<Synset>();
		result.addAll(result0);
		return result;
	}

	/**
	 * This methods take a synset and retrieves from
	 * WordNet the list of associated holonyms, up to
	 * the specified distance.
	 * <br/>
	 * Note: this is defined only for nouns.
	 * <br/>
	 * TODO It seems possible to do some equivalent process
	 * for adjectives and adverbs, cf. {@link #getHypernyms}.
	 *  
	 * @param meronym
	 * 		The concerned synset.
	 * @param limit
	 * 		The search limit (in terms of distance on the hyper/hyponymial graph).
	 * @return
	 * 		The list of all its holonyms.
	 */
	public static List<Synset> getAllHolonyms(Synset meronym, int limit)
	{	Set<Synset> result0 = new TreeSet<Synset>();
		
		int distance = 0;
		Set<Synset> toProcess = new TreeSet<Synset>();
		Set<Synset> processed = new TreeSet<Synset>();
		toProcess.add(meronym);
		while(distance<limit)
		{	distance++;
			
			// get all direct holonyms
			Set<Synset> holonyms = new TreeSet<Synset>();
			for(Synset synset: toProcess)
			{	List<Synset> temp = getHolonyms(synset);
				holonyms.addAll(temp);
			}
			
			// update sets
			result0.addAll(holonyms);
			processed.addAll(toProcess);
			holonyms.removeAll(processed);
			toProcess = holonyms;
		}
		
		// create the result list
		List<Synset> result = new ArrayList<Synset>();
		result.addAll(result0);
		return result;
	}
}
