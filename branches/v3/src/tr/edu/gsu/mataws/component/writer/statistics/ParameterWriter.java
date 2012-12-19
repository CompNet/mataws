package tr.edu.gsu.mataws.component.writer.statistics;

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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import tr.edu.gsu.mataws.data.parameter.MatawsParameter;
import tr.edu.gsu.mataws.tools.misc.CollectionTools;
import tr.edu.gsu.mataws.tools.misc.FileTools;
import tr.edu.gsu.sine.col.Parameter;

/**
 * 
 * @author Vincent Labatut
 */
public class ParameterWriter extends AbstractStatisticsWriter
{	
	/**
	 * Builds a writer able to record parameter instances
	 * and their associated statistics.
	 */
	public ParameterWriter()
	{	super();
	}
	
	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	/** Parameter instances file */
	private final static String INSTANCE_FILE = "parameter.instances.txt";
	/** Unique parmeters file */
	private final static String UNIQUE_FILE = "parameter.unique.txt";
	
	@Override
	public void write(String subfolder, List<MatawsParameter> parameters) throws IOException
	{	logger.log("Writting the list of parameter instances");
		logger.increaseOffset();
	
		// init path
		logger.log("Setting path");
		String outputPath = FileTools.OUT_STATS_FOLDER;
		File outFolder = new File(outputPath);
		if(!outFolder.exists())
			outFolder.mkdir();
		
		// order parameters
		logger.log("Ordering parameters alphabetically");
		TreeSet<MatawsParameter> parameterSet = new TreeSet<MatawsParameter>(new Comparator<MatawsParameter>()
		{	@Override
			public int compare(MatawsParameter o1, MatawsParameter o2)
			{	Parameter p1 = o1.getSineParameter();
				Parameter p2 = o2.getSineParameter();
				int result = CollectionTools.compareParameters(p1, p2, true);
				
				return result;
			}	
		});
		parameterSet.addAll(parameters);
		
		// open the output file
		logger.log("Starting writting ("+parameters.size()+" parameters)");
		FileOutputStream fos = new FileOutputStream(outputPath + File.separator + FILE);
		OutputStreamWriter writer = new OutputStreamWriter(fos);
		PrintWriter printWriter = new PrintWriter(writer);
		
		// write each parameter instance
		for(MatawsParameter parameter: parameterSet)
		{	String name = parameter.getSineParameter().getName();
			String concept = parameter.getConcept();
			
			printWriter.print("blablablabla");
		}

		// close output file
		fos.close();

		logger.log("Writting finished for the collection");
		logger.decreaseOffset();
	}
}
