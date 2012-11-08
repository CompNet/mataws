package tr.edu.gsu.mataws2.components.preprocessor.decomposition;

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

import java.util.*;


/**
 * Decomposition Strategy which divides each little word 
 * of a parameter in smaller little words by a number.
 *   
 * @author Koray Mancuhan & Cihan Aksoy
 *
 */
public class NumberDecomposition implements DecompositionStrategy {

	@Override
	public List<String> execute(List<String> paramNames) {
		// TODO Auto-generated method stub
		List<String> result=new ArrayList<String>();
		for(int i=0; i<paramNames.size(); i++){
			String name=paramNames.get(i);
			String[] dividedName=name.split("[0-9]");
			for(int j=0; j<dividedName.length; j++){
				result.add(dividedName[j]);
			}
		}
		return result;
	}
	
}
