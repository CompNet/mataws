package tr.edu.gsu.mataws.component.preparator.normalizer;

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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import tr.edu.gsu.mataws.tools.FileTools;

/**
 * Transform abbreviations (or any expression, actually) 
 * into the full corresponding word.
 * <br/>
 * The abbreviations are defined as a text file of
 * coma separated values, such as on each line we have
 * one couple "abbreviation,full word".
 * <br/>
 * Example: {@code "My"} {@code "Int"}, {@code "Param"} -> {@code "My"}, {@code "Integer"}, {@code "Parameter"}
 *   
 * @author Koray Mancuhan
 * @author Cihan Aksoy
 * @author Vincent Labatut
 */
public class AbbreviationNormalizer implements NormalizerInterface
{	
	/**
	 * Creates an abbreviation normalizer with the default
	 * abbreviation map.
	 */
	public AbbreviationNormalizer()
	{	String path = FileTools.CONFIG_FOLDER + File.separator + "Abbreviations.txt";
		initData(path);
	}
	
	/**
	 * Creates a filter with a specific
	 * abbreviation map.
	 * 
	 * @param path
	 * 		Path of the file containing the abbreviations.
	 */
	public AbbreviationNormalizer(String path)
	{	initData(path);
	}
	
	///////////////////////////////////////////////////////////
	//	CASE SENSITIVE						///////////////////
	///////////////////////////////////////////////////////////
	/** Whether or not this normalizer is case sensitive */
	private boolean caseSensitive = false;
	
	/**
	 * Change the case-sensitivity of this normalizer.
	 * By default, the normalizer is not case-sensitive.
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
	/** Map of abbreviations for this normalizer */
	private Map<String,String> abbreviations = null;
	
	/**
	 * Init the map of abbreviations for this normalizer.
	 * 
	 * @param path
	 * 		Path of the file containing the stop words.
	 */
	private void initData(String path)
	{	// init
		File file = new File(path);		
		abbreviations = new HashMap<String,String>();
		
		// read the file
		try
		{	FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			while(line!=null)
			{	String temp[] = line.split(",");
				String abbreviation = temp[0].trim();
				String fullWord = temp[1].trim();
				abbreviations.put(abbreviation,fullWord);
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
	public List<String> normalize(List<String> strings)
	{	List<String> result = new ArrayList<String>();
		
		if(caseSensitive)
		{	for(String string: strings)
			{	String fullWord = abbreviations.get(string);
				if(fullWord==null)
					result.add(string);
				else
					result.add(fullWord);
			}
		}
		
		else
		{	for(String string: strings)
			{	boolean found = false;
				Iterator<Entry<String,String>> it = abbreviations.entrySet().iterator();
				while(it.hasNext() && !found)
				{	Entry<String,String> entry = it.next();
					String abbreviation = entry.getKey();
					String fullWord = entry.getValue();
					found = abbreviation.equalsIgnoreCase(string);
					result.add(fullWord);
				}
				if(!found)
					result.add(string);
			}
		}
		
		return result;
	}
}
