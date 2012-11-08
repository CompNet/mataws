package tr.edu.gsu.mataws.preprocessing.decomposition.impl;

/*
 * Mataws - Multimodal Automatic Tool for the Annotation of Web Services
 * Copyright 2011-12 Cihan Aksoy & Koray Mançuhan
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

import tr.edu.gsu.mataws.preprocessing.decomposition.DecompositionStrgy;

/**
 * Decomposition Strategy which divides each little word 
 * of a parameter in smaller little words by an upper case letter
 * following lower case letters.
 *   
 * @author Koray Mancuhan & Cihan Aksoy
 *
 */
public class MajusculeAfterMinusculeDecomposition implements DecompositionStrgy {

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
	 * Divides a word into smaller words by determining upper case letters
	 * following lower case letters.
	 * 
	 * @param word
	 * 			a word
	 * @return
	 * 			an array of little words
	 */
	public String[] divide(String word){
		String[] resultArray=new String[1], arrayLeft, arrayRight;
		boolean check=false;
		for(int i=1; i<word.length(); i++){
			char a=word.charAt(i-1);
			char b=word.charAt(i);
			if(Character.isLowerCase(a) && Character.isUpperCase(b)){
				String leftString =word.substring(0, i);
				String rightString =word.substring(i, word.length());
				arrayLeft=divide(leftString);
				arrayRight=divide(rightString);
				resultArray=new String[arrayLeft.length+arrayRight.length];
				for(int j=0; j<arrayLeft.length; j++)
					resultArray[j]=arrayLeft[j];
				for(int j=0; j<arrayRight.length; j++)
					resultArray[arrayLeft.length+j]=arrayRight[j];
				check=true;
				break;
			}
		}
		
		if(!check)
			resultArray[0]=word;
		return resultArray;		
	}
}
