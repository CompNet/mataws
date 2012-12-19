package tr.edu.gsu.mataws.tools.misc;

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
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.SortedSet;

import tr.edu.gsu.mataws.component.reader.descriptions.AbstractDescriptionReader;
import tr.edu.gsu.mataws.component.reader.descriptions.WsdlDescriptionReader;
import tr.edu.gsu.sine.col.Collection;
import tr.edu.gsu.sine.col.Operation;
import tr.edu.gsu.sine.col.Parameter;
import tr.edu.gsu.sine.col.Way;

/**
 * This class contains a set of methods related to 
 * the handling of the WS service collections. It was
 * used when data-driving Mataws conception process.
 * 
 * @author Vincent Labatut
 */
public class CollectionTools
{	
	/**
	 * Launches various processes defined 
	 * when desinging Mataws.
	 * 
	 * @param args
	 * 		Not used.
	 * @throws FileNotFoundException 
	 * 		...
	 */
	public static void main(String[] args) throws FileNotFoundException
	{	extractOperations(null);
	}
	
	/////////////////////////////////////////////////////////////////
	// WSDL COLLECTION		/////////////////////////////////////////
	/////////////////////////////////////////////////////////////////
	/**
	 * Uses Sine to parse the collection in the input folder
	 * and produces a text file containing all
	 * operations with their parameter names and types.
	 * 
	 * @param subfolder
	 * 		Subfolder of the input containing the collection to process,
	 * 		or {@code null} to process the whole content.  
	 * 
	 * @throws FileNotFoundException
	 * 		Problem while loading the collection or recording the text file. 
	 */
	public static void extractOperations(String subfolder) throws FileNotFoundException
	{	// read the collection
		System.out.println("Reading the collection");
		AbstractDescriptionReader readCollection = new WsdlDescriptionReader();
		Collection collection = readCollection.readCollection(subfolder);
		SortedSet<Operation> operations = collection.getOperations();
		
		// open the output text file
		String outFile = FileTools.OUT_OTHERS_FOLDER + File.separator + "operations.txt";
		FileOutputStream fileOut = new FileOutputStream(outFile);
		OutputStreamWriter writer = new OutputStreamWriter(fileOut);
		PrintWriter printWriter = new PrintWriter(writer);

		// write the data in the text file
		System.out.println("Writing the text file");
		for(Operation operation: operations)
		{	List<Parameter> inParameters = operation.getParameters(Way.IN);
			for(Way way: Way.values())
			{	for(Parameter parameter: inParameters)
				{	printWriter.print(operation.getParent().getName() + "\t");
					printWriter.print(operation.getName() + "\t");
					printWriter.print(way.toString() + "\t");
					printWriter.print(parameter.getName() + "\t");
					printWriter.print(parameter.getTypeName());
					printWriter.print("\n");
				}
			}
		}
		
		// close the text file
		printWriter.close();
		System.out.println("All done");
	}
	
	/**
	 * Compares two parameters and returns {@code true} only if they are equal.
	 * The parameter names are always considered first. Then, they types
	 * are optionaly compared. First the type name, and if they are equal, 
	 * then their possibly subparameters.
	 *  
	 * @param p1
	 * 		First parameter.
	 * @param p2
	 * 		Second parameter.
	 * @param compareTypes 
	 * 		Indicates if types should be taken into account.
	 * @return
	 * 		{@code true} iff both parameters are equal. 
	 */
	public static boolean areEqualParameters(Parameter p1, Parameter p2, boolean compareTypes)
	{	boolean result;

		// compare names
		result = p1.getName().equals(p2.getName());
		
		// compare types
		if(result && compareTypes)
		{	// compare type names
			result = p1.getTypeName().equals(p2.getTypeName());
			
			// compare type structures
			if(result)
			{	List<Parameter> s1 = p1.getSubParameters();
				List<Parameter> s2 = p2.getSubParameters();
				// compare the numbers of subparameters 
				result = s1==null && s2==null || s1.size()==s2.size();
				// compare the subparameters themselves
				if(result && s1!=null)
				{	int i=0;
					while(i<s1.size() && result)
					{	Parameter sp1 = s1.get(i);
						Parameter sp2 = s2.get(i);
						result = areEqualParameters(sp1, sp2, compareTypes);
						i++;
					}
				}
			}
		}
		
		return result;
	}

	/**
	 * Compares two parameters, returning an integer depending
	 * on their relative values (standard behavior).
	 * 
	 * @param p1
	 * 		First parameter.
	 * @param p2
	 * 		Second parameter.
	 * @param compareTypes
	 * 		Whether types should be taken into account, or not.
	 * @return
	 * 		An integer representing the result of the comparison.
	 */
	public static int compareParameters(Parameter p1, Parameter p2, boolean compareTypes)
	{	int result;

		// compare names
		result = p1.getName().compareTo(p2.getName());
		
		// compare types
		if(result==0 && compareTypes)
		{	// compare type names
			result = p1.getTypeName().compareTo(p2.getTypeName());
			
			// compare type structures
			if(result==0)
			{	List<Parameter> s1 = p1.getSubParameters();
				List<Parameter> s2 = p2.getSubParameters();
				// compare the numbers of subparameters 
				if(s1==null && s2==null || s1.size()==s2.size())
					result = 0;
				// compare the subparameters themselves
				if(result==0 && s1!=null)
				{	int i=0;
					while(i<s1.size() && result==0)
					{	Parameter sp1 = s1.get(i);
						Parameter sp2 = s2.get(i);
						result = compareParameters(sp1, sp2, compareTypes);
						i++;
					}
				}
			}
		}
		
		return result;
	}
}
