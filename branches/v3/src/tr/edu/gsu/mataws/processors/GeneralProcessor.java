package tr.edu.gsu.mataws.processors;

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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.SortedSet;

import com.articulate.sigma.KBmanager;
import com.articulate.sigma.WordNet;

import tr.edu.gsu.mataws.components.core.Core;
import tr.edu.gsu.mataws.components.core.Core;
import tr.edu.gsu.mataws.components.core.associator.Associator;
import tr.edu.gsu.mataws.components.core.selector.AnalysisType;
import tr.edu.gsu.mataws.components.core.selector.Analyzer;
import tr.edu.gsu.mataws.components.io.reader.CollectionReader;
import tr.edu.gsu.mataws.components.io.reader.WsdlCollectionReader;
import tr.edu.gsu.mataws.components.io.writer.CollectionTransformationUtil;
import tr.edu.gsu.mataws.data.MatawsParameter;
import tr.edu.gsu.mataws.stats.StatData;
import tr.edu.gsu.mataws.stats.StatWritter;
import tr.edu.gsu.mataws.tools.FileTools;
import tr.edu.gsu.mataws.trace.TraceableParameter;
import tr.edu.gsu.mataws.zzzzz.Node;
import tr.edu.gsu.sine.col.Collection;
import tr.edu.gsu.sine.col.Parameter;

/**
 * @author Vincent Labatut
 */
public class GeneralProcessor
{	
	/**
	 * Implements the general algorithm for the 
	 * annotation process.
	 * 
	 */
	public static List<MatawsParameter> process(Collection collection)
	{	// init
		List<MatawsParameter> result = new ArrayList<MatawsParameter>();
		
		// process operations
		
		
		// process parameters
		
		
		// return results
		
	
		
		
		
		// load the syntactic descriptions
		CollectionReader reader = new WsdlCollectionReader();
		List<MatawsParameter> parameters = reader.readCollection(inFolder);
		
		// apply the core processing
		
		
		// record the semantic descriptions
		// record the statistics
		
		
		String[] collections = new File(System.getProperty("user.dir")
				+ File.separator + "input").list();
		for (int i = 0; i < collections.length; i++) {
			statistics
					.setAllParameterObjects(extractParameterCollection(collections[i]));
			statistics.setAllNodeObjects(createParameterNodes(statistics
					.getAllParameterObjects()));

			for (int j = 0; j < statistics.getAllParameterObjects().size(); j++) {
				Node node = statistics.getAllNodeObjects().get(j);
				TraceableParameter tparameter = node.getTraceableParameter();
				List<String> preprocessingResult = new ArrayList<String>();
				String wordToAnnotate = null;
				String concept = null;
				AnalysisType analysisType;
				String wordUsage;
				Queue<Node> queue = new LinkedList<Node>();
				queue.offer(node);

				preprocessingResult = core.process(queue);
				wordToAnnotate = analyzer.analyzeWords(tparameter,
						preprocessingResult);
				analysisType = analyzer.getAnalysisType();
				wordUsage = analyzer.getWordUsage(wordToAnnotate);
				concept = Associator.findConcept(wordToAnnotate, wordUsage);

				statistics.calculateStatistics(tparameter, preprocessingResult,
						wordToAnnotate, analysisType, concept);
				output.write(tparameter, preprocessingResult, wordToAnnotate,
						analysisType, concept);
			}

			output.save();

			colTransUtil = new CollectionTransformationUtil(collections[i]);
			colTransUtil.createSemanticCollection();
		}
	}

	/**
	 * Service method creating a parameter list in which each parameter is
	 * represented by a Parameter object.
	 * 
	 * @param collectionName
	 *            collection name to extract the parameter list.
	 * @return parameter list in which each parameter is represented by a
	 *         Parameter object.
	 * @throws Exception
	 *             indicates a problem if an error occurs during the creation of
	 *             parameter list.
	 */
	public static List<TraceableParameter> extractParameterCollection(String collectionName) throws Exception
	{	List<TraceableParameter> result = new ArrayList<TraceableParameter>();
		CollectionReader sineUtil = new CollectionReader();
		SortedSet<Parameter> sortedSet = sineUtil.readCollection(collectionName);
		Iterator<Parameter> iterator = sortedSet.iterator();
		while (iterator.hasNext())
		{	Parameter param = iterator.next();
			TraceableParameter tp = new TraceableParameter(param);
			result.add(tp);
		}
		return result;
	}

	/**
	 * Service method creating a parameter list in which each parameter is
	 * represented by a Node object.
	 * 
	 * @param allParameterObjects
	 *            a parameter list in which each parameter is represented by a
	 *            Parameter object
	 * @return a parameter list in which each parameter is represented by a Node
	 *         object.
	 */
	public static List<Node> createParameterNodes(List<TraceableParameter> allParameterObjects)
	{	List<Node> result = new ArrayList<Node>();
		for (int i = 0; i < allParameterObjects.size(); i++)
		{	Node node = new Node(allParameterObjects.get(i), 0);
			result.add(node);
		}
		return result;
	}
}
