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

import sine.col.Parameter;
import tr.edu.gsu.mataws.analyzer.AnalysisType;
import tr.edu.gsu.mataws.components.AnnotatedParameter;
import tr.edu.gsu.mataws.components.Node;

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
	
	private Map<String, List<String>> parameterPreprocessingMap;
	private Map<String, String> parameterAnnotationMap;
	
	//control set to avoid holding same parameter with same name
	private List<String> allParameterNames;
	
	private Map<AnalysisType, Integer> analyzeTypesCounter;
	
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
	
	public void calculateStatistics(Parameter parameter, List<String> preprocessingResult,
			String wordToAnnotate, AnalysisType analyzeType, String concept){
		
		/////////////////////////////////////////////////////////////////////
		//////////////PARAMETER STATISTICS///////////////////////////////////
		/////////////////////////////////////////////////////////////////////
		//each iteration counted and considered as annotated or not annotated
		allParameters.add(parameter);
		if(!concept.equals("NoMatch"))
			annotatedParameters.add(parameter);
		else
			nonAnnotatedParameters.add(parameter);
		
		//uniquely holded parameters  
		if(!allParameterNames.contains(parameter.getName())){
			
			allParameterNames.add(parameter.getName());
			
			allDifferentParameters.add(parameter);
			
			//This will be useful to extract which concepts are linked to any parameter
			parameterPreprocessingMap.put(parameter.getName(), preprocessingResult);
			parameterAnnotationMap.put(parameter.getName(), concept);
			
			if(!concept.equals("NoMatch"))
				differentAnnotatedParameters.add(parameter);
			else
				differentNonAnnotatedParameters.add(parameter);
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
