package tr.edu.gsu.mataws.component.cleaner;

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

import tr.edu.gsu.mataws.component.cleaner.filter.FilterInterface;
import tr.edu.gsu.mataws.component.cleaner.filter.LengthFilter;
import tr.edu.gsu.mataws.component.cleaner.filter.RedundancyFilter;
import tr.edu.gsu.mataws.component.cleaner.filter.StopWordFilter;
import tr.edu.gsu.mataws.component.cleaner.normalizer.AbbreviationNormalizer;
import tr.edu.gsu.mataws.component.cleaner.normalizer.CaseNormalizer;
import tr.edu.gsu.mataws.component.cleaner.normalizer.DiacriticsNormalizer;
import tr.edu.gsu.mataws.component.cleaner.normalizer.NormalizerInterface;
import tr.edu.gsu.mataws.component.cleaner.normalizer.StemNormalizer;
import tr.edu.gsu.mataws.component.cleaner.splitter.LetterCaseSplitter;
import tr.edu.gsu.mataws.component.cleaner.splitter.LexiconSplitter;
import tr.edu.gsu.mataws.component.cleaner.splitter.NumberSplitter;
import tr.edu.gsu.mataws.component.cleaner.splitter.SeparatorSplitter;
import tr.edu.gsu.mataws.component.cleaner.splitter.SplitterInterface;
import tr.edu.gsu.mataws.component.cleaner.splitter.LexiconSplitter.Mode;

/**
 * Series of processings corresponding to the
 * default Preprocessor component.
 *   
 * @author Koray Mancuhan
 * @author Cihan Aksoy
 * @author Vincent Labatut
 */
public class DefaultPreprocessor extends AbstractPreprocessor
{	
	///////////////////////////////////////////////////////////
	//	SPLIT								///////////////////
	///////////////////////////////////////////////////////////
	@Override
	protected void initSplitters()
	{	SplitterInterface splitter;
	
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
	{	NormalizerInterface normalizer;
	
		// TODO not sure this should be done here, because Sigma actually seems to be able to take advantage of that.
		normalizer = new CaseNormalizer();
		normalizers.add(normalizer);
		
		normalizer = new AbbreviationNormalizer();
		normalizers.add(normalizer);

		// TODO is this one really necessary?
		normalizer = new DiacriticsNormalizer();
		normalizers.add(normalizer);
		
		// TODO seems a better idea not to do that here, because it causes information loss
		normalizer = new StemNormalizer(tr.edu.gsu.mataws.component.cleaner.normalizer.StemNormalizer.Mode.JWAS);
//		normalizer = new StemNormalizer(tr.edu.gsu.mataws.component.core.preprocessor.normalizer.StemNormalizer.Mode.JWI);
		normalizers.add(normalizer);
	}
	
	///////////////////////////////////////////////////////////
	//	FILTERING							///////////////////
	///////////////////////////////////////////////////////////
	@Override
	protected void initFilters()
	{	FilterInterface filter;
	
		filter = new StopWordFilter();
		filters.add(filter);
		
		filter = new RedundancyFilter();
		filters.add(filter);
		
		filter = new LengthFilter();
		filters.add(filter);
	}
}
