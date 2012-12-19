package tr.edu.gsu.mataws.data.parameter;

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
public class IdentifiedWord<T> implements Comparable<IdentifiedWord<?>>
{
	/**
	 * Builds a new {@code IdentifiedWord}.
	 * The {@code original} parameter must be
	 * different from {@code null}. Both {@code stem} 
	 * and {@code synset} fields are set to {@code null}
	 * and must be subsequently initialized, in order
	 * to get a complete object.
	 * 
	 * @param original
	 * 		The original form of the word.
	 */
	public IdentifiedWord(String original)
	{	this(original,null,null);
	}
	
	/**
	 * Builds a new {@code IdentifiedWord}.
	 * The {@code original} parameter must be
	 * different from {@code null}.
	 * 
	 * @param original
	 * 		The original form of the word.
	 * @param stem
	 * 		Its step form.
	 * @param synset
	 * 		The corresponding WordNet synset.
	 */
	public IdentifiedWord(String original, String stem, T synset)
	{	if(original==null)
			throw new NullPointerException();
	
		this.original = original;
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

	/**
	 * Change the stem associated to
	 * this word.
	 * 
	 * @param stem
	 * 		New stem.
	 */
	public void setStem(String stem)
	{	this.stem = stem;
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

	/**
	 * Change the synset associated to
	 * this word.
	 * 
	 * @param synset
	 * 		New synset.
	 */
	public void setSynset(T synset)
	{	this.synset = synset;
	}

	///////////////////////////////////////////////////////////
	//	COMPARABLE							///////////////////
	///////////////////////////////////////////////////////////
	@Override
	public boolean equals(Object obj)
	{	boolean result = false;
		if(obj!=null && obj instanceof IdentifiedWord<?>)
		{	IdentifiedWord<?> word2 = (IdentifiedWord<?>) obj;
			int compare = compareTo(word2);
			result = compare==0;
		}
		return result;
	}
	
	/**
	 * The comparison is only based on the original
	 * word, the rest is ignored.
	 */
	@Override
	public int compareTo(IdentifiedWord<?> word2)
	{	int result = 0;
		String original2 = word2.getOriginal();
		result = original.compareTo(original2);
		return result;
	}

	@Override
	public int hashCode()
	{	int result = original.hashCode();
		return result;
	}
	
	///////////////////////////////////////////////////////////
	//	MISC								///////////////////
	///////////////////////////////////////////////////////////
	/**
	 * Check if this word is complete, i.e. if it
	 * has all the required fields.
	 * 
	 * @return
	 * 		{@code true} iff both stem and synset fields are defined.
	 */
	public boolean isComplete()
	{	boolean result = stem==null || synset==null;
		return result;
	}
	
	@Override
	public String toString()
	{	String result = "";
		result = result + "original: " + original;
		if(stem!=null)
			result = result + "stem: " + stem;
		if(synset!=null)
			result = result + "synset: " + synset;
		return result;
	}
}
