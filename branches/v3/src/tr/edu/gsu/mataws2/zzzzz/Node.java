package tr.edu.gsu.mataws2.zzzzz;

import tr.edu.gsu.mataws2.trace.TraceableParameter;

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

/**
 * Class representing a parameter and its level for breadth first algorithm.
 * 
 * @author Koray Mancuhan & Cihan Aksoy
 *
 */
public class Node {
	
	private TraceableParameter traceableParameter;
	private int level;
	
	/**
	 * Constructs a node for breadth first algorithm.
	 * 
	 * @param parameter
	 * 			the parameter
	 * @param level
	 * 			the parameter's level
	 */
	public Node(TraceableParameter tparameter, int level){
		this.traceableParameter=tparameter;
		this.level=level;
	}
	
	/**
	 * Returns the parameter object of node.
	 * 
	 * @return
	 * 		the parameter object of node.
	 */
	public TraceableParameter getTraceableParameter(){
		return (this.traceableParameter);
	}
	
	/**
	 * Returns level of the parameter which is represented by node. 
	 * 
	 * @return
	 * 		the level of a parameter which is represented by node.
	 */
	public int getLevel(){
		return (this.level);
	}
}
