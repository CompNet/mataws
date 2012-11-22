package tr.edu.gsu.mataws.component.core.selector;

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
import tr.edu.gsu.mataws.component.core.selector.identifier.IdentifierInterface;
import tr.edu.gsu.mataws.component.core.selector.identifier.JawsIdentifier;
import tr.edu.gsu.mataws.component.core.selector.simplifier.CommonHolonymSimplifier;
import tr.edu.gsu.mataws.component.core.selector.simplifier.CommonHypernymSimplifier;
import tr.edu.gsu.mataws.component.core.selector.simplifier.DirectHolonymSimplifier;
import tr.edu.gsu.mataws.component.core.selector.simplifier.DirectHypernymSimplifier;
import tr.edu.gsu.mataws.component.core.selector.simplifier.FrequentVerbSimplifier;
import tr.edu.gsu.mataws.component.core.selector.simplifier.FrequentWordSimplifier;
import tr.edu.gsu.mataws.component.core.selector.simplifier.FusionSimplifier;
import tr.edu.gsu.mataws.component.core.selector.simplifier.LastNounSimplifier;
import tr.edu.gsu.mataws.component.core.selector.simplifier.SimplifierInterface;

/**
 * Series of processings corresponding to the
 * default Selector component.
 *   
 * @author Cihan Aksoy
 * @author Vincent Labatut
 */
public class DefaultSelector extends AbstractSelector<Synset>
{	
	///////////////////////////////////////////////////////////
	//	IDENTIFICATION						///////////////////
	///////////////////////////////////////////////////////////
	@Override
	protected void initIdentifiers()
	{	IdentifierInterface<Synset> identifier;
	
		identifier = new JawsIdentifier();
		identifiers.add(identifier);
	}

	///////////////////////////////////////////////////////////
	//	SIMPLIFICATION						///////////////////
	///////////////////////////////////////////////////////////
	@Override
	protected void initSimplifiers()
	{	SimplifierInterface<Synset> simplifier;
	
		simplifier = new FusionSimplifier();
		simplifiers.add(simplifier);
		
		simplifier = new DirectHypernymSimplifier();
		simplifiers.add(simplifier);
		
		simplifier = new CommonHypernymSimplifier();
		simplifiers.add(simplifier);
		
		simplifier = new DirectHolonymSimplifier();
		simplifiers.add(simplifier);
		
		simplifier = new CommonHolonymSimplifier();
		simplifiers.add(simplifier);
		
		simplifier = new LastNounSimplifier();
		simplifiers.add(simplifier);
		
		simplifier = new FrequentVerbSimplifier();
		simplifiers.add(simplifier);
		
		simplifier = new FrequentWordSimplifier();
		simplifiers.add(simplifier);
	}
}
