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

import java.util.List;
import java.util.Map;

import sine.col.Parameter;
import tr.edu.gsu.mataws.components.AnnotatedParameter;
import tr.edu.gsu.mataws.components.Node;

/**
 * Interface for various statistics tools.
 *   
 * @author Cihan Aksoy
 *
 */
public interface Statistics {

	/**
	 * Method takes the preprocessing and annotation results 
	 * for a given parameter and adds the necessary information 
	 * in an instance of this class.  
	 * 
	 * @param parameter
	 * @param decompositionResult
	 * @param annotationResult
	 */
	public void calculateStatistics(Parameter parameter, List<String> decompositionResult,
			List<String> annotationResult);
	
	/**
	 * Method for getting the parameters of the collection.
	 * 
	 * @return a list of parameters
	 */
	public List<Parameter> getAllParameterObjects();
	
	/**
	 * Method for defining the parameters of the collection 
	 * into an instance of this class.
	 * 
	 * @param allParams
	 */
	public void setAllParameterObjects(List<Parameter> allParams);
	
	/**
	 * Method for getting the parameters of the collection 
	 * in the form of Node
	 * 
	 * @return a list of Node
	 */
	public List<Node> getAllNodeObjects();
	
	/**
	 * Method for defining node forms of parameters of the collection 
	 * into an instance of this class.
	 * 
	 * @param allNodes
	 */
	public void setAllNodeObjects(List<Node> allNodes);
	
	/**
	 * Method for getting parameters of the collection with their 
	 * annotations in the form of Annotated Parameter
	 * 
	 * @return a list of Annotated Parameter
	 */
	public List<AnnotatedParameter> getAllAnnotatedParameterObjects();
	
	/**
	 * Method for defining annotated forms of parameters of the collection 
	 * into an instance of this class.
	 * 
	 * @param allAnnotatedParams
	 */
	public void setAllAnnotatedParameterObjects(
			List<AnnotatedParameter> allAnnotatedParams);
	
	/**
	 * Returns the parameters of the collection.
	 * 
	 * @return a list of parameters
	 */
	public List<Parameter> getAllParameters();
	
	/**
	 * Sets a list of parameters for the collection 
	 * into an instance of this class. 
	 * 
	 * @param allParameters
	 */
	public void setAllParameters(List<Parameter> allParameters);

	/**
	 * Returns successfully annotated parameter list.
	 * 
	 * @return a list of parameters
	 */
	public List<Parameter> getAnnotatedParameters();
	
	/**
	 * Sets annotated parameters for the collection 
	 * into an instance of this class.
	 * 
	 * @param annotatedParameters
	 */
	public void setAnnotatedParameters(List<Parameter> annotatedParameters);

	/**
	 * Returns parameter list for which annotation process failed.
	 * 
	 * @return a list of parameters
	 */
	public List<Parameter> getNonAnnotatedParameters();
	public void setNonAnnotatedParameters(List<Parameter> nonAnnotatedParameters);

	/**
	 * Returns a list of parameters in which a parameter of the collection 
	 * can be found at most once. 
	 * 
	 * @return a list of parameters
	 */
	public List<Parameter> getAllDifferentParameters();
	public void setAllDifferentParameters(List<Parameter> allDifferentParameters);

	/**
	 * Returns a list of annotated parameters in which a parameter of the 
	 * collection can be found at most once.
	 * 
	 * @return a list of parameters
	 */
	public List<Parameter> getDifferentAnnotatedParameters();
	public void setDifferentAnnotatedParameters(
			List<Parameter> differentAnnotatedParameters);

	/**
	 * Returns a list of non-annotated parameters in which a parameter of the 
	 * collection can be found at most once.
	 * 
	 * @return a list of parameters
	 */
	public List<Parameter> getDifferentNonAnnotatedParameters();
	public void setDifferentNonAnnotatedParameters(
			List<Parameter> differentNonAnnotatedParameters);

	/**
	 * Returns all words obtained from preprocessing operations 
	 * of the parameters of the collection.
	 * 
	 * @return a list of words
	 */
	public List<String> getAllWords();
	public void setAllWords(List<String> allWords);

	/**
	 * Returns all annotated words obtained from preprocessing operations 
	 * of the parameters of the collection.
	 * 
	 * @return a list of words
	 */
	public List<String> getAnnotatedWords();
	public void setAnnotatedWords(List<String> annotatedWords);

	/**
	 * Returns all non-annotated words obtained from preprocessing operations 
	 * of the parameters of the collection.
	 * 
	 * @return a list of words
	 */
	public List<String> getNonAnnotatedWords();
	public void setNonAnnotatedWords(List<String> nonAnnotatedWords);

	/**
	 * Returns uniquely held words obtained from preprocessing operations 
	 * of the parameters of the collection.
	 * 
	 * @return a list of words
	 */
	public List<String> getAllDifferentWords();
	public void setAllDifferentWords(List<String> allDifferentWords);
	
	/**
	 * Returns uniquely held annotated words obtained from preprocessing operations 
	 * of the parameters of the collection.
	 * 
	 * @return a list of words
	 */
	public List<String> getDifferentAnnotatedWords();
	public void setDifferentAnnotatedWords(List<String> differentAnnotatedWords);

	/**
	 * Returns uniquely held non-annotated words obtained from preprocessing operations 
	 * of the parameters of the collection.
	 * 
	 * @return a list of words
	 */
	public List<String> getDifferentNonAnnotatedWords();
	public void setDifferentNonAnnotatedWords(List<String> differentNonAnnotatedWords);

	/**
	 * Returns a mapping from parameter name to its relevant preprocessing results.
	 * 
	 * @return map
	 */
	public Map<String, List<String>> getParameterPreprocessingMap();
	public void setParameterPreprocessingMap(
			Map<String, List<String>> parameterPreprocessingMap);

	/**
	 * Returns a mapping from parameter name to its relevant annotation results.
	 * 
	 * @return map
	 */
	public Map<String, List<String>> getParameterAnnotationMap();
	public void setParameterAnnotationMap(
			Map<String, List<String>> parameterAnnotationMap);
}
