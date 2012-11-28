package tr.edu.gsu.mataws.component.contraster.breaker;

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

import tr.edu.gsu.mataws.data.MatawsParameter;
import tr.edu.gsu.sine.col.Operation;

/**
 * Interface for classes in charge of breaking 
 * down operation names and using this result
 * to improve the annotation of their parameters.
 * 
 * @author Vincent Labatut
 */
public interface BreakerInterface
{
	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	/**
	 * Takes an operation and analyses its name, then takes
	 * advantage of this to annotate its parameters. It returns
	 * {@code true} if it could annotate at least one parameter.
	 * <br/>
	 * Depending on how the annotation process went, parameters
	 * in the received list are updated.
	 * 
	 * @param operation
	 * 		The operation to process. 
	 * @param parameters
	 * 		The parameters to annotate. 
	 * @return
	 * 		{@code true} iff the method could annotate at least one parameter.
	 */
	public boolean breakk(Operation operation, List<MatawsParameter> parameters);
}

/*

+ comparaison de paramètres:
	- exemples :
		- username, userage et userid >> user n'est pas pertinent. les concepts seraient name, age, et id
		- username, adminname et clientname >> alors ça le devient. les concepts seraient user, admin et client.
	- contre-exemple :
		- userTel, adminTel >> on voudrait plutot savoir qu'il s'agit de téléphones plutot que d'user et admin (?)
		  ceci devrait être considéré lors de l'évaluation manuelle (nature des autres paramètres) >> mais ça implique que chaque instance de paramètre doive être évaluée séparément, puisque le contexte (potentiellement différent) serait pris en compte.

+ nom de l'opération
	- patterns courants :
		- getXxxxxbyYyyyyReturn >>> Xxxxx
		- getXxxxxbyYyyyyParameter >>> Yyyyy
		- "get" est optionel
		- "by" peut etre remplacé par from, for...
		- variante : userForId >>> user
	- pattern pas forcément caractérisé par des alternances de min/majuscules
	  parfois il s'agit uniquement d'unités
	  ex: PoundsPerSqrInch
	- autre pattern : getXxxxReturn >> Xxxxxx	

*/
