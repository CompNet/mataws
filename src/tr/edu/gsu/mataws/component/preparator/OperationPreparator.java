package tr.edu.gsu.mataws.component.preparator;

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
import tr.edu.gsu.mataws.component.preparator.filter.AbstractFilter;
import tr.edu.gsu.mataws.component.preparator.filter.LengthFilter;
import tr.edu.gsu.mataws.component.preparator.filter.RedundancyFilter;
import tr.edu.gsu.mataws.component.preparator.identifier.AbstractIdentifier;
import tr.edu.gsu.mataws.component.preparator.identifier.JawsIdentifier;
import tr.edu.gsu.mataws.component.preparator.normalizer.AbbreviationNormalizer;
import tr.edu.gsu.mataws.component.preparator.normalizer.CaseNormalizer;
import tr.edu.gsu.mataws.component.preparator.normalizer.DiacriticsNormalizer;
import tr.edu.gsu.mataws.component.preparator.normalizer.AbstractNormalizer;
import tr.edu.gsu.mataws.component.preparator.normalizer.StemNormalizer;
import tr.edu.gsu.mataws.component.preparator.splitter.LetterCaseSplitter;
import tr.edu.gsu.mataws.component.preparator.splitter.LexiconSplitter;
import tr.edu.gsu.mataws.component.preparator.splitter.NumberSplitter;
import tr.edu.gsu.mataws.component.preparator.splitter.SeparatorSplitter;
import tr.edu.gsu.mataws.component.preparator.splitter.AbstractSplitter;
import tr.edu.gsu.mataws.component.preparator.splitter.LexiconSplitter.Mode;

/**
 * Series of processings corresponding to the
 * preparation of an operation name.
 *   
 * @author Vincent Labatut
 */
public class OperationPreparator extends AbstractPreparator<Synset>
{	
	///////////////////////////////////////////////////////////
	//	SPLIT								///////////////////
	///////////////////////////////////////////////////////////
	@Override
	protected void initSplitters()
	{	AbstractSplitter splitter;
	
		splitter = new SeparatorSplitter("_");
		splitters.add(splitter);
		splitter = new SeparatorSplitter("-");
		splitters.add(splitter);
		splitter = new SeparatorSplitter(" ");
		splitters.add(splitter);
		
		splitter = new NumberSplitter();
		splitters.add(splitter);
	
		splitter = new LetterCaseSplitter();
		splitters.add(splitter);
		
		splitter = new LexiconSplitter(Mode.JWORDSPLITTER);
//		splitter = new LexiconBasedDivision(Mode.WORDSPLIT);	//TODO to be tested
		splitters.add(splitter);
	}

	///////////////////////////////////////////////////////////
	//	NORMALIZATION						///////////////////
	///////////////////////////////////////////////////////////
	@Override
	protected void initNormalizers()
	{	AbstractNormalizer normalizer;
	
		// TODO not sure this should be done here, because Sigma actually seems to be able to take advantage of that.
		normalizer = new CaseNormalizer();
		normalizers.add(normalizer);
		
		normalizer = new AbbreviationNormalizer();
		normalizers.add(normalizer);

		// TODO is this one really necessary?
		normalizer = new DiacriticsNormalizer();
		normalizers.add(normalizer);
		
		// TODO seems a better idea not to do that here, because it causes information loss
		normalizer = new StemNormalizer(StemNormalizer.Mode.JWAS);
//		normalizer = new StemNormalizer(StemNormalizer.Mode.JWI);
		normalizers.add(normalizer);
	}
	
	///////////////////////////////////////////////////////////
	//	FILTERING							///////////////////
	///////////////////////////////////////////////////////////
	@Override
	protected void initFilters()
	{	AbstractFilter filter;
	
//		filter = new StopWordFilter();
//		filters.add(filter);
		
		filter = new RedundancyFilter();
		filters.add(filter);
		
		filter = new LengthFilter();
		filters.add(filter);
	}

	///////////////////////////////////////////////////////////
	//	IDENTIFICATION						///////////////////
	///////////////////////////////////////////////////////////
	@Override
	protected void initIdentifiers()
	{	AbstractIdentifier<Synset> identifier;
	
		identifier = new JawsIdentifier();
		identifiers.add(identifier);
	}
}
