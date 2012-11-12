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
package tr.edu.gsu.mataws.preprocessing.decomposition.wrappers;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import de.abelssoft.wordtools.jwordsplitter.impl.EnglishWordSplitter;

public class WrapperForEnglishWordSplitter extends EnglishWordSplitter{

	private static Set<String> words = null;
	
	public WrapperForEnglishWordSplitter() throws IOException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public WrapperForEnglishWordSplitter(boolean withoutConnectingCharacters) throws IOException
	{
		super(withoutConnectingCharacters);
	}
	
	@Override
	protected Set<String> getWordList() throws IOException
	{
	if (words == null) {
        words = loadWords();
    }
		return words;
	}
	
	private static Set<String> loadWords() throws IOException
	{
		return (HashSet<String>)WrapperForFastObjectSaver.load("/wordsEnglish.ser");
	}
}
