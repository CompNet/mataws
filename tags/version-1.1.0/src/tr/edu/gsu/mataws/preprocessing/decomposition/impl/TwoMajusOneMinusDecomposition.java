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
package tr.edu.gsu.mataws.preprocessing.decomposition.impl;

import java.util.*;

import tr.edu.gsu.mataws.preprocessing.decomposition.DecompositionStrgy;

/**
 * Decomposition Strategy which divides each little word 
 * of a parameter in smaller little words by a lower case letter
 * following two upper case letters.
 *   
 * @author Koray Mancuhan & Cihan Aksoy
 *
 */
public class TwoMajusOneMinusDecomposition implements DecompositionStrgy {

	@Override
	public List<String> execute(List<String> paramNames) {
		// TODO Auto-generated method stub
		List<String> result=new ArrayList<String>();
		for(int i=0; i<paramNames.size(); i++){
			String name=paramNames.get(i);
			String[] dividedName=this.divide(name);
			for(int j=0; j<dividedName.length; j++){
				result.add(dividedName[j]);
			}
		}
		return result;

	}

	/**
	 * Divides a word into smaller words by determining a lower case letter
	 * following two upper case letters.
	 * 
	 * @param word
	 * 			a word
	 * @return
	 * 			an array of little words
	 */
	private String[] divide(String word){
		String[] result=new String[1];
		result[0]=word;
		for(int i=2; i<word.length(); i++){
			char a=word.charAt(i-2);
			char b=word.charAt(i-1);
			char c=word.charAt(i);
			if(Character.isUpperCase(a)&&Character.isUpperCase(b)&&Character.isLowerCase(c)){
				String leftString=word.substring(0,i-1);
				String rightString=word.substring(i-1, word.length());
				result=new String[2];
				result[0]=leftString;
				result[1]=rightString;
				break;
			}
		}
		return result;
	}
}
