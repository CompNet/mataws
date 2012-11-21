package tr.edu.gsu.mataws.component.core.selector.simplifier;

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

import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.SynsetType;

import tr.edu.gsu.mataws.component.core.selector.IdentifiedWord;

/**
 * Keeps the word corresponding to the last noun.
 * The assumption here is we have a nominal group,
 * with one noun complementing another one, like in
 * "user's email" for instance. With this simplifier,
 * the alst noun is considered as the most relevant.
 * The other parameter might of the form "user's name",
 * "user's age", so the word "user" is not considered
 * as informative.
 * <br>
 * Example: <b>{@code "Popular"}</b>, <b>{@code "User"}</b>, {@code "Address"} -> {@code "Address"}
 *  
 * @author Vincent Labatut
 */
public class LastNounSimplifier implements SimplifierInterface<Synset>
{
	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	@Override
	public boolean simplify(List<IdentifiedWord<Synset>> words)
	{	boolean result = false;
		
		// look for the last noun
		IdentifiedWord<Synset> lastNoun = null;
		int i = words.size() - 1;
		while(i>=0 && lastNoun==null)
		{	IdentifiedWord<Synset> word = words.get(i);
			Synset synset = word.getSynset();
			SynsetType type = synset.getType();
			if(type==SynsetType.NOUN)
				lastNoun = word;
			i--;
		}
		
		// change the word list accordingly (i.e. no change if there's no noun)
		if(lastNoun!=null)
		{	words.clear();
			words.add(lastNoun);
			result = true;
		}
		
		return result;
	}
}
