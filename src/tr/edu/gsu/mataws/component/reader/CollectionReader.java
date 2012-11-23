package tr.edu.gsu.mataws.component.reader;

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

import java.io.FileNotFoundException;

import tr.edu.gsu.sine.col.Collection;

/**
 * This class is used to read the data contained in the input
 * description files, and represent them as a hierarchy of Java objects.
 * 
 * @author Vincent Labatut
 */
public abstract class CollectionReader
{
	/**
	 * Loads the collection of syntactic descriptions 
	 * contained in the specified folder,
	 * which must be contained itself in Mataws input folder.
	 * <br/>
	 * If it is {@code null}, then all description files are processed.
	 * 
	 * @param subfolder
	 *		The folder containing the collection.
	 * @return 
	 * 		The sine object representing a WS collection. 
	 * 
	 * @throws FileNotFoundException 
	 * 		If no file could be found at the specified location. 
	 */
	public abstract Collection readCollection(String subfolder) throws FileNotFoundException;
//	public abstract List<MatawsParameter> readCollection(String subfolder) throws FileNotFoundException;
	
	/**
	 * Analyzes the specified Sine object representing a collection,
	 * and extract all parameters. Those are embedded in Mataws objects,
	 * to allow further processing.
	 * 
	 * @param collection
	 * 		The Sine collection object.
	 * @return
	 * 		A list of Mataws parameter objects.
	 */
/*	protected List<MatawsParameter> extractParameters(Collection collection)
	{	List<MatawsParameter> result = new ArrayList<MatawsParameter>();
		
		for(Service service: collection.getServices())
		{	for(Operation operation: service.getOperations())
			{	for(Parameter parameter: operation.getParameters())
				{	MatawsParameter p = new MatawsParameter(parameter, operation, service);
					result.add(p);
				}
			}
		}
		
		return result;
	}
*/	
}
