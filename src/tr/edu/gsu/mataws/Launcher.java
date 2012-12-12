package tr.edu.gsu.mataws;

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

import tr.edu.gsu.mataws.component.associator.Associator;
import tr.edu.gsu.mataws.component.core.Core;
import tr.edu.gsu.mataws.component.reader.collection.CollectionReaderInterface;
import tr.edu.gsu.mataws.component.reader.collection.WsdlCollectionReader;
import tr.edu.gsu.mataws.component.selector.AnalysisType;
import tr.edu.gsu.mataws.component.selector.Analyzer;
import tr.edu.gsu.mataws.component.writer.CollectionTransformationUtil;
import tr.edu.gsu.mataws.data.MatawsParameter;
import tr.edu.gsu.mataws.stats.StatData;
import tr.edu.gsu.mataws.stats.StatWritter;
import tr.edu.gsu.mataws.tools.misc.FileTools;
import tr.edu.gsu.mataws.trace.TraceableParameter;
import tr.edu.gsu.mataws.zzzzz.Node;
import tr.edu.gsu.sine.col.Collection;
import tr.edu.gsu.sine.col.Parameter;

/**
 * This class is used to run the main program,
 * performing the semantic annotation of the 
 * WSDL files contained in the input folder.
 * 
 * @author Cihan Aksoy
 * @author Koray Mancuhan
 * @author Vincent Labatut
 */
public class Launcher
{	/**
	 * Launch the annotation process for the WSDL
	 * contained in the input folder.
	 * 
	 * @param args
	 * 		Possibly the name of a subfolder of Mataws input folder.
	 * 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException
	{	if(args.length>0)
			inFolder = args[0];
		
		init();
		process();
	}

	///////////////////////////////////////////////////////////
	//	INITIALIZATION						///////////////////
	///////////////////////////////////////////////////////////
	private static String inFolder = null;
	
	private static StatData statistics;
	private static StatWritter statWriter;
	private static Core core;
	private static Analyzer analyzer;
	private static CollectionTransformationUtil colTransUtil;

	/**
	 * Intializes the necessary objects.
	 * @throws IOException 
	 */
	private static void init() throws IOException
	{	// init sigma
		
		
		statistics = StatData.getInstance();
		statWriter = new StatWritter();
		analyzer = new Analyzer();
		core = Core.getInstance();
	}
	
	/**
	 * Performs the complete annotation process, 
	 * including annotation, statistics,
	 * output, sws generation.
	 * 
	 * @throws Exception
	 */
	public static void process()
	{	
		// load the syntactic descriptions
		CollectionReaderInterface reader = new WsdlCollectionReader();
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
		CollectionReaderInterface sineUtil = new CollectionReaderInterface();
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


/* TODO TODO
 * changes:
 * - new architecture
 * - preprocessor: becomes 'preparator'
 * 		- splitter:
 * 			- possibility to use a different lexicon
 * 		- normalizer:
 * 			- we don't change case anymore, because it conveys information (acronyms, etc.)
 * 				- is it sure ? TODO
 *				- it can still be done later, e.g. just before sigma TODO
 *			- new alternate stem normalizer.
 *				- we don't use it anymore neither, because of the changes in the selector.
 * 			- unlike the explanations in the paper, the diacritic marks are removed in the normalization phase, and not in the split phase.
 * 				- TODO not impletemented yet
 * 		- filter:
 * 			- new redundancy filter
 * 			- new length filter (to avoid having letter-parameters)
 * 		- identifier:
 * 			- new component outputing the word and the synset, to avoid information loss
 * - selector:
 * 		- unlike what is said in the paper, the frequency returned by wordnet is only relative 
 * 		  and concerns the other meanings of the considered word (i.e. it allows knowing only 
 * 		  which meaning is the most frequent).
 * 		- creation of the notion of simplifier. other steps could be used if needed. 
 * - associator:
 * 		- no more syntactic work: the synset allows retrieving the concept without any ambiguity
 * 		- some concepts returned by sigma are actually the opposite of the considered words >> those are ignored
 * 		- some words in WordNet 3 are not annotated in sigma
 * - operation:
 * 		- takes advantage of the operation names
 * 
 * 
 * others:
 * - it might be interesting not to use the stems in the preprocessing,
 *   because the form of the word can be used to determine its grammatical type
 *   and/or meaning. so this cleaning should be let to a latter time 
 *   (might still be required for Sigma)
 * - i removed all acronyms from the "abbreviations" list. 
 * 		> see if WN can retrieve full words from acronyms, even when in lower case.
 * 		> maybe acronyms should be processed using a separate list
 *   
 *   
 * - problems:
 * 		- manual evaluation (incomplete, imprecise, inconsistant)
 * 		- process related to evaluation (averages, etc.)
 * 		- operation names not considered
 * 		- software not working as it should (not consistant with the paper)
 * 		- frequency is not absolute but relative to the word
 * 		- hypernyms processing: only consider the first hypernym
 * 
 * - todo:
 * 		- include the log to get a trace
 */
