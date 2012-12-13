package tr.edu.gsu.mataws.component.preparator.filter;

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
 * Simply removes the extra occurrences of words 
 * appearing several times in the parameter name.
 * <br/>
 * Example: {@code "Param"} {@code "X"}, {@code "Param"} -> {@code "Param"}, {@code "X"}
 *   
 * @author Vincent Labatut
 */
public class RedundancyFilter extends AbstractFilter
{
	///////////////////////////////////////////////////////////
	//	CASE SENSITIVE						///////////////////
	///////////////////////////////////////////////////////////
	/** Whether or not this filter is case sensitive */
	private boolean caseSensitive = false;
	
	/**
	 * Change the case-sensitivity of this filter.
	 * By default, the filter is not case-sensitive.
	 * 
	 * @param caseSensitive
	 * 		New case-sensitivity.
	 */
	public void setCaseSensitive(boolean caseSensitive)
	{	this.caseSensitive = caseSensitive;
	}

	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	@Override
	public List<String> filter(List<String> strings)
	{	List<String> result = new ArrayList<String>();
		
		if(caseSensitive)
		{	while(strings.size()>1)
			{	String string = strings.get(0);
				result.add(string);
				strings.remove(0);
				int index;
				do
				{	index = strings.indexOf(string);
					if(index!=-1)
						strings.remove(index);
				}
				while(index!=-1);
			}
			if(strings.size()==1)
				result.add(strings.get(0));
		}
		
		else
		{	while(strings.size()>1)
			{	String string = strings.get(0);
				result.add(string);
				strings.remove(0);
				int index;
				do
				{	index = -1;
					int i = 0;
					while(index==-1 && i<strings.size())
					{	String str = strings.get(i);
						if(string.equalsIgnoreCase(str))
							index = i;
						i++;
					}
					if(index!=-1)
						strings.remove(index);
				}
				while(index!=-1);
			}
			if(strings.size()==1)
				result.add(strings.get(0));
		}
		
		return result;
	}
}
