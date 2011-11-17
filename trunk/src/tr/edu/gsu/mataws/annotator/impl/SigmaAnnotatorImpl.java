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
package tr.edu.gsu.mataws.annotator.impl;

import java.io.IOException;
import java.util.List;
import java.util.Queue;

import sine.col.Parameter;

import tr.edu.gsu.mataws.annotator.Annotator;
import tr.edu.gsu.mataws.components.Node;
import tr.edu.gsu.mataws.preprocessing.strategyGroups.PreprocessingSet;
import tr.edu.gsu.mataws.preprocessing.strategyGroups.impl.DefaultPreprocessing;
import tr.edu.gsu.mataws.toolbox.SigmaUtil;

import com.articulate.sigma.WordNet;

/**
 * This class is an implementation of Annotator in which 
 * Sigma KEE mappings is used for annotation.
 * 
 * @author Koray Mancuhan & Cihan Aksoy
 *
 */
public class SigmaAnnotatorImpl implements Annotator{

	private static SigmaAnnotatorImpl INSTANCE = null;
	
	private PreprocessingSet preprocessingSet;
	
	/**
	 * Constructs an instance of this class and loads
	 * mapping files into the memory.
	 */
	private SigmaAnnotatorImpl(){
		try {
			WordNet.initOnce();
			//concept taken with SigmaUtil 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		preprocessingSet = new DefaultPreprocessing();
	}
	
	/**
	 * Returns an instance of SigmaAnnotatorImpl class
	 */
	public static SigmaAnnotatorImpl getInstance(){
		if(INSTANCE == null)
			INSTANCE = new SigmaAnnotatorImpl();
		return INSTANCE;
	}
	
	@Override
	public void annotate(Queue<Node> queue, List<String> decompositionResult, List<String> annotationResult){
		boolean condition = true;
		while (condition) {
			Node aNode=queue.poll();
			Parameter parameter = aNode.getParameter();
			int level=aNode.getLevel();
			String aParamName = parameter.getName();
			preprocessingSet.processName(aParamName, decompositionResult);
			annotateDecomposedNames(decompositionResult, annotationResult);
			if (!isAnnotated(annotationResult)) {
				String typeName = parameter.getTypeName();
				if (!typeName.equals("")) {
					preprocessingSet.processName(typeName, decompositionResult);
					annotateDecomposedNames(decompositionResult, annotationResult);
					if (!isAnnotated(annotationResult)) {
						List<Parameter> subParamList = parameter.getSubParameters();
						if (subParamList != null) {
							level++;
							for (int i = 0; i < subParamList.size(); i++) {
								Node newNode=new Node(subParamList.get(i), level);
								queue.offer(newNode);
							}
						}
						else{
							condition=false;
						}
					}
					else{
						if(!isMoreNode(queue, aNode))
							condition=false;
					}
				}
				else
					condition=false;
			}
			else{
				if(!isMoreNode(queue, aNode))
					condition=false;
			}
		}
	}
	
	/**
	 * Service method verifying if there is any node left at same level in FIFO structure.
	 * 
	 * @param queue
	 * 			FIFO structure that holds the parameter and its sub parameters.
	 * @param actualNode
	 * 			Node representing the parameter that is actually processed by tool. 
	 * @return
	 * 			true if there are still some nodes at same level in FIFO structure.
	 */
	public boolean isMoreNode(Queue<Node> queue, Node actualNode){
		boolean result=true;
		int actualLevel=actualNode.getLevel();
		Node firstNode=queue.peek();
		if(firstNode==null)
			result=false;
		else{
			int nextNodeLevel=firstNode.getLevel();
			if(actualLevel!=nextNodeLevel)
				result=false;
		}
		return result;
	}
	
	/**
	 * Service method verifying if a parameter is annotated.
	 * 
	 * @param annotationResult
	 * 			list holding the result of annotation 
	 * @return
	 * 			true if the parameter is annotated
	 */
	public boolean isAnnotated(List<String> annotationResult) {
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
	
	/**
	 * Service method annotating each little word obtained after parameter name 
	 * decomposition, normalization and filtering.
	 * 
	 * @param decompositionResult
	 * 			list holding the result of name decomposition, normalization and filtering
	 * @param paramName
	 * 			parameter name
	 * @return
	 * 			the result of the annotation for each little word.
	 */
	private void annotateDecomposedNames(
			List<String> decompositionResult, List<String> annotationResult) {
		String conceptName;
		for (int i = 0; i < decompositionResult.size(); i++) {
			String decomposedName = decompositionResult.get(i);
			conceptName = SigmaUtil.findConcept(decomposedName);
			annotationResult.add(conceptName);
		}
	}
}
