package tr.edu.gsu.mataws.preprocessing.purification.impl;

/*
 * Mataws - Multimodal Automatic Tool for the Annotation of Web Services
 * Copyright 2011-12 Cihan Aksoy & Koray Mançuhan
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

import java.io.File;
import java.util.List;

import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.WordNetDatabase;

import tr.edu.gsu.mataws.preprocessing.purification.PurificationStrgy;
import tr.edu.gsu.mataws.toolbox.LevenshteinDistance;

/**
 * Purification Strategy which turns plurial names, conjugated verbs, 
 * participles etc. into their original forms by using WordNet lexicon.
 *   
 * @author Cihan Aksoy
 *
 */
public class JawsPurificationImpl implements PurificationStrgy {

	@Override
	public List<String> execute(List<String> paramName) {
		
		System.setProperty("wordnet.database.dir",System.getProperty("user.dir") + File.separator + "dictionary");
		
		WordNetDatabase wd = WordNetDatabase.getFileInstance();
		
		for (int i = 0; i < paramName.size(); i++) {
			String result = null;
			String string = paramName.get(i);
			Synset[] synsets = wd.getSynsets(string);
			for (Synset synset : synsets) {
				String[] wordForms = synset.getWordForms();
				for (String string2 : wordForms) {
					if(LevenshteinDistance.getLevenshteinDistance(string2, string) < 4 
							&& LevenshteinDistance.getLevenshteinDistance(string2, string)>=1){
						result = string2;
						break;
					}
				}
				paramName.set(i, result);
			}
		}
		return paramName;
	}
}
