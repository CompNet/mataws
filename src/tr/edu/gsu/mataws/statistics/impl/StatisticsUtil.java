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
package tr.edu.gsu.mataws.statistics.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sine.col.Parameter;
import tr.edu.gsu.mataws.components.AnnotatedParameter;
import tr.edu.gsu.mataws.components.Node;
import tr.edu.gsu.mataws.statistics.Statistics;

/**
 * This class is an implementation of Statistics in which 
 * parameters and words of parameters of the collection, and their 
 * annotation informations are hold. 
 *   
 * @author Cihan Aksoy
 *
 */
public class StatisticsUtil implements Statistics{

	private static StatisticsUtil INSTANCE = null;
	
	private List<Parameter> allParameterObjects;
	private List<Node> allNodeObjects;
	private List<AnnotatedParameter> allAnnotatedParameterObjects;

	private List<Parameter> allParameters;
	private List<Parameter> annotatedParameters;
	private List<Parameter> nonAnnotatedParameters;
	
	private List<Parameter> allDifferentParameters;
	private List<Parameter> differentAnnotatedParameters;
	private List<Parameter> differentNonAnnotatedParameters;
	
	private List<String> allWords;
	private List<String> annotatedWords;
	private List<String> nonAnnotatedWords;
	
	private List<String> allDifferentWords;
	private List<String> differentAnnotatedWords;
	private List<String> differentNonAnnotatedWords;
	
	//sine param doesn't correspond with owls api param
	//private Map<Parameter, List<String>> parameterDecompositionMap;
	//private Map<Parameter, List<String>> parameterAnnotationMap;
	//private Map<Parameter, String> transformedParameter;
	
	private Map<String, List<String>> parameterPreprocessingMap;
	private Map<String, List<String>> parameterAnnotationMap;
	
	//control set to avoid holding same parameter with same name
	private List<String> allParameterNames;
	
	private StatisticsUtil() {
		
		allParameterObjects = new ArrayList<Parameter>();
		allNodeObjects = new ArrayList<Node>();
		allAnnotatedParameterObjects = new ArrayList<AnnotatedParameter>();
		
		allParameters = new ArrayList<Parameter>();
		annotatedParameters = new ArrayList<Parameter>();
		nonAnnotatedParameters = new ArrayList<Parameter>();
		
		allDifferentParameters = new ArrayList<Parameter>();
		differentAnnotatedParameters = new ArrayList<Parameter>();
		differentNonAnnotatedParameters = new ArrayList<Parameter>();
		
		allWords = new ArrayList<String>();
		annotatedWords = new ArrayList<String>();
		nonAnnotatedWords = new ArrayList<String>();
		
		allDifferentWords = new ArrayList<String>();
		differentAnnotatedWords = new ArrayList<String>();
		differentNonAnnotatedWords = new ArrayList<String>();
		
		parameterPreprocessingMap = new HashMap<String, List<String>>();
		parameterAnnotationMap = new HashMap<String, List<String>>();
		//transformedParameter = new HashMap<Parameter, String>();
		
		allParameterNames = new ArrayList<String>();
	}
	
	public static StatisticsUtil getInstance(){
		if(INSTANCE == null)
			INSTANCE = new StatisticsUtil();
		return INSTANCE;
	}
	
	@Override
	public void calculateStatistics(Parameter parameter, List<String> decompositionResult,
			List<String> annotationResult){
		
		/////////////////////////////////////////////////////////////////////
		//////////////PARAMETER STATISTICS///////////////////////////////////
		/////////////////////////////////////////////////////////////////////
		//each iteration counted and considered as annotated or not annotated
		allParameters.add(parameter);
		if(isParameterAnnotated(annotationResult))
			annotatedParameters.add(parameter);
		else
			nonAnnotatedParameters.add(parameter);
		
		//uniquely holded parameters  
		if(!allParameterNames.contains(parameter.getName())){
			
			allParameterNames.add(parameter.getName());
			
			allDifferentParameters.add(parameter);
			
			//This will be useful to extract which concepts are linked to any parameter
			parameterPreprocessingMap.put(parameter.getName(), decompositionResult);
			parameterAnnotationMap.put(parameter.getName(), annotationResult);
			//transformedParameter.put(parameter, parameter.getName());
			
			if(isParameterAnnotated(annotationResult))
				differentAnnotatedParameters.add(parameter);
			else
				differentNonAnnotatedParameters.add(parameter);
		}
		
		/////////////////////////////////////////////////////////////////////
		//////////////WORD STATISTICS////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////
		for (String string : decompositionResult) {
			//each iteration counted and considered as annotated or not annotated
			allWords.add(string);
			if(!annotationResult.get(decompositionResult.indexOf(string)).equals("NoMatch"))
				annotatedWords.add(string);
			else
				nonAnnotatedWords.add(string);
			
			//words are hold differently 
			if(!allDifferentWords.contains(string)){
				allDifferentWords.add(string);
				
				if(!annotationResult.get(decompositionResult.indexOf(string)).equals("NoMatch"))
					differentAnnotatedWords.add(string);
				else
					differentNonAnnotatedWords.add(string);
			}
		}
		
		//AnnotatedParameter objects created
		AnnotatedParameter annotatedParameter = new AnnotatedParameter(parameter.getName());
		for (String string : annotationResult) {
			annotatedParameter.addConcept(string);
		}
		allAnnotatedParameterObjects.add(annotatedParameter);
	}

