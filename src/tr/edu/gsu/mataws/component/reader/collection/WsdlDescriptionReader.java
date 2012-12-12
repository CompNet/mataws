package tr.edu.gsu.mataws.component.reader.collection;

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
import java.io.FileNotFoundException;
import java.util.logging.Logger;

import tr.edu.gsu.mataws.tools.misc.FileTools;
import tr.edu.gsu.sine.col.Collection;
import tr.edu.gsu.sine.in.Digger;
import tr.edu.gsu.sine.in.Language;

/**
 * This class is used to read the data contained in the input
 * WSDL files, and represent them as a hierarchy of Java objects.
 * 
 * @author Cihan Aksoy
 * @author Koray Mancuhan
 * @author Vincent Labatut
 */
public class WsdlDescriptionReader implements DescriptionReaderInterface
{
	@Override
	public Collection readCollection(String subfolder) throws FileNotFoundException
	{	// init path & name
		String path = FileTools.INPUT_FOLDER;
		String name = "all";
		if(subfolder!=null)
		{	path = path + File.separator + subfolder;
			name = subfolder;
		}
		File folder = new File(path);
		
		// init sine digger
		Digger d = new Digger(Logger.getAnonymousLogger());
		// read description files
		Collection result = d.dig(folder, Language.WSDL, name);
		return result;
	}
}
