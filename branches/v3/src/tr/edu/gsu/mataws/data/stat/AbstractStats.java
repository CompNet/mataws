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
	//	OCCURRENCES							///////////////////
	///////////////////////////////////////////////////////////
	/** Number of occurrences (one for unique lists) */
	private int occurrences;
	
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
	private boolean annotated;
	
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
	private float pVsW;
	/** Annotation grade when comparing word and concept */
	private float wVsC;
	/** Annotation grade when comparing parameter and concept */
	private float pVsC;
	
	/**
	 * Returns the annotation grade for the
	 * comparison of parameter and word.
	 * 
	 * @return
	 * 		Annotation grade obtained when comparing parameter and word.
	 */
	public float getpVsW()
	{	return pVsW;
	}
	
	/**
	 * Changes the annotation grade for the
	 * comparison of parameter and word.
	 * 
	 * @param pVsW
	 * 		New annotation grade obtained when comparing parameter and word.
	 */
	public void setpVsW(float pVsW)
	{	this.pVsW = pVsW;
	}
	
	/**
	 * Returns the annotation grade for the
	 * comparison of word and concept.
	 * 
	 * @return
	 * 		Annotation grade obtained when comparing word and concept.
	 */
	public float getwVsC()
	{	return wVsC;
	}
	
	/**
	 * Changes the annotation grade for the
	 * comparison of word and concept.
	 * 
	 * @param wVsC
	 * 		New annotation grade obtained when comparing word and concept.
	 */
	public void setwVsC(float wVsC)
	{	this.wVsC = wVsC;
	}
	
	/**
	 * Returns the annotation grade for the
	 * comparison of parameter and word.
	 * 
	 * @return
	 * 		Annotation grade obtained when comparing parameter and concept.
	 */
	public float getpVsC()
	{	return pVsC;
	}
	
	/**
	 * Changes the annotation grade for the
	 * comparison of parameter and concept.
	 * 
	 * @param pVsC
	 * 		New annotation grade obtained when comparing parameter and concept.
	 */
	public void setpVsC(float pVsC)
	{	this.pVsC = pVsC;
	}
}
