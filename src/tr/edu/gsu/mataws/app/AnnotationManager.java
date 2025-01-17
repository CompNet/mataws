/*
 * Mataws - Multimodal Automatic Tool for the Annotation of  Web Services
 * Copyright 2011 Cihan Aksoy & Koray Man�uhan
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
package tr.edu.gsu.mataws.app;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.SortedSet;

import sine.col.Parameter;
import tr.edu.gsu.mataws.analyzer.AnalysisType;
import tr.edu.gsu.mataws.analyzer.Analyzer;
import tr.edu.gsu.mataws.components.Node;
import tr.edu.gsu.mataws.components.TraceableParameter;
import tr.edu.gsu.mataws.core.Core;
import tr.edu.gsu.mataws.core.impl.CoreImpl;
import tr.edu.gsu.mataws.output.Output;
import tr.edu.gsu.mataws.output.impl.CsvOutputImpl;
import tr.edu.gsu.mataws.output.impl.TextOutputImpl;
import tr.edu.gsu.mataws.statistics.StatisticsUtil;
import tr.edu.gsu.mataws.toolbox.CollectionTransformationUtil;
import tr.edu.gsu.mataws.toolbox.SigmaUtil;
import tr.edu.gsu.mataws.toolbox.SineUtil;

/**
 * This class manages annotation process by interacting with annotator,
 * statistics and output components.
 * 
 * @author Koray Mancuhan & Cihan Aksoy
 * 
 */
public class AnnotationManager {

	private StatisticsUtil statistics;
	private Output txt_output;
	private Output csv_output;
	private Core core;
	private Analyzer analyzer;
	private CollectionTransformationUtil colTransUtil;

	private static AnnotationManager INSTANCE = null;

	/**
	 * Constructs an instance of this class and gets an instance of necessary
	 * classes.
	 */
	private AnnotationManager() {
		statistics = StatisticsUtil.getInstance();
		txt_output = new TextOutputImpl();
		csv_output = new CsvOutputImpl();
		analyzer = new Analyzer();
		core = CoreImpl.getInstance();
	}

	/**
	 * Returns an instance of AnnotationManager
	 */
	public static AnnotationManager getInstance() {
		if (INSTANCE == null)
			INSTANCE = new AnnotationManager();
		return INSTANCE;
	}

	/**
	 * Realizes all processes of MATAWS including annotation, statistics,
	 * output, sws generation
	 * 
	 * @throws Exception
	 */
	public void startProcess() throws Exception {

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
				concept = SigmaUtil.findConcept(wordToAnnotate, wordUsage);

				statistics.calculateStatistics(tparameter, preprocessingResult,
						wordToAnnotate, analysisType, concept);
				txt_output.write(tparameter, preprocessingResult, wordToAnnotate,
						analysisType, concept);
				csv_output.write(tparameter, preprocessingResult, wordToAnnotate,
						analysisType, concept);
			}

			txt_output.save();
			csv_output.save();
			
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
	public List<TraceableParameter> extractParameterCollection(
			String collectionName) throws Exception {
		List<TraceableParameter> result = new ArrayList<TraceableParameter>();
		SineUtil sineUtil = new SineUtil();
		SortedSet<Parameter> sortedSet = sineUtil
				.initializeParameterList(collectionName);
		Iterator<Parameter> iterator = sortedSet.iterator();
		while (iterator.hasNext()) {
			Parameter param = iterator.next();
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
	public List<Node> createParameterNodes(
			List<TraceableParameter> allParameterObjects) {
		List<Node> result = new ArrayList<Node>();
		for (int i = 0; i < allParameterObjects.size(); i++) {
			Node node = new Node(allParameterObjects.get(i), 0);
			result.add(node);
		}
		return result;
	}
}
