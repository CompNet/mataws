package tr.edu.gsu.mataws.tools.strings;

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

import java.io.File;
import java.io.IOException;

import tr.edu.gsu.mataws.tools.misc.FileTools;

import com.whitemagicsoftware.wordsplit.TextSegmenter;

/**
 * This class contains various methods and variables used 
 * all over the software when accessing the WordSplit API.
 * 
 * @author Vincent Labatut
 */
public class WordSplitTools
{	
	/** WordSplit object */
	private static TextSegmenter access = null;
	
	/**
	 * Initializes the WordSplit library once and for all
	 */
	private static void init()
	{	access = new TextSegmenter();
		String path = FileTools.SPLITTER_FOLDER + File.separator + "english.dic";
		try
		{	access.loadLexicon(path);
		}
		catch (IOException e)
		{	// problem while loading the dictionary
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the object allowing accessing the 
	 * WordSplit library. Initializes this object
	 * if necessary.
	 * 
	 * @return
	 * 		The object granting access to the Jaws library.
	 */
	public static TextSegmenter getAccess()
	{	if(access==null)
			init();
		return access;
	}
}
