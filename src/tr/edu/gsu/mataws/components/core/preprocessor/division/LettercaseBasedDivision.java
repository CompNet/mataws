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

import java.util.*;

/**
 * Split a word according to the presence of change in the letter case
 * (i.e. lower/upper case). Empty strings are not returned.
 * <br/>
 * Two modes exist: single or double. Single looks for an uppercase followed
 * by a lowercase, double looks for two consecutive uppercase followed by a
 * lowercase.
 * <br/>
 * Examples:
 * <ul> 
 * 	<li>simple mode: {@code "myParamOut"} -> {@code "my"}, {@code "Param"},{@code "Out"}</li>
 * 	<li>double mode: {@code "AParamOut"} -> {@code "A"},{@code "ParamOut"}</li>
 * </ul>
 *   
 * @author Koray Mancuhan
 * @author Cihan Aksoy
 * @author Vincent Labatut
 */
public class LettercaseBasedDivision implements DivisionInterface
{	
	public LettercaseBasedDivision(Mode mode)
	{	this.mode = mode;
	}
	
	///////////////////////////////////////////////////////////
	//	MODE								///////////////////
	///////////////////////////////////////////////////////////
	/** Current mode of this object */
	private Mode mode;
	
	/**
	 * Represents the functioning mode
	 * of this processing.
	 * 
	 * @author Vincent Labatut
	 */
	public enum Mode
	{	/** Looks for sequence of two uppercase letters followed by a lowercase one */
		DOUBLE,
		/** Looks for an uppercase letter followed by a lowercase one */
		SIMPLE;
	}

	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	@Override
	public List<String> divide(String name)
	{	List<String> result;
	
		if(mode==Mode.SIMPLE)
			result = simpleDivide(name);
		else
			result = doubleDivide(name);
		
		return result;
	}
	
	public List<String> simpleDivide(String name)
	{	List<String> result = new ArrayList<String>();
	
		// too short to be split
		if(name.length()<1)
		{	result.add(name);
		}
		
		// regular case
		else
		{	// look for a change in letter case
			for(int i=1; i<name.length(); i++)
			{	char c0 = name.charAt(i-1);
				char c1 = name.charAt(i);
				if(Character.isLowerCase(c0) && Character.isUpperCase(c1))
				{	// everything before the change is a word
					String word = name.substring(0,i);
					result.add(word);
					// the rest must be processed similarly
					String rest = name.substring(i,name.length());
					List<String> temp = simpleDivide(rest);
					result.addAll(temp);
				}
			}
			
			// if no split, then take the whole word
			if(result.isEmpty())
				result.add(name);
		}
		
		return result;		
	}

	public List<String> doubleDivide(String name)
	{	List<String> result = new ArrayList<String>();
	
		// too short to be split
		if(name.length()<2)
		{	result.add(name);
		}
		
		// regular case
		else
		{	// look for two consecutive uppercase letters followed by a lowercase one
			for(int i=2; i<name.length(); i++)
			{	char c0 = name.charAt(i-2);
				char c1 = name.charAt(i-1);
				char c2 = name.charAt(i);
				if(Character.isUpperCase(c0) 
						&& Character.isUpperCase(c1) 
						&& Character.isLowerCase(c2))
				{
					String leftString=name.substring(0,i-1);
					String rightString=name.substring(i-1, name.length());
					result=new String[2];
					result[0]=leftString;
					result[1]=rightString;
					break;
				}
		}
		}
		
		return result;
	}
}
