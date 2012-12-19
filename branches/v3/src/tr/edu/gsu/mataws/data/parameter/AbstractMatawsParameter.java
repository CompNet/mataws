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

import java.util.ArrayList;
import java.util.List;

import tr.edu.gsu.sine.col.Parameter;

/**
 * This class is an internal representation of WS parameter
 * or subparameter (i.e. field from a complex type XSD type).
 * 
 * @author Vincent Labatut
 */
public abstract class AbstractMatawsParameter
{	
	/**
	 * Builds a new internal repreesntation of the
	 * specified WS parameter.
	 * 
	 * @param parameter
	 * 		The original WS parameter represented by this object.
	 */
	public AbstractMatawsParameter(Parameter parameter)
	{	sineParameter = parameter;
		
		// init this object fields
		List<Parameter> sp = sineParameter.getSubParameters();
		for(Parameter p: sp)
		{	MatawsSubParameter child = new MatawsSubParameter(p,this);
			children.add(child);
		}
	}
	
	///////////////////////////////////////////////////////////
	//	PARAMETER							///////////////////
	///////////////////////////////////////////////////////////
	/** The corresponding Sine parameter object */
	protected Parameter sineParameter;
	
	/**
	 * Returns the Sine object representing
	 * this parameter.
	 * 
	 * @return
	 * 		A Sine {@link Parameter} object.
	 */
	public Parameter getSineParameter()
	{	return sineParameter;
	}

	///////////////////////////////////////////////////////////
	//	CHILDREN							///////////////////
	///////////////////////////////////////////////////////////
	/** Subparameters belonging to this parameter */
	protected final List<MatawsSubParameter> children = new ArrayList<MatawsSubParameter>();
	
	/**
	 * Returns the list of children of this parameter.
	 * 
	 * @return
	 * 		A list of subparameters.
	 */
	public List<MatawsSubParameter> getChildren()
	{	return children;
	}

	///////////////////////////////////////////////////////////
	//	CONCEPT								///////////////////
	///////////////////////////////////////////////////////////
	/** The ontological concept associated to this parameter */ 
	protected String concept = null;

	/**
	 * Returns the concept associated to this
	 * parameter (or {@code null} if there is no
	 * such concept.
	 * 
	 * @return
	 * 		A {@code String} representing the concept of this parameter.
	 */
	public String getConcept() 
	{	return concept;
	}

	/**
	 * Changes the concept associated to this parameter.
	 * 
	 * @param concept
	 * 		New concept associated to this parameter.
	 */
	public void setConcept(String concept)
	{	this.concept = concept;
	}

	///////////////////////////////////////////////////////////
	//	REPRESENTATIVE WORD					///////////////////
	///////////////////////////////////////////////////////////
	/** The word used to represent this parameter during the annotation process */
	protected IdentifiedWord<?> representativeWord = null;

	/**
	 * Returns the representative word associated to this parameter.
	 * It contains the corresponding original word, stem and synset.
	 * <br/>
	 * The original word can be either the name of the parameter itself,
	 * or of its data type. 
	 * 
	 * @return
	 * 		An {@link IdentifiedWord} representing this parameter.
	 */
	public IdentifiedWord<?> getRepresentativeWord()
	{	return representativeWord;
	}
	
	/**
	 * Changes the representative word associated to this parameter.
	 * 
	 * @param representativeWord
	 * 		The new {@link IdentifiedWord} to represent this parameter.
	 */
	public void setRepresentativeWord(IdentifiedWord<?> representativeWord)
	{	this.representativeWord = representativeWord;
	}

	///////////////////////////////////////////////////////////
	//	NAMES								///////////////////
	///////////////////////////////////////////////////////////
	/**
	 * Returns the name of this parameter.
	 * 
	 * @return
	 * 		String representing the name of the parameter.
	 */
	public String getName()
	{	String result = sineParameter.getName();
		return result;
	}
	
	/**
	 * Returns the type name of this parameter.
	 * 
	 * @return
	 * 		String representing the type name of this parameter.
	 */
	public String getTypeName()
	{	String result = sineParameter.getTypeName();
		return result;
	}
}
