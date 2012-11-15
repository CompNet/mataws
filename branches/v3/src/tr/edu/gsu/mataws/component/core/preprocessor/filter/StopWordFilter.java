package tr.edu.gsu.mataws.component.core.preprocessor.filter;

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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import tr.edu.gsu.mataws.tools.FileTools;

/**
 * Uses a predefined list to remove words judged irrelevant.
 * The stop words list is defined as a text file, whose each
 * line corresponds to a different word.
 *   
 * @author Koray Mancuhan
 * @author Cihan Aksoy
 * @author Vincent Labatut
 */
public class StopWordFilter implements FilterInterface
{
	/**
	 * Creates a stop word filter with the default
	 * stop words list.
	 */
	public StopWordFilter()
	{	String path = FileTools.CONFIG_FOLDER + File.separator + "StopWords.txt";
		initData(path);
	}
	
	/**
	 * Creates a stop word filter with a specific
	 * stop words list.
	 * 
	 * @param path
	 * 		Path of the file containing the stop words.
	 */
	public StopWordFilter(String path)
	{	initData(path);
	}
	
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
	//	DATA								///////////////////
	///////////////////////////////////////////////////////////
	/** List of stop words for this filter */
	private List<String> stopWords = null;
	
	/**
	 * Init the list of stop words for this filter.
	 * 
	 * @param path
	 * 		Path of the file containing the stop words.
	 */
	private void initData(String path)
	{	// init
		File file = new File(path);		
		stopWords = new ArrayList<String>();
		
		// read the file
		try
		{	FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			while(line!=null)
			{	stopWords.add(line.trim());
				line = br.readLine();
			}
			br.close();
		}
		catch (FileNotFoundException e)
		{	// problem when opening the file
			e.printStackTrace();
		}
		catch (IOException e)
		{	// problem when reading the file
			e.printStackTrace();
		}
	}

	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	@Override
	public List<String> filter(List<String> strings)
	{	List<String> result = new ArrayList<String>();
		
		if(caseSensitive)
		{	for(String string: strings)
			{	if(!stopWords.contains(string))
					result.add(string);
			}
		}
		
		else
		{	for(String string: strings)
			{	boolean found = false;
				Iterator<String> it = stopWords.iterator();
				while(it.hasNext() && !found)
				{	String stopWord = it.next();
					found = stopWord.equalsIgnoreCase(string);
				}
				if(!found)
					result.add(string);
			}
		}
		
		return result;
	}
}
