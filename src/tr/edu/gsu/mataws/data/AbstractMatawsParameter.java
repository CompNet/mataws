package tr.edu.gsu.mataws.data;

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

public class AbstractMatawsParameter
{	
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
	protected final List<MatawsSubParameter> children = new ArrayList<MatawsSubParameter>();
	
	public List<MatawsSubParameter> getChildren()
	{	return children;
	}

	///////////////////////////////////////////////////////////
	//	ANNOTATION							///////////////////
	///////////////////////////////////////////////////////////
	protected String representativeWord = null;
	protected String concept = null;

	public String getRepresentativeWord()
	{	return representativeWord;
	}
	
	public void setRepresentativeWord(String representativeWord)
	{	this.representativeWord = representativeWord;
	}

	public String getConcept() 
	{	return concept;
	}

	public void setConcept(String concept)
	{	this.concept = concept;
	}

	///////////////////////////////////////////////////////////
	//	NAMES								///////////////////
	///////////////////////////////////////////////////////////
	public String getName()
	{	String result = sineParameter.getName();
		return result;
	}
	
	public String getTypeName()
	{	String result = sineParameter.getTypeName();
		return result;
	}
}
