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

import java.util.Arrays;
import java.util.List;

import edu.smu.tspell.wordnet.Synset;

import tr.edu.gsu.mataws.data.IdentifiedWord;
import tr.edu.gsu.mataws.data.MatawsParameter;

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
	public boolean breakk(List<IdentifiedWord<Synset>> operationList, List<MatawsParameter> parameters)
	{	boolean result = false;
		
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
}
