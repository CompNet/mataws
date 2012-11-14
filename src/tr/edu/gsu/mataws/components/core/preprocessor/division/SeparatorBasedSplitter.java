package tr.edu.gsu.mataws.components.core.preprocessor.division;

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
 * Split a word according to the presence of a specific substring
 * acting as a separator. Empty strings are not returned.
 * <br/>
 * Example: {@code "myParam_Out"} -> {@code "myParam"},{@code "Out"}
 *   
 * @author Koray Mancuhan
 * @author Cihan Aksoy
 * @author Vincent Labatut
 */
public class SeparatorBasedSplitter implements SplitterInterface {
	
	/**
	 * Creates an instance for the specified separator string.
	 * 
	 * @param separator
	 * 		String separating substring.
	 */
	public SeparatorBasedSplitter(String separator)
	{	this.separator = separator;
	}
	
	///////////////////////////////////////////////////////////
	//	SEPARATOR							///////////////////
	///////////////////////////////////////////////////////////
	/** Separator used to perform the split */
	private String separator;
	
	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	@Override
	public List<String> split(List<String> strings)
	{	List<String> result = new ArrayList<String>();
		for(String string: strings)
		{	String temp[] = string.split(separator);
			for(String str: temp)
			{	if(!str.isEmpty())
					result.add(str);
			}
		}
		return result;
	}
}
