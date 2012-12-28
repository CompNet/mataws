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

import tr.edu.gsu.mataws.data.parameter.MatawsParameter;

/**
 * Represents the statistics processed for the
 * parameters resulting from the annotation process.
 * 
 * @author Vincent Labatut
 */
public class ParameterStats extends AbstractStats
{	
	/**
	 * Builds a parameter stats from a file.
	 * 
	 * @param id
	 * 		Id read in the file.
	 */
	public ParameterStats(int id)
	{	this.id = id;
		updateIdIdnex(id);
	}
	
	/**
	 * Builds a new parmeter stats, from
	 * an actual parameter.
	 * 
	 * @param parameter
	 * 		The parameter used to initialize the new
	 * 		parameter stat currently built.
	 */
	public ParameterStats(MatawsParameter parameter)
	{	id = getNewId();
		name = parameter.getName();
		representativeWord = parameter.getRepresentativeWord().toString();
		typeName = parameter.getTypeName();
		operationName = parameter.getSineOperation().getName();
		concept = parameter.getConcept();
		annotated = concept.equals(MatawsParameter.NO_CONCEPT);
		occurrences = 1;
	}
	
	///////////////////////////////////////////////////////////
	//	ID								///////////////////////
	///////////////////////////////////////////////////////////
	/** Id of this parameter */
	private int id;
	/** Static field used to generate ids */
	private static int ID_INDEX = 0;
	
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

	/**
	 * Returns a new parameter id.
	 * 
	 * @return
	 * 		New id (not used by any existing parameter).
	 */
	private static int getNewId()
	{	int result = ID_INDEX;
		ID_INDEX++;
		return result;
	}
	
	/**
	 * Updates the current id index so
	 * that it takes into account some
	 * parameters loaded from a file
	 * (which already have their own ids).
	 * 
	 * @param id
	 * 		The last loaded id.
	 */
	private static void updateIdIdnex(int id)
	{	ID_INDEX = Math.max(ID_INDEX,id);
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
	//	OPERATION NAME						///////////////////
	///////////////////////////////////////////////////////////
	/** Operation name of this parameter */
	private String operationName;
	
	/**
	 * Returns the operation name of this parameter.
	 * 
	 * @return
	 * 		Operation name of this parameter.
	 */
	public String getOperationName()
	{	return operationName;
	}
	
	/**
	 * Changes the operation name of this parameter.
	 * 
	 * @param operationName
	 * 		New operation name of this parameter.
	 */
	public void setOperationName(String operationName)
	{	this.operationName = operationName;
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

	///////////////////////////////////////////////////////////
	//	MISC.								///////////////////
	///////////////////////////////////////////////////////////
	/**
	 * Returns a copy of this parameter stats object,
	 * meant to represent an id-less parameter instance.
	 * 
	 * @return
	 * 		A copy of this object.
	 */
	public ParameterStats copy()
	{	ParameterStats result = new ParameterStats(-1);
	
		result.annotated = annotated;
		result.concept = concept;
		result.name = name;
		result.occurrences = occurrences;
		result.typeName = typeName;
		result.operationName = operationName;
		result.representativeWord = representativeWord;
		result.PvsW = PvsW;
		result.WvsC = WvsC;
		result.PvsC = PvsC;
		
		return result;
	}
}
