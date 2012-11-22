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

import java.io.IOException;

import com.articulate.sigma.KBmanager;
import com.articulate.sigma.WordNet;

/**
 * This class contains various methods and variables used 
 * all over the software when accessing Sigma.
 * 
 * @author Vincent Labatut
 */
public class SigmaTools
{	
	/** Object allowing accessing Sigma */
	private static WordNet access = null;
	
	/**
	 * Initializes the Sigma library once and for all
	 */
	private static void init()
	{	try
		{	KBmanager.getMgr().initializeOnce();
			KBmanager.getMgr().setPref("kbDir", FileTools.KNOWBASE_FOLDER);
			WordNet.initOnce();
			access = WordNet.wn;
		}
		catch (IOException e)
		{	// problem while loading the knowledge base or the lexicon 
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the object allowing accessing Sigma.
	 * Initializes this object if necessary.
	 * 
	 * @return
	 * 		The object granting access to the Sigma library.
	 */
	public static WordNet getAccess()
	{	if(access==null)
			init();
		return access;
	}
}
