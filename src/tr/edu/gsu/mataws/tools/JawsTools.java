package tr.edu.gsu.mataws.tools;

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

import edu.smu.tspell.wordnet.WordNetDatabase;

/**
 * This class contains various methods and variables used all over the
 * software when accessing Jaws.
 * 
 * @author Vincent Labatut
 */
public class JawsTools
{	
	/** Object allowing accessing WordNet through the Jaws library */
	private static WordNetDatabase jawsObject = null;
	
	/**
	 * Initializes the Jaws library once and for all
	 */
	private static void initJaws()
	{	System.setProperty("wordnet.database.dir",FileTools.WORDNET_FOLDER);
		jawsObject = WordNetDatabase.getFileInstance();
	}
	
	/**
	 * Returns the object allowing accessing WordNet
	 * through the Jaws library. Initializes this object
	 * if necessary.
	 * 
	 * @return
	 * 		The object granting access to the Jaws library.
	 */
	public static WordNetDatabase getJawsObject()
	{	if(jawsObject==null)
			initJaws();
		return jawsObject;
	}
}
