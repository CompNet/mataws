/*
 * Mataws - Multimodal Automatic Tool for the Annotation of  Web Services
 * Copyright 2011 Cihan Aksoy & Koray Mançuhan
 * 
 * This file is part of Mataws - Multimodal Automatic Tool for the Annotation of  Web Services.
 * 
 * Mataws - Multimodal Automatic Tool for the Annotation of  Web Services is 
 * free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 * 
 * Mataws - Multimodal Automatic Tool for the Annotation of  Web Services 
 * is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Mataws - Multimodal Automatic Tool for the Annotation of  Web Services.
 * If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package tr.edu.gsu.mataws.toolbox;

import com.articulate.sigma.WordNet;

/**
 * This class is used to find appropriate concept for a given word.
 *   
 * @author Koray Mancuhan & Cihan Aksoy
 *
 */
public class SigmaUtil {

	private static WordNet myDictionary = WordNet.wn;
	
	private static final int NOUN = 1;
	private static final int VERB = 2;
	private static final int ADJECTIF = 3;
	private static final int ADVERB = 4;
	
	/**
	 * Return an ontological concept in SUMO for a parameter.
	 * 
	 * @param aParameter
	 * 			a parameter name 
	 * @return
	 * 			an ontological concept in SUMO
	 */
	public static String findConcept(String aParameter) {
		String result;
		
		String aMatching = myDictionary.getSUMOterm(aParameter, NOUN);
		if (aMatching == null) {
			aMatching = myDictionary.getSUMOterm(aParameter, VERB);
			if (aMatching == null) {
				aMatching = myDictionary.getSUMOterm(aParameter, ADJECTIF);
				if (aMatching == null) {
					aMatching = myDictionary.getSUMOterm(aParameter, ADVERB);
					if (aMatching == null) {
						result = "NoMatch";
					} else {
						result = aMatching;
					}
				} else {
					result = aMatching;
				}
			} else {
				result = aMatching;
			}
		} else {
			result = aMatching;
		}
		return result;
	}
	
	public static String findConcept(String aParameter, String wordUsage) {
		myDictionary.setControl(wordUsage);
		return findConcept(aParameter);
	}
}
