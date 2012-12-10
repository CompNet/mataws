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

import java.util.List;
import java.util.Map;

import tr.edu.gsu.mataws.data.IdentifiedWord;
import tr.edu.gsu.mataws.tools.misc.MatawsWay;

/**
 * Interface for classes in charge of identifying  
 * parts of operation names which can be used to
 * describe their parameters, eg. {@code getIdForName}.
 * 
 * @param <T> 
 *		Class used to represent a WordNet synset.
 * 
 * @author Vincent Labatut
 */
public interface BreakerInterface<T>
{
	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	/**
	 * Takes an operation and analyses its name, in order to
	 * extract information regarding its parameters.
	 * 
	 * @param operationList
	 * 		The list of words composing the operation name. 
	 * @return
	 * 		A map of the identified parts, or null if none could be identified.
	 */
	public Map<MatawsWay,List<IdentifiedWord<T>>> breakk(List<IdentifiedWord<T>> operationList);
}

/* TODO TODO

+ comparaison de paramètres:
	- exemples :
		- username, userage et userid >> user n'est pas pertinent. les concepts seraient name, age, et id
		- username, adminname et clientname >> alors ça le devient. les concepts seraient user, admin et client.
	- contre-exemple :
		- userTel, adminTel >> on voudrait plutot savoir qu'il s'agit de téléphones plutot que d'user et admin (?)
		  ceci devrait être considéré lors de l'évaluation manuelle (nature des autres paramètres) >> mais ça implique que chaque instance de paramètre doive être évaluée séparément, puisque le contexte (potentiellement différent) serait pris en compte.

+ nom de l'opération
	- patterns courants :
		- getXxxxxByYyyyyReturn >>> Xxxxx
		- getXxxxxByYyyyyParameter >>> Yyyyy
		- "get" est optionel
		- "by" peut etre remplacé par from, for...
		- variante : userForId >>> user
	- pattern pas forcément caractérisé par des alternances de min/majuscules
	  parfois il s'agit uniquement d'unités
	  ex: PoundsPerSqrInch
	- autre pattern : getXxxxReturn >> Xxxxxx	

*/
