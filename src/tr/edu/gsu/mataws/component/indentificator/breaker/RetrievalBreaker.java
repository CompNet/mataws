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
import tr.edu.gsu.mataws.tools.misc.MatawsWay;

/**
 * Anlyzes the name of a retrieval method, using some predefined
 * patterns to identify parameter meanings.
 * <br/>
 * For example, the {@code "getStudentForId"} method should have
 * an output parameter corresponding to a student and an input
 * one representing an identifier.
 *   
 * @author Vincent Labatut
 */
public class RetrievalBreaker implements BreakerInterface<Synset>
{
	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	/** List of action names likely to appear in a retrieval method */
	private final static List<String> ACTIONS = Arrays.asList("get","find","retrieve","search"); 
	/** Words likely to separate parameters */
	private final static List<String> CONNECTORS = Arrays.asList("by","for","from","to","with"); 
	
	@Override
	public Map<MatawsWay, List<IdentifiedWord<Synset>>> breakk(List<IdentifiedWord<Synset>> operationList)
	{	Map<MatawsWay, List<IdentifiedWord<Synset>>> result = null;
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
				List<IdentifiedWord<Synset>> outParam = new ArrayList<IdentifiedWord<Synset>>(opnameCopy.subList(0, indexCnct));
				List<IdentifiedWord<Synset>> inParam = new ArrayList<IdentifiedWord<Synset>>(opnameCopy.subList(indexCnct+1,opnameCopy.size()));
				
				// set the result map
				result = new HashMap<MatawsWay, List<IdentifiedWord<Synset>>>();
				result.put(MatawsWay.IN_1, inParam);
				result.put(MatawsWay.OUT, outParam);
				
				
				// si deux parametres : facile, on fait en fonction de in/out
				// si quatre paramètres (doublons in/out) : faut déterminer lequel est lequel
				//		1) s'il manque le concept pr les deux >> on ne peut rien faire
				//		2) s'il y a un concept pr l'un des deux >> distance avec les deux concepts trouvés ici ?
				//			(>> besoin de n'avoir qu'un seul mot rep dès le breaker, et non pas en remontant au contraster)
				
				
				// pb : comment faire remonter ça ?
				// >> faire remonter une map, et mettre à jour les params dans le contraster
				// >> ça empêche de faire de l'identification si le nombre de paramètres ne colle pas
				// >> suffit de faire remonter les idWords annotés puis de faire le matching avec les params séparément
				//    après tout c'est une tâche générique. on doit définir un nouveau type de composant pour ça.
				
			}
		}
		
		/* TODO TODO
		 * 
		 *  - dans le nom de la méthode, on distingue les params d'entrée/sortie
		 *    - la forme le permet-elle ? si non >> échec
		 *    - si oui, on identifie le mot correspondant
		 *  - qui en entrée, qui en sortie ? >> soit les mêmes, soit différents
		 *  	- les mêmes >> on continue normalement
		 *  	- différents >> on les traite séparément
		 *  	- un peu des deux >> ??
		 *  
		 */
		
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
