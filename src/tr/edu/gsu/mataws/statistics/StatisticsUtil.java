/*
 * Mataws - Multimodal Automatic Tool for the Annotation of  Web Services
 * Copyright 2011 Cihan Aksoy & Koray Mançuhan
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
package tr.edu.gsu.mataws.statistics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import tr.edu.gsu.mataws.analyzer.AnalysisType;
import tr.edu.gsu.mataws.components.Node;
import tr.edu.gsu.mataws.components.TraceableParameter;
import tr.edu.gsu.mataws.toolbox.SigmaUtil;

/**
 * This class is an implementation of Statistics in which parameters and words
 * of parameters of the collection, and their annotation informations are hold.
 * 
 * @author Cihan Aksoy
 * 
 */
public class StatisticsUtil {

	private static StatisticsUtil INSTANCE = null;

	//variables for parameter statistics
	private List<TraceableParameter> allParameterObjects;
	private List<Node> allNodeObjects;

	private List<TraceableParameter> allParameters;
	private List<TraceableParameter> annotatedParameters;
	private List<TraceableParameter> nonAnnotatedParameters;

	private List<TraceableParameter> allDifferentParameters;
	private List<TraceableParameter> differentAnnotatedParameters;
	private List<TraceableParameter> differentNonAnnotatedParameters;
	
	//variables for word statistics
	private List<String> allWords;
	private List<String> annotatedWords;
	private List<String> nonAnnotatedWords;
	
	private List<String> allDifferentWords;
	private List<String> differentAnnotatedWords;
	private List<String> differentNonAnnotatedWords;

	private Map<String, String> parameterAnnotationMap;

	private Map<AnalysisType, Integer> analyzeTypesCounter;
	
	private StatisticsUtil() {

		allParameterObjects = new ArrayList<TraceableParameter>();
		allNodeObjects = new ArrayList<Node>();

		allParameters = new ArrayList<TraceableParameter>();
		annotatedParameters = new ArrayList<TraceableParameter>();
		nonAnnotatedParameters = new ArrayList<TraceableParameter>();

		allDifferentParameters = new ArrayList<TraceableParameter>();
		differentAnnotatedParameters = new ArrayList<TraceableParameter>();
		differentNonAnnotatedParameters = new ArrayList<TraceableParameter>();
		
		allWords = new ArrayList<String>();
		annotatedWords = new ArrayList<String>();
		nonAnnotatedWords = new ArrayList<String>();
		
		allDifferentWords = new ArrayList<String>();
		differentAnnotatedWords = new ArrayList<String>();
		differentNonAnnotatedWords = new ArrayList<String>();

		parameterAnnotationMap = new HashMap<String, String>();

		analyzeTypesCounter = new HashMap<AnalysisType, Integer>();
		for (AnalysisType at : AnalysisType.values()) {
			analyzeTypesCounter.put(at, 0);
		}
	}

	public static StatisticsUtil getInstance() {
		if (INSTANCE == null)
			INSTANCE = new StatisticsUtil();
		return INSTANCE;
	}

	public void calculateStatistics(TraceableParameter tparameter,
			List<String> preprocessingResult, String wordToAnnotate,
			AnalysisType analyzeType, String concept) {

		/////////////////////////////////////////////////////////////////////
		//////////////PARAMETER STATISTICS///////////////////////////////////
		/////////////////////////////////////////////////////////////////////
		// each iteration counted and considered as annotated or not annotated
		allParameters.add(tparameter);
		if (!concept.equals("NoMatch"))
			annotatedParameters.add(tparameter);
		else
			nonAnnotatedParameters.add(tparameter);

		if (!isFoundInUniqueParams(tparameter)) {
			allDifferentParameters.add(tparameter);

			if (!concept.equals("NoMatch"))
				differentAnnotatedParameters.add(tparameter);
			else
				differentNonAnnotatedParameters.add(tparameter);

			// This will be useful to extract which concepts are linked to any
			// parameter
			parameterAnnotationMap.put(tparameter.getParameter().getName(),
					concept);
		}
		
		/////////////////////////////////////////////////////////////////////
		//////////////WORD STATISTICS////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////
		for (String string : preprocessingResult) {
			allWords.add(string);
			if(!SigmaUtil.findConcept(string).equals("NoMatch"))
				annotatedWords.add(string);
			else
				nonAnnotatedWords.add(string);
			
			if(!allDifferentWords.contains(string)){
				allDifferentWords.add(string);
				if(!SigmaUtil.findConcept(string).equals("NoMatch"))
					differentAnnotatedWords.add(string);
				else
					differentNonAnnotatedWords.add(string);
			}
		}
		
		
		//analysis type statistics
		Set<AnalysisType> set = analyzeTypesCounter.keySet();
		for (AnalysisType analyzeType2 : set) {
			if (analyzeType.equals(analyzeType2))
				analyzeTypesCounter.put(analyzeType2,
						analyzeTypesCounter.get(analyzeType2) + 1);
		}
		analyzeTypesCounter.put(analyzeType,
				analyzeTypesCounter.get(analyzeType) + 1);
	}

