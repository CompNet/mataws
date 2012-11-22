package tr.edu.gsu.mataws.component.core.associator;

/*
 * Mataws - Multimodal Automatic Tool for the Annotation of Web Services
 * Copyright 2010 Cihan Aksoy and Koray Man�uhan
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
import tr.edu.gsu.mataws.component.core.associator.mapper.MapperInterface;
import tr.edu.gsu.mataws.component.core.associator.mapper.SigmaMapper;

/**
 * Series of processings corresponding to the
 * default Associator component.
 * <br/>
 * Other associators can be designed by using
 * different combinations of mappers, and/or
 * different mappers.
 *   
 * @author Vincent Labatut
 */
public class DefaultAssociator extends AbstractAssociator<Synset>
{	
	///////////////////////////////////////////////////////////
	//	MAPPING								///////////////////
	///////////////////////////////////////////////////////////
	@Override
	protected void initMappers()
	{	MapperInterface<Synset> mapper;
	
		mapper = new SigmaMapper();
		mappers.add(mapper);
	}
}
