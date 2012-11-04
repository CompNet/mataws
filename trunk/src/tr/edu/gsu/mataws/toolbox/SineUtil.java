/*
 * Mataws - Multimodal Automatic Tool for the Annotation of  Web Services
 * Copyright 2011 Cihan Aksoy & Koray Mançuhan
 * 
 * This file is part of Mataws - Multimodal Automatic Tool for the Annotation of  Web Services.
 * 
 * Mataws - Multimodal Automatic Tool for the Annotation of  Web Services is 
 * free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 * 
 * Mataws - Multimodal Automatic Tool for the Annotation of  Web Services 
 * is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Mataws - Multimodal Automatic Tool for the Annotation of  Web Services.
 * If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package tr.edu.gsu.mataws.toolbox;

import java.io.File;
import java.util.SortedSet;
import java.util.logging.Logger;

import sine.col.Collection;
import sine.col.Parameter;
import sine.in.Digger;
import sine.in.Language;

/**
 * This class is used to extract parameters and subparameters of a given
 * collection.
 * 
 * @author Koray Mancuhan & Cihan Aksoy
 * 
 */
public class SineUtil {

	/**
	 * Interact with SINE to parse and obtain alphabetically ordered parameter
	 * list from a web service collection.
	 * 
	 * @param collectionName
	 *            a web service collection name.
	 * @return ordered parameter list for a web service collection.
	 * @throws Exception
	 *             indicates a problem if an error occurs while using SINE.
	 */
	public SortedSet<Parameter> initializeParameterList(String collectionName)
			throws Exception {
		Digger d = new Digger(Logger.getAnonymousLogger());
		Collection coll = null;
		coll = d.dig(new File(System.getProperty("user.dir") + File.separator
				+ "input" + File.separator + collectionName), Language.WSDL,
				collectionName);
		SortedSet<Parameter> sortedSet = coll.getParameters();
		return sortedSet;
	}
}