	public List<TraceableParameter> getAllParameterObjects() {
		return allParameterObjects;
	}

	public void setAllParameterObjects(List<TraceableParameter> allParams) {
		this.allParameterObjects = allParams;
	}

	public List<Node> getAllNodeObjects() {
		return allNodeObjects;
	}

	public void setAllNodeObjects(List<Node> allNodes) {
		this.allNodeObjects = allNodes;
	}

	public List<TraceableParameter> getAllParameters() {
		return allParameters;
	}

	public void setAllParameters(List<TraceableParameter> allParameters) {
		this.allParameters = allParameters;
	}

	public List<TraceableParameter> getAnnotatedParameters() {
		return annotatedParameters;
	}

	public void setAnnotatedParameters(
			List<TraceableParameter> annotatedParameters) {
		this.annotatedParameters = annotatedParameters;
	}

	public List<TraceableParameter> getNonAnnotatedParameters() {
		return nonAnnotatedParameters;
	}

	public void setNonAnnotatedParameters(
			List<TraceableParameter> nonAnnotatedParameters) {
		this.nonAnnotatedParameters = nonAnnotatedParameters;
	}

	public List<TraceableParameter> getAllDifferentParameters() {
		return allDifferentParameters;
	}

	public void setAllDifferentParameters(
			List<TraceableParameter> allDifferentParameters) {
		this.allDifferentParameters = allDifferentParameters;
	}

	public List<TraceableParameter> getDifferentAnnotatedParameters() {
		return differentAnnotatedParameters;
	}

	public void setDifferentAnnotatedParameters(
			List<TraceableParameter> differentAnnotatedParameters) {
		this.differentAnnotatedParameters = differentAnnotatedParameters;
	}

	public List<TraceableParameter> getDifferentNonAnnotatedParameters() {
		return differentNonAnnotatedParameters;
	}

	public void setDifferentNonAnnotatedParameters(
			List<TraceableParameter> differentNonAnnotatedParameters) {
		this.differentNonAnnotatedParameters = differentNonAnnotatedParameters;
	}

	public void setParameterAnnotationMap(
			Map<String, String> parameterAnnotationMap) {
		this.parameterAnnotationMap = parameterAnnotationMap;
	}

	public Map<String, String> getParameterAnnotationMap() {
		return parameterAnnotationMap;
	}

	public Map<AnalysisType, Integer> getAnalyzeTypesCounter() {
		return analyzeTypesCounter;
	}

	public List<String> getAllWords() {
		return allWords;
	}

	public void setAllWords(List<String> allWords) {
		this.allWords = allWords;
	}

	public List<String> getAnnotatedWords() {
		return annotatedWords;
	}

	public void setAnnotatedWords(List<String> annotatedWords) {
		this.annotatedWords = annotatedWords;
	}

	public List<String> getNonAnnotatedWords() {
		return nonAnnotatedWords;
	}

	public void setNonAnnotatedWords(List<String> nonAnnotatedWords) {
		this.nonAnnotatedWords = nonAnnotatedWords;
	}

	public List<String> getAllDifferentWords() {
		return allDifferentWords;
	}

	public void setAllDifferentWords(List<String> allDifferentWords) {
		this.allDifferentWords = allDifferentWords;
	}

	public List<String> getDifferentAnnotatedWords() {
		return differentAnnotatedWords;
	}

	public void setDifferentAnnotatedWords(List<String> differentAnnotatedWords) {
		this.differentAnnotatedWords = differentAnnotatedWords;
	}

	public List<String> getDifferentNonAnnotatedWords() {
		return differentNonAnnotatedWords;
	}

	public void setDifferentNonAnnotatedWords(
			List<String> differentNonAnnotatedWords) {
		this.differentNonAnnotatedWords = differentNonAnnotatedWords;
	}

	public void setAnalyzeTypesCounter(
			Map<AnalysisType, Integer> analyzeTypesCounter) {
		this.analyzeTypesCounter = analyzeTypesCounter;
	}

	private boolean isFoundInUniqueParams(TraceableParameter tp) {
		for(TraceableParameter tparam: allDifferentParameters){
			if (tparam.getParameter().getName()
					.equals(tp.getParameter().getName())
					&& tparam.getParameter().getTypeName()
							.equals(tp.getParameter().getTypeName())) {
				if (tparam.getParameter().getConceptURI() != null
						&& tp.getParameter().getConceptURI() != null) {
					if (!tparam.getParameter().getConceptURI()
							.equals(tp.getParameter().getConceptURI()))
						return false;
				}
				if (tparam.getParameter().getSubParameters() != null
						&& tp.getParameter().getSubParameters() != null) {
					if (!tparam.getParameter().getSubParameters()
							.equals(tp.getParameter().getSubParameters()))
						return false;
				}
				return true;
			}
		}
		return false;
	}
}
