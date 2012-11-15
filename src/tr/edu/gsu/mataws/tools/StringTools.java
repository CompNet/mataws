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

import java.text.Normalizer;
import java.text.Normalizer.Form;

/**
 * This class contains various methods and variables used all over the
 * software to handle strings.
 * 
 * @author Vincent Labatut
 */
public class StringTools
{	
	
	/**
	 * Removes all diactrics (accents, cedilla, umlaut, etc.)
	 * from the specified text.
	 * <br/>
	 * source: <a href="http://www.drillio.com/en/software-development/java/removing-accents-diacritics-in-any-language/">Drillio web site</a>
	 * 
	 * @param string
	 * 		The text to process.
	 * @return
	 * 		The same text, but without any diacritics.
	 * 
	 * @author	Drillio
	 */
	public static String removeDiacritics(String string)
	{	String result = Normalizer.normalize(string,Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
		return result;
	}
}
