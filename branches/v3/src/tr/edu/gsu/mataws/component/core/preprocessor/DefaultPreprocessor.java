package tr.edu.gsu.mataws.component.core.preprocessor;

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

import tr.edu.gsu.mataws.component.core.preprocessor.filters.FilterInterface;
import tr.edu.gsu.mataws.component.core.preprocessor.filters.StopWordFilter;
import tr.edu.gsu.mataws.component.core.preprocessor.normalizers.AbbreviationNormalizer;
import tr.edu.gsu.mataws.component.core.preprocessor.normalizers.CaseNormalizer;
import tr.edu.gsu.mataws.component.core.preprocessor.normalizers.NormalizerInterface;
import tr.edu.gsu.mataws.component.core.preprocessor.normalizers.StemNormalizer;
import tr.edu.gsu.mataws.component.core.preprocessor.splitters.LettercaseBasedSplitter;
import tr.edu.gsu.mataws.component.core.preprocessor.splitters.LexiconBasedSplitter;
import tr.edu.gsu.mataws.component.core.preprocessor.splitters.NumberBasedSplitter;
import tr.edu.gsu.mataws.component.core.preprocessor.splitters.SeparatorBasedSplitter;
import tr.edu.gsu.mataws.component.core.preprocessor.splitters.SplitterInterface;
import tr.edu.gsu.mataws.component.core.preprocessor.splitters.LexiconBasedSplitter.Mode;

/**
 * Series of processings corresponding to the
 * default Preprocessor.
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
	
		splitter = new SeparatorBasedSplitter("_");
		splitters.add(splitter);
		splitter = new SeparatorBasedSplitter("-");
		splitters.add(splitter);
		splitter = new SeparatorBasedSplitter(" ");
		splitters.add(splitter);
		
		splitter = new NumberBasedSplitter();
		splitters.add(splitter);
	
		splitter = new LettercaseBasedSplitter();
		splitters.add(splitter);
		
		splitter = new LexiconBasedSplitter(Mode.JWORDSPLITTER);
//		splitter = new LexiconBasedDivision(Mode.WORDSPLIT);	//TODO to be tested
		splitters.add(splitter);
	}

	///////////////////////////////////////////////////////////
	//	NORMALIZATION						///////////////////
	///////////////////////////////////////////////////////////
	@Override
	protected void initNormalizers()
	{	NormalizerInterface normalizer;
	
		normalizer = new CaseNormalizer();
		normalizers.add(normalizer);
		
		normalizer = new AbbreviationNormalizer();
		normalizers.add(normalizer);
		
		normalizer = new StemNormalizer();	// TODO seems a better idea not to do that here, because it causes information loss
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
	}
}
