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
package tr.edu.gsu.mataws.components;

import sine.col.Parameter;
import java.util.*;

/**
 * Represents an annotated parameter.
 * 
 * @author Koray Mancuhan & Cihan Aksoy
 *
 */
public class AnnotatedParameter extends Parameter {
	//list of concepts found by Sigma
	private List<String> conceptList;
	
	/**
	 * Constructs an object representing an annotated parameter.
	 * 
	 * @param name
	 * 			the name of the annotated parameter
	 */
	public AnnotatedParameter(String name) {
		super(name);
		this.conceptList=new ArrayList<String>();		
	}
	
	/**
	 * Adds a new concept name found by Sigma to list of concepts.
	 * 
	 * @param conceptName
	 * 				a concept name found by Sigma.
	 */
	public void addConcept(String conceptName){
		this.conceptList.add(conceptName);
	}

	/**
	 * Returns the list of concepts associated to parameter
	 * 
	 * @return
	 * 		List of concepts associated to parameter
	 */
	public List<String> getConcepts(){
		return(this.conceptList);
	}
	
	/**
	 * Erases the list of concepts
	 * 
	 */
	public void clearConcepts(){
		this.conceptList.clear();
	}
	
	/**
	 * Erases the specific concept indicated with parameter 
	 * 
	 * @param conceptName
	 * 			concept to erase
	 */
	public void removeConcept(String conceptName){
		for(int i=0; i<this.conceptList.size(); i++){
			if(conceptName.equals(conceptList.get(i))){
				this.conceptList.remove(i);
				break;
			}
		}
	}
}