	public List<Parameter> getAllParameterObjects() {
		return allParameterObjects;
	}

	public void setAllParameterObjects(List<Parameter> allParams) {
		this.allParameterObjects = allParams;
	}

	public List<Node> getAllNodeObjects() {
		return allNodeObjects;
	}

	public void setAllNodeObjects(List<Node> allNodes) {
		this.allNodeObjects = allNodes;
	}

	public List<AnnotatedParameter> getAllAnnotatedParameterObjects() {
		return allAnnotatedParameterObjects;
	}

	public void setAllAnnotatedParameterObjects(
			List<AnnotatedParameter> allAnnotatedParams) {
		this.allAnnotatedParameterObjects = allAnnotatedParams;
	}
	
	public List<Parameter> getAllParameters() {
		return allParameters;
	}

	public void setAllParameters(List<Parameter> allParameters) {
		this.allParameters = allParameters;
	}

	public List<Parameter> getAnnotatedParameters() {
		return annotatedParameters;
	}

	public void setAnnotatedParameters(List<Parameter> annotatedParameters) {
		this.annotatedParameters = annotatedParameters;
	}

	public List<Parameter> getNonAnnotatedParameters() {
		return nonAnnotatedParameters;
	}

	public void setNonAnnotatedParameters(List<Parameter> nonAnnotatedParameters) {
		this.nonAnnotatedParameters = nonAnnotatedParameters;
	}

	public List<Parameter> getAllDifferentParameters() {
		return allDifferentParameters;
	}

	public void setAllDifferentParameters(List<Parameter> allDifferentParameters) {
		this.allDifferentParameters = allDifferentParameters;
	}

	public List<Parameter> getDifferentAnnotatedParameters() {
		return differentAnnotatedParameters;
	}

	public void setDifferentAnnotatedParameters(
			List<Parameter> differentAnnotatedParameters) {
		this.differentAnnotatedParameters = differentAnnotatedParameters;
	}

	public List<Parameter> getDifferentNonAnnotatedParameters() {
		return differentNonAnnotatedParameters;
	}

	public void setDifferentNonAnnotatedParameters(
			List<Parameter> differentNonAnnotatedParameters) {
		this.differentNonAnnotatedParameters = differentNonAnnotatedParameters;
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

	public void setDifferentNonAnnotatedWords(List<String> differentNonAnnotatedWords) {
		this.differentNonAnnotatedWords = differentNonAnnotatedWords;
	}

	public Map<String, List<String>> getParameterPreprocessingMap() {
		return parameterPreprocessingMap;
	}

	public void setParameterPreprocessingMap(
			Map<String, List<String>> parameterPreprocessingMap) {
		this.parameterPreprocessingMap = parameterPreprocessingMap;
	}

	public Map<String, List<String>> getParameterAnnotationMap() {
		return parameterAnnotationMap;
	}

	public void setParameterAnnotationMap(
			Map<String, List<String>> parameterAnnotationMap) {
		this.parameterAnnotationMap = parameterAnnotationMap;
	}

	/**
	 * Service method verifying if a parameter is annotated.
	 * 
	 * @param annotationResult
	 * 			list holding the result of annotation 
	 * @return
	 * 			true if the parameter is annotated
	 */
	public boolean isParameterAnnotated(List<String> annotationResult) {
		boolean result = true;
		int noMatchCount = 0;
		for (int i = 0; i < annotationResult.size(); i++) {
			String conceptName = annotationResult.get(i);
			if (conceptName.equals("NoMatch")) {
				noMatchCount++;
			}
		}
		if (noMatchCount == annotationResult.size()) {
			result = false;
		}
		return result;
	}
}
