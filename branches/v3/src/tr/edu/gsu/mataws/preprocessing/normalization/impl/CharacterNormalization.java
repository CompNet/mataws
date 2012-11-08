package tr.edu.gsu.mataws.preprocessing.normalization.impl;

/*
 * Mataws - Multimodal Automatic Tool for the Annotation of Web Services
 * Copyright 2011-12 Cihan Aksoy, Koray Mançuhan & Vincent Labatut
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
import java.util.Locale;

import tr.edu.gsu.mataws.preprocessing.normalization.NormalizationStrgy;

/**
 * Normalization Strategy which transforms upper case characters to lower case
 * characters in the list of little words of a parameter name.
 *   
 * @author Koray Mancuhan & Cihan Aksoy
 *
 */
public class CharacterNormalization implements NormalizationStrgy {
    @Override
	public List<String> execute(List<String> paramName) {
		List<String> results = paramName;
		for(int i =0 ; i < results.size(); i++)
			results.set(i, results.get(i).toLowerCase(Locale.ENGLISH));
		return results;
	}

}
