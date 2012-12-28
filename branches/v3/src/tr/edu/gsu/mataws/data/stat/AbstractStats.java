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
 * results the annotation process.
 * 
 * @author Vincent Labatut
 */
public abstract class AbstractStats
{	
	///////////////////////////////////////////////////////////
	//	CONCEPT							///////////////////////
	///////////////////////////////////////////////////////////
	/** Concept for this stat */
	protected String concept;
	
	/**
	 * Returns the associated concept.
	 * 
	 * @return
	 * 		Concept for this stat.
	 */
	public String getConcept()
	{	return concept;
	}
	
	/**
	 * Changes the associated concept.
	 * 
	 * @param concept
	 * 		New concept of this stat.
	 */
	public void setConcept(String concept)
	{	this.concept = concept;
	}

	///////////////////////////////////////////////////////////
	//	OCCURRENCES							///////////////////
	///////////////////////////////////////////////////////////
	/** Number of occurrences (one for unique lists) */
	protected int occurrences;
	
	/**
	 * Returns the number of occurrences.
	 * 
	 * @return
	 * 		Number of occurrences.
	 */
	public int getOccurrences()
	{	return occurrences;
	}
	
	/**
	 * Changes the number of occurrences.
	 * 
	 * @param occurrences
	 * 		New number of occurrences.
	 */
	public void setOccurrences(int occurrences)
	{	this.occurrences = occurrences;
	}
	
	///////////////////////////////////////////////////////////
	//	ANNOTATED							///////////////////
	///////////////////////////////////////////////////////////
	/** Whether the annotation was succesful or not */
	protected boolean annotated;
	
	/**
	 * Indicates if the annotation was successful.
	 * 
	 * @return
	 * 		{@code true} iff the annotation was successful.
	 */
	public boolean isAnnotated()
	{	return annotated;
	}
	
	/**
	 * Changes the flag indicating if the 
	 * annotation was successful.
	 * 
	 * @param annotated
	 * 		New annotation flag value.
	 */
	public void setAnnotated(boolean annotated)
	{	this.annotated = annotated;
	}
	
	///////////////////////////////////////////////////////////
	//	ANNOTATION GRADES					///////////////////
	///////////////////////////////////////////////////////////
	/** Annotation grade when comparing parameter and word */
	protected float PvsW;
	/** Annotation grade when comparing word and concept */
	protected float WvsC;
	/** Annotation grade when comparing parameter and concept */
	protected float PvsC;
	
	/**
	 * Returns the annotation grade for the
	 * comparison of parameter and word.
	 * 
	 * @return
	 * 		Annotation grade obtained when comparing parameter and word.
	 */
	public float getPvsW()
	{	return PvsW;
	}
	
	/**
	 * Changes the annotation grade for the
	 * comparison of parameter and word.
	 * 
	 * @param PvsW
	 * 		New annotation grade obtained when comparing parameter and word.
	 */
	public void setPvsW(float PvsW)
	{	this.PvsW = PvsW;
	}
	
	/**
	 * Returns the annotation grade for the
	 * comparison of word and concept.
	 * 
	 * @return
	 * 		Annotation grade obtained when comparing word and concept.
	 */
	public float getWvsC()
	{	return WvsC;
	}
	
	/**
	 * Changes the annotation grade for the
	 * comparison of word and concept.
	 * 
	 * @param WvsC
	 * 		New annotation grade obtained when comparing word and concept.
	 */
	public void setWvsC(float WvsC)
	{	this.WvsC = WvsC;
	}
	
	/**
	 * Returns the annotation grade for the
	 * comparison of parameter and word.
	 * 
	 * @return
	 * 		Annotation grade obtained when comparing parameter and concept.
	 */
	public float getPvsC()
	{	return PvsC;
	}
	
	/**
	 * Changes the annotation grade for the
	 * comparison of parameter and concept.
	 * 
	 * @param PvsC
	 * 		New annotation grade obtained when comparing parameter and concept.
	 */
	public void setPvsC(float PvsC)
	{	this.PvsC = PvsC;
	}
}
