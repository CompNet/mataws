package tr.edu.gsu.mataws.component.core.selector.identifier;

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

import edu.smu.tspell.wordnet.Synset;

/**
 * Represents a word whose WordNet synset has been identified.
 *  
 * @param <T> 
 *		Class used to represent a WordNet synset.
 *  
 * @author Vincent Labatut
 */
public class IdentifiedWord<T>
{
	/**
	 * Builds a new {@code IdentifiedWord}.
	 * 
	 * @param original
	 * 		The original form of the word.
	 * @param stem
	 * 		Its step form.
	 * @param synset
	 * 		The corresponding WordNet synset.
	 */
	public IdentifiedWord(String original, String stem, T synset)
	{	this.original = original;
		this.stem = stem;
		this.synset = synset;
	}
	
	///////////////////////////////////////////////////////////
	//	WORD								///////////////////
	///////////////////////////////////////////////////////////
	/** The original string */
	private String original;

	/**
	 * The original string provided
	 * for this word.
	 * 
	 * @return
	 * 		Original string.
	 */
	public String getOriginal()
	{	return original;
	}

	///////////////////////////////////////////////////////////
	//	STEM								///////////////////
	///////////////////////////////////////////////////////////
	/** The corresponding normalized string */
	private String stem;

	/**
	 * The stem form of this word, 
	 * if it could be retrieved from WordNet.
	 * 
	 * @return
	 * 		A string representing the stem of this word. 
	 */
	public String getStem()
	{	return stem;
	}

	///////////////////////////////////////////////////////////
	//	WORD								///////////////////
	///////////////////////////////////////////////////////////
	/** The WordNet synset */
	private T synset;

	/**
	 * Returns the synset associated to this
	 * word, if it could be identified.
	 *  
	 * @return
	 * 		A {@link Synset} object.
	 */
	public T getSynset()
	{	return synset;
	}
}
