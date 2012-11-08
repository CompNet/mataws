package tr.edu.gsu.mataws.toolbox;

/*
 * Mataws - Multimodal Automatic Tool for the Annotation of Web Services
 * Copyright 2011-12 Cihan Aksoy & Koray Mançuhan
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

import java.io.*;

import tr.edu.gsu.mataws.transformer.Transformer;
import tr.edu.gsu.mataws.transformer.impl.OWLSTransformerImpl;

/**
 * Class containing the operations to turn a syntaxic web service
 * collection into a semantic web service collection.
 * 
 * @author Koray Mancuhan & Cihan Aksoy
 *
 */
public class CollectionTransformationUtil {
	private String semCollectionDirectory;
	private String synCollectionDirectory;
	
	private Transformer transformer;

	/**
	 * Constructs a CollectionTransformation instance to use
	 * transformation operations.
	 * 
	 * @param semCollectionName
	 * 			semantic collection name to create
	 * @param synCollectionName
	 * 			syntaxic collection name to transform
	 */
	public CollectionTransformationUtil(String synCollectionName) {
		this.semCollectionDirectory = System.getProperty("user.dir") + File.separator + "output" + File.separator + synCollectionName + "_SWS";
		this.synCollectionDirectory = System.getProperty("user.dir") + File.separator + "input" + File.separator + synCollectionName;
		
		transformer = new OWLSTransformerImpl();
	}

	/**
	 * Creates a semantic web service collection.
	 * 
	 * @throws Exception
	 * 			indicates a problem if an error occurs throughout creation
	 */
	public void createSemanticCollection() throws Exception {
		File destFolder = new File(this.semCollectionDirectory);
		File srcFolder = new File(this.synCollectionDirectory);
		this.createSemanticFiles(srcFolder, destFolder);
	}

	/**
	 * Service method creating semantically defined web services 
	 * from syntaxically defined web services.
	 * 
	 * @param srcFolder
	 * 			source folder of syntaxic collection.
	 * @param destFolder
	 * 			destination folder to save semantic collection.
	 */
	private void createSemanticFiles(File srcFolder, File destFolder) {
		String[] folderContents=srcFolder.list();
		
		for(int i=0; i<folderContents.length; i++){
			if(isFolder(folderContents[i])){
				File src=new File(srcFolder.getPath()+ File.separator + folderContents[i]);
				File dest=new File(destFolder.getPath()+ File.separator + folderContents[i]);
				createSemanticFiles(src,dest);
			}
			else{
				if(folderContents[i].endsWith(".wsdl")){
					String sourceFile = srcFolder.getPath()+ File.separator +folderContents[i];
					String dtFolder = destFolder.getPath()+ File.separator +folderContents[i];
					
					File destinationFolderForEachWSDLOp = new File(dtFolder);
					
					destinationFolderForEachWSDLOp.mkdirs();
					transformer.transform(sourceFile, dtFolder);
				}
			}
		}
	}
	
	/**
	 * Service method verifying if a folder content is another folder or not. 
	 * 
	 * @param name
	 * 			a file content
	 * @return
	 * 			true if this file content is a folder.
	 */
	private boolean isFolder(String name){
		boolean result=true;
		if(name.endsWith(".txt") || name.endsWith(".wsdl") || name.endsWith(".html"))
			result=false;
		return result;
	}
}
