package tr.edu.gsu.mataws.component.contraster;

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

import edu.smu.tspell.wordnet.Synset;

/**
 * Series of processings corresponding to the
 * default Contraster component.
 * <br/>
 * Other contrasters can be designed by using
 * different combinations of breakers.
 *   
 * @author Vincent Labatut
 */
public class DefaultContraster extends AbstractContraster<Synset>
{	
	///////////////////////////////////////////////////////////
	//	BREAK								///////////////////
	///////////////////////////////////////////////////////////
	@Override
	protected void initBreakers()
	{	
//		BreakerInterface<Synset> breaker;
	
//		breaker = new SigmaMapper();
//		breakers.add(breaker);
	}
}
