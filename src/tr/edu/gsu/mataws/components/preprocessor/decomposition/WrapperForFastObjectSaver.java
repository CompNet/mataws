package tr.edu.gsu.mataws.components.preprocessor.decomposition;

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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import tr.edu.gsu.mataws.tools.FileTools;

public class WrapperForFastObjectSaver {

	/**
	 * Stores serializable objects. IMPORTANT: THOSE OBJECTS SHOULD HAVE A serialVersionUID!
	 *     private static final long serialVersionUID = 1L;
	 * @throws IOException 
	 */
	public static void saveToFile(String filename, Serializable serializableObject) throws IOException {
		FileOutputStream fos = new FileOutputStream(filename);
		ObjectOutputStream oos = new ObjectOutputStream( fos );
		oos.writeObject( serializableObject );
		oos.close();
	}


	/**
	 * Load a serialized dictionary.
	 * @throws IOException
	 */
	public static synchronized Object load(String filename) throws IOException {
		//System.out.println(filename);
		String s = filename.substring(1);
		filename = System.getProperty("user.dir") + File.separator + "resources" + File.separator + s;
filename = FileTools.SPLITTER_FOLDER + File.separator + s; //TODO modif
		File file = new File(filename);
		FileInputStream f = new FileInputStream(file);
		ObjectInputStream oos = new ObjectInputStream(f);
		try {
			return oos.readObject();
		} catch (ClassNotFoundException e) {
			IOException ioe = new IOException();
			ioe.initCause(e);
			throw ioe;
		}
	}
}
