package tr.edu.gsu.mataws.component.indentificator.breaker;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.smu.tspell.wordnet.Synset;

import tr.edu.gsu.mataws.data.IdentifiedWord;
import tr.edu.gsu.sine.col.Way;

/**
 * Anlyzes the name of a modification method, using some predefined
 * patterns to identify parameter meanings.
 * <br/>
 * For example, the {@code "addStudentToDepartment"} method should have
 * two input parameters representing a student and a department.
 *   
 * @author Vincent Labatut
 */
public class ModificationBreaker implements BreakerInterface<Synset>
{
	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	/** List of action names likely to appear in a modification method */
	private final static List<String> ACTIONS = Arrays.asList("add","change","delete","edit","create","remove","set"); 
	/** Words likely to separate parameters */
	private final static List<String> CONNECTORS = Arrays.asList("by","for","from","to","with"); 

	@Override
	public Map<Way,List<List<IdentifiedWord<Synset>>>> breakk(List<IdentifiedWord<Synset>> operationList)
	{	Map<Way,List<List<IdentifiedWord<Synset>>>> result = null;
		List<IdentifiedWord<Synset>> opnameCopy = new ArrayList<IdentifiedWord<Synset>>(operationList);
		
		// check if the operation name contains a relevant action
		int indexAct = indexOfIn(opnameCopy, ACTIONS);
		if(indexAct==0 || indexAct == opnameCopy.size()-1)
		{	// remove it, because we don't need it anymore
			opnameCopy.remove(indexAct);
			// look for a connector
			int indexCnct = indexOfIn(opnameCopy,CONNECTORS);
			if(indexCnct>0 && indexCnct<opnameCopy.size()-1)
			{	// get the input and output parameters in the name list
				List<IdentifiedWord<Synset>> inParam1 = new ArrayList<IdentifiedWord<Synset>>(opnameCopy.subList(0, indexCnct));
				List<IdentifiedWord<Synset>> inParam2 = new ArrayList<IdentifiedWord<Synset>>(opnameCopy.subList(indexCnct+1,opnameCopy.size()));
				
				// set the result map
				List<List<IdentifiedWord<Synset>>> inList = new ArrayList<List<IdentifiedWord<Synset>>>();
				inList.add(inParam1);
				inList.add(inParam2);
				result = new HashMap<Way,List<List<IdentifiedWord<Synset>>>>();
				result.put(Way.IN,inList);
			}
		}
		
		return result;
	}

	/**
	 * Checks if one of the words in {@code strings} is contained
	 * in the list of identified words. If it is the case,
	 * it returns the position of the concerned word in
	 * the list of identified words; otherwise it 
	 * returns -1. 
	 * 
	 * 
	 * @param operationName
	 * 		List of identified words.
	 * @param strings
	 * 		List of reference strings.
	 * @return
	 * 		The position of the matching word in the first list.
	 */
	private int indexOfIn(List<IdentifiedWord<Synset>> operationName, List<String> strings)
	{	int result = -1;
		int i = 0;
		while(i<operationName.size() && result==-1)
		{	IdentifiedWord<Synset> word = operationName.get(i);
			String orig = word.getOriginal();
			if(strings.contains(orig))
				result = i;
			else
				i++;
		}
		return result;
	}
}
