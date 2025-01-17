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
package tr.edu.gsu.mataws.core.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import sine.col.Parameter;
import tr.edu.gsu.mataws.components.Node;
import tr.edu.gsu.mataws.components.TraceableParameter;
import tr.edu.gsu.mataws.core.Core;
import tr.edu.gsu.mataws.preprocessing.PreprocessingStrategy;
import tr.edu.gsu.mataws.preprocessing.decomposition.impl.WithoutSpecialCharDecomposition;
import tr.edu.gsu.mataws.preprocessing.purification.impl.JawsPurificationImpl;
import tr.edu.gsu.mataws.preprocessing.strategyGroups.PreprocessingSet;
import tr.edu.gsu.mataws.preprocessing.strategyGroups.impl.DefaultPreprocessing;
import tr.edu.gsu.mataws.toolbox.SigmaUtil;

import com.articulate.sigma.WordNet;

/**
 * This class is an implementation of Core in which 
 * necessary preprocessing operations are applied
 * to the given parameter.
 * 
 * @author Koray Mancuhan & Cihan Aksoy
 *
 */
public class CoreImpl implements Core{

	private static CoreImpl INSTANCE = null;
	
	private PreprocessingSet preprocessingSet;
	private PreprocessingStrategy preprocessingStrategy;
	
	private CoreImpl(){
		try {
			WordNet.initOnce();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static CoreImpl getInstance(){
		if(INSTANCE == null)
			INSTANCE = new CoreImpl();
		return INSTANCE;
	}
	
	public List<String> process(Queue<Node> queue){
		List<String> preprocessingResult = new ArrayList<String>();
		boolean condition = true;
		while (condition) {
			Node aNode=queue.poll();
			TraceableParameter tparameter = aNode.getTraceableParameter();
			int level=aNode.getLevel();
			//String aParamName = tparameter.getName();
			preprocessingResult = this.processName(tparameter, tparameter.getParameter().getName());
			if (!isAnnotable(preprocessingResult)) {
				String typeName = tparameter.getParameter().getTypeName();
				if (!typeName.equals("")) {
					preprocessingResult = this.processName(tparameter, tparameter.getParameter().getTypeName());
					if (!isAnnotable(preprocessingResult)) {
						List<Parameter> subParamList = tparameter.getSubParameters();
						if (subParamList != null) {
							level++;
							for (int i = 0; i < subParamList.size(); i++) {
								TraceableParameter tempTP = new TraceableParameter(subParamList.get(i));
								Node newNode=new Node(tempTP, level);
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
		return preprocessingResult;
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
	 * Service method verifying if preprocessing result 
	 * may be annotated.
	 * 
	 * @param annotationResult
	 * 			list holding the result of annotation 
	 * @return
	 * 			true if the parameter is annotated
	 */
	public boolean isAnnotable(List<String> preprocessingResult) {
		boolean result = true;
		int noMatchCount = 0;
		String concept = null;
		
		for(String string: preprocessingResult){
			concept = SigmaUtil.findConcept(string);
			if(concept.equals("NoMatch"))
				noMatchCount++;
		}
		
		if (noMatchCount == preprocessingResult.size()) {
			result = false;
		}
		return result;
	}
	
	/**
	 * This method realizes the core operation of the program. It
	 * applies the default preprocessing operations set and also
	 * the purification and splitting operations to the given parameter name.
	 * 
	 * @param TraceableParameter tp
	 * @return list of processed words
	 */
	public List<String> processName(TraceableParameter tp, String toProcess){
		List<String> result = new ArrayList<String>();
		
		//default preprocessing is applied
		preprocessingSet = new DefaultPreprocessing();
		result = preprocessingSet.processName(tp, toProcess);

		//purifying operation is applied if necessary
		List<String> nonAnnotableWords = new ArrayList<String>();
		for (String string : result) {
			if(SigmaUtil.findConcept(string).equals("NoMatch")){
				nonAnnotableWords.add(string);
			}
		}
		for (String string : nonAnnotableWords) {
			result.remove(string);
		}
		List<String> purifiedWords = new ArrayList<String>();
		if(nonAnnotableWords.size()>0){
			preprocessingStrategy = new JawsPurificationImpl();
			purifiedWords = preprocessingStrategy.execute(nonAnnotableWords);
		}
		List<String> nonAnnotableWords2 = new ArrayList<String>();
		for (String string : purifiedWords) {
			if(!SigmaUtil.findConcept(string).equals("NoMatch"))
				result.add(string);
			else{
				//result.add(nonAnnotableWords.get(purifiedWords.indexOf(string)));
				nonAnnotableWords2.add(string);
			}
		}
		//splitting operation is applied if necessary
		List<String> splittedWords = new ArrayList<String>();
		if(nonAnnotableWords2.size()>0){
			preprocessingStrategy = new WithoutSpecialCharDecomposition();
			splittedWords = preprocessingStrategy.execute(nonAnnotableWords2);
		}
		for (String string : splittedWords) {
			result.add(string);
		}
		return result;
	}
}
