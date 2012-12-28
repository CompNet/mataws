package tr.edu.gsu.mataws.component.reader.evaluations;

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
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

import tr.edu.gsu.mataws.data.parameter.AbstractMatawsParameter;
import tr.edu.gsu.mataws.data.stat.CollectionStats;
import tr.edu.gsu.mataws.data.stat.ParameterStats;
import tr.edu.gsu.mataws.tools.misc.FileTools;

/**
 * This class is used to read whatever is necessary
 * besides the description files themselves.
 * 
 * @author Vincent Labatut
 */
public class EvaluatedParameterReader extends AbstractOtherReader
{
	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	/** Input file for the evaluation data */
	private static final String FILE = "evaluation.txt";
	
	@Override
	public void read(CollectionStats stats) throws Exception
	{	logger.increaseOffset();
		List<ParameterStats> parameterStats = stats.getParameterInstanceStats();
		
		// opening 
		String path = FileTools.IN_OTHERS_FOLDER + File.separator + FILE;
		File file = new File(path);
		logger.log("Opening the file "+file.getName());
		FileInputStream fi = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(fi);
		Scanner scanner = new Scanner(isr);
		
		// reading
		logger.increaseOffset();
		int count = 0;
		if(scanner.hasNext())
		{	String msg = "";
		
			// get the text
			String line = scanner.nextLine();
			String temp[] = line.split("\t");
			int idx = 0;
			
			// id
			int id = Integer.parseInt(temp[idx]);
			msg = msg + id;
			idx++;
			
			// init stat
			ParameterStats stat = new ParameterStats(id);
			count++;
			
			// name
			String name = temp[idx];
			stat.setName(name);
			msg = msg + " " + name;
			idx++;
			
			// type name
			String typeName = temp[idx];
			stat.setTypeName(typeName);
			msg = msg + " " + typeName;
			idx++;
			
			// type name
			String operationName = temp[idx];
			stat.setOperationName(operationName);
			idx++;
			
			// occurrences
			int occurrences = Integer.parseInt(temp[idx]);
			stat.setOccurrences(occurrences);
			idx++;
			
			// representative word
			String representativeWord = temp[idx];
			if(representativeWord.equals(AbstractMatawsParameter.NO_WORD))
				representativeWord = null;
			stat.setRepresentativeWord(representativeWord);
			idx++;
			
			// concept
			String concept = temp[idx];
			boolean annotated = true;
			if(concept.equals(AbstractMatawsParameter.NO_CONCEPT))
			{	concept = null;
				annotated = false;
			}
			stat.setConcept(concept);
			stat.setAnnotated(annotated);
			idx++;
			
			// P vs. W
			float pvsW = Float.parseFloat(temp[idx]);
			stat.setPvsW(pvsW);
			idx++;
			
			// W vs. C
			float wvsC = Float.parseFloat(temp[idx]);
			stat.setWvsC(wvsC);
			idx++;
			
			// P vs. C
			float pvsC = Float.parseFloat(temp[idx]);
			stat.setPvsC(pvsC);
			idx++;
			
			parameterStats.add(stat);
			logger.log(msg);
		}
		logger.decreaseOffset();
		logger.log("Reading finished: "+count+"parameters processed");
		
		// closing
		logger.log("Closing the file "+file.getName());
		scanner.close();
		
		// updating the parameter stats thanks to the param stats
		logger.log("Updating unique parameter stats using parameter instance stats");
		stats.initUniqueFromInstanceParam();
		
		// updating the word stats thanks to the param stats
		logger.log("Updating word stats using parameter stats");
		stats.initWordFromParams();
		
		logger.decreaseOffset();
	}
}
