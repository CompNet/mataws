package tr.edu.gsu.mataws.component.preparator.splitter;

/*
 * Mataws - Multimodal Automatic Tool for the Annotation of Web Services
 * Copyright 2010 Cihan Aksoy and Koray Man�uhan
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
 * Split a word according to the presence of digits.
 * Empty strings are not returned.
 * <br/>
 * Example: {@code "myParam64Out"} -> {@code "myParam"},{@code "Out"}
 *   
 * @author Koray Mancuhan
 * @author Cihan Aksoy
 * @author Vincent Labatut
 */
public class NumberSplitter extends AbstractSplitter
{	
	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	@Override
	public List<String> split(List<String> strings)
	{	String msg = "Spitting using case, for the strings: ";
		for(String string: strings)
			msg = msg + " '" + string + "'";
		logger.log(msg);
		logger.increaseOffset();
		List<String> result = new ArrayList<String>();
		
		logger.log("Processing each string individually");
		logger.increaseOffset();
		for(String string: strings)
		{	logger.log("Processing string '"+string+"'");
		
			String temp[] = string.split("[0-9]");
			for(String str: temp)
			{	if(!str.isEmpty())
					result.add(str);
			}
		}
		logger.decreaseOffset();
		
		// log result
		msg = " Result:";
		for(String s: result)
			msg = msg + " '"+s+"'";
		logger.log(msg);

		logger.decreaseOffset();
		return result;
	}
}
