package tr.edu.gsu.mataws.data.stat;

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

/**
 * Represents the statistics processed for the
 * representative words resulting from the annotation process.
 * 
 * @author Vincent Labatut
 */
public class WordStats extends AbstractStats
{	
	///////////////////////////////////////////////////////////
	//	WORD								///////////////////
	///////////////////////////////////////////////////////////
	/** Word associated to these stats */
	private String word;
	
	/**
	 * Returns the word.
	 * 
	 * @return
	 * 		Word associated to these stats.
	 */
	public String getWord()
	{	return word;
	}
	
	/**
	 * Changes the number of occurrences.
	 * 
	 * @param word
	 * 		New word associated to these stats.
	 */
	public void setWord(String word)
	{	this.word = word;
	}
	
}
