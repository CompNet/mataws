package tr.edu.gsu.mataws.component.cleaner.filter;

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

/**
 * Simply removes the words shorter than a given
 * limit. Those are supposed to be irrelevant
 * (e.g. single letters).
 * <br/>
 * Example: {@code "A"} {@code "Nice"}, {@code "Parameter"} -> {@code "Nice"}, {@code "Parameter"}
 *   
 * @author Vincent Labatut
 */
public class LengthFilter implements FilterInterface
{
	/**
	 * Creates a length-based filter 
	 * using the default limit of 1.
	 * All words above this length
	 * (or equal) will be removed.
	 */
	public LengthFilter()
	{	this.limit = 1;
	}
	
	/**
	 * Creates a length-based filter 
	 * using the specified limit.
	 * All words above this length
	 * (or equal) will be removed.
	 * 
	 * @param limit
	 * 		Length limit.
	 */
	public LengthFilter(int limit)
	{	this.limit = limit;
	}
	
	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	/** Minimal length specified for a word */
	private int limit;
	
	@Override
	public List<String> filter(List<String> strings)
	{	List<String> result = new ArrayList<String>();
		
		for(String string: strings)
		{	if(string.length()>limit)
				result.add(string);
		}
		
		return result;
	}
}
