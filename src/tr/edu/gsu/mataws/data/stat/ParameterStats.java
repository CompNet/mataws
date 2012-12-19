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
 * parameters resulting from the annotation process.
 * 
 * @author Vincent Labatut
 */
public class ParameterStats extends AbstractStats
{	
	///////////////////////////////////////////////////////////
	//	ID								///////////////////////
	///////////////////////////////////////////////////////////
	/** Id of this parameter */
	private int id;
	
	/**
	 * Returns the id of this parameter.
	 * 
	 * @return
	 * 		Id of this parameter.
	 */
	public int getId()
	{	return id;
	}
	
	/**
	 * Changes the id of this parameter.
	 * 
	 * @param id
	 * 		New id of this parameter.
	 */
	public void setId(int id)
	{	this.id = id;
	}

	///////////////////////////////////////////////////////////
	//	NAME							///////////////////////
	///////////////////////////////////////////////////////////
	/** Name of this parameter */
	private String name;
	
	/**
	 * Returns the name of this parameter.
	 * 
	 * @return
	 * 		Name of this parameter.
	 */
	public String getName()
	{	return name;
	}
	
	/**
	 * Changes the name of this parameter.
	 * 
	 * @param name
	 * 		New name of this parameter.
	 */
	public void setName(String name)
	{	this.name = name;
	}

	///////////////////////////////////////////////////////////
	//	TYPE NAME							///////////////////
	///////////////////////////////////////////////////////////
	/** Type name of this parameter */
	private String typeName;
	
	/**
	 * Returns the type name of this parameter.
	 * 
	 * @return
	 * 		Type name of this parameter.
	 */
	public String getTypeName()
	{	return typeName;
	}
	
	/**
	 * Changes the type name of this parameter.
	 * 
	 * @param typeName
	 * 		New type name of this parameter.
	 */
	public void setTypeName(String typeName)
	{	this.typeName = typeName;
	}

	///////////////////////////////////////////////////////////
	//	REPRESENTATIVE WORD					///////////////////
	///////////////////////////////////////////////////////////
	/** Representative word of this parameter */
	private String representativeWord;
	
	/**
	 * Returns the word.
	 * 
	 * @return
	 * 		Word associated to these stats.
	 */
	public String getRepresentativeWord()
	{	return representativeWord;
	}
	
	/**
	 * Changes the number of occurrences.
	 * 
	 * @param word
	 * 		New word associated to these stats.
	 */
	public void setRepresentativeWord(String word)
	{	this.representativeWord = word;
	}
}
