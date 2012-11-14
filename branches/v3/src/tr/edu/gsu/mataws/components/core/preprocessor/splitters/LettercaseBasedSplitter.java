package tr.edu.gsu.mataws.components.core.preprocessor.splitters;

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

import java.util.*;

/**
 * Split a word according to the presence of change in the letter case
 * (i.e. lower/upper case). Empty strings are not returned.
 * <br/>
 * Example: {@code "MYParamOut"} -> {@code "MY"}, {@code "Param"}, {@code "Out"}
 * <br/>
 * Possible improvement: use a lexicon to determine only relevant splits.
 * 
 * @author Koray Mancuhan
 * @author Cihan Aksoy
 * @author Vincent Labatut
 */
public class LettercaseBasedSplitter implements SplitterInterface
{	
	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	@Override
	public List<String> split(List<String> strings)
	{	List<String> result = new ArrayList<String>();
		
		// simple split
		for(String string1: strings)
		{	List<String> strings2 = lowUpSplit(string1);
			
			// double split
			for(String string2: strings2)
			{	List<String> temp = upLowSplit(string2);
				result.addAll(temp);
			}
		}
		
		return result;
	}
	
	/**
	 * Looks for a lowercase followed  by an uppercase.
	 * <br/>
	 * Example: {@code "myParamOut"} -> {@code "my"}, {@code "Param"},{@code "Out"}
	 * 
	 * @param string
	 * 		The string to be split.
	 * @return
	 * 		The corresponding list of substrings.
	 */
	public List<String> lowUpSplit(String string)
	{	List<String> result = new ArrayList<String>();
	
		// too short to be split
		if(string.length()==1)
		{	result.add(string);
		}
		
		// regular case
		else if(string.length()>1) 
		{	// look for a change in letter case
			for(int i=1; i<string.length(); i++)
			{	char c0 = string.charAt(i-1);
				char c1 = string.charAt(i);
				if(Character.isLowerCase(c0) && Character.isUpperCase(c1))
				{	// everything before the change is a word
					String word = string.substring(0,i);
					result.add(word);
					// the rest must be processed similarly
					String rest = string.substring(i,string.length());
					List<String> temp = lowUpSplit(rest);
					result.addAll(temp);
				}
			}
			
			// if no split, then keep the whole word
			if(result.isEmpty())
				result.add(string);
		}
		
		return result;		
	}

	/**
	 * Looks for an uppercase followed by a lowercase.
	 * Must be applied after the other process.
	 * <br/>
	 * Example: {@code "MYParamOut"} -> {@code "MY"}, {@code "ParamOut"}
	 * 
	 * @param string
	 * 		The string to be split.
	 * @return
	 * 		The corresponding list of substrings.
	 */
	public List<String> upLowSplit(String string)
	{	List<String> result = new ArrayList<String>();
	
		// too short to be split
		if(string.length()==1 || string.length()==2)
		{	result.add(string);
		}
		
		// regular case
		else if(string.length()>2) 
		{	// look for a change in letter case
			for(int i=2; i<string.length(); i++)
			{	char c0 = string.charAt(i-1);
				char c1 = string.charAt(i);
				if(Character.isUpperCase(c0) && Character.isLowerCase(c1))
				{	// everything before the change is a word
					String word = string.substring(0,i-1);
					result.add(word);
					// the rest must be processed similarly
					String rest = string.substring(i-1,string.length());
					List<String> temp = lowUpSplit(rest);
					result.addAll(temp);
				}
			}
			
			// if no split, then keep the whole word
			if(result.isEmpty())
				result.add(string);
		}
		
		return result;		
	}
}
