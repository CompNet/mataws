package tr.edu.gsu.mataws2.postprocessing;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tr.edu.gsu.mataws2.components.selector.AnalysisType;
import tr.edu.gsu.mataws2.trace.TraceableParameter;
import tr.edu.gsu.mataws2.zzzzz.Node;

/**
 * This class is an implementation of Statistics in which 
 * parameters and words of parameters of the collection, and their 
 * annotation informations are hold. 
 *   
 * @author Cihan Aksoy
 *
 */
public class StatisticsUtil {

	private static StatisticsUtil INSTANCE = null;
	
	private List<TraceableParameter> allParameterObjects;
	private List<Node> allNodeObjects;

	private List<TraceableParameter> allParameters;
	private List<TraceableParameter> annotatedParameters;
	private List<TraceableParameter> nonAnnotatedParameters;
	
	private List<TraceableParameter> allDifferentParameters;
	private List<TraceableParameter> differentAnnotatedParameters;
	private List<TraceableParameter> differentNonAnnotatedParameters;
	
	private Map<String, List<String>> parameterPreprocessingMap;
	private Map<String, String> parameterAnnotationMap;
	
	//control set to avoid holding same parameter with same name
	private List<String> allParameterNames;
	
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
		
		parameterPreprocessingMap = new HashMap<String, List<String>>();
		parameterAnnotationMap = new HashMap<String, String>();
		
		allParameterNames = new ArrayList<String>();
		
		analyzeTypesCounter = new HashMap<AnalysisType, Integer>();
		for(AnalysisType at: AnalysisType.values()){
			analyzeTypesCounter.put(at, 0);
		}
	}
	
	public static StatisticsUtil getInstance(){
		if(INSTANCE == null)
			INSTANCE = new StatisticsUtil();
		return INSTANCE;
	}
	
	public void calculateStatistics(TraceableParameter tparameter, List<String> preprocessingResult,
			String wordToAnnotate, AnalysisType analyzeType, String concept){
		
		/////////////////////////////////////////////////////////////////////
		//////////////PARAMETER STATISTICS///////////////////////////////////
		/////////////////////////////////////////////////////////////////////
		//each iteration counted and considered as annotated or not annotated
		allParameters.add(tparameter);
		if(!concept.equals("NoMatch"))
			annotatedParameters.add(tparameter);
		else
			nonAnnotatedParameters.add(tparameter);
		
		//uniquely holded parameters  
		if(!allParameterNames.contains(tparameter.getParameter().getName())){
			
			allParameterNames.add(tparameter.getParameter().getName());
			
			allDifferentParameters.add(tparameter);
			
			//This will be useful to extract which concepts are linked to any parameter
			parameterPreprocessingMap.put(tparameter.getParameter().getName(), preprocessingResult);
			parameterAnnotationMap.put(tparameter.getParameter().getName(), concept);
			
			if(!concept.equals("NoMatch"))
				differentAnnotatedParameters.add(tparameter);
			else
				differentNonAnnotatedParameters.add(tparameter);
		}
		
		/*Set<AnalysisType> set = analyzeTypesCounter.keySet(); 
		for (AnalysisType analyzeType2 : set) {
			if(analyzeType.equals(analyzeType2))
				analyzeTypesCounter.put(analyzeType2, 
						analyzeTypesCounter.get(analyzeType2)+1);
		}*/
		analyzeTypesCounter.put(analyzeType, 
				analyzeTypesCounter.get(analyzeType)+1);
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

	public void setAnnotatedParameters(List<TraceableParameter> annotatedParameters) {
		this.annotatedParameters = annotatedParameters;
	}

	public List<TraceableParameter> getNonAnnotatedParameters() {
		return nonAnnotatedParameters;
	}

	public void setNonAnnotatedParameters(List<TraceableParameter> nonAnnotatedParameters) {
		this.nonAnnotatedParameters = nonAnnotatedParameters;
	}

	public List<TraceableParameter> getAllDifferentParameters() {
		return allDifferentParameters;
	}

	public void setAllDifferentParameters(List<TraceableParameter> allDifferentParameters) {
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

	public Map<String, List<String>> getParameterPreprocessingMap() {
		return parameterPreprocessingMap;
	}

	public void setParameterPreprocessingMap(
			Map<String, List<String>> parameterPreprocessingMap) {
		this.parameterPreprocessingMap = parameterPreprocessingMap;
	}

	public void setParameterAnnotationMap(Map<String, String> parameterAnnotationMap) {
		this.parameterAnnotationMap = parameterAnnotationMap;
	}

	public Map<String, String> getParameterAnnotationMap() {
		return parameterAnnotationMap;
	}

	public Map<AnalysisType, Integer> getAnalyzeTypesCounter() {
		return analyzeTypesCounter;
	}
}
