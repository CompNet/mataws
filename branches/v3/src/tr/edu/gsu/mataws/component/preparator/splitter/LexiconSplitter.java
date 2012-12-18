package tr.edu.gsu.mataws.component.preparator.splitter;

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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import tr.edu.gsu.mataws.tools.misc.FileTools;
import tr.edu.gsu.mataws.tools.strings.WordSplitTools;

import com.whitemagicsoftware.wordsplit.TextSegmenter;

import de.abelssoft.wordtools.jwordsplitter.impl.EnglishWordSplitter;

/**
 * Splitting Strategy which separates contiguous words
 * into only one word by using JWordSplitter.
 *   
 * @author Cihan Aksoy
 * @author Vincent Labatut
 *
 * TODO needs a lexicon with word frequencies, such as wordnet, or:
 * http://www.kilgarriff.co.uk/bnc-readme.html
 * 
 * TODO question: are the libs case-sensitive?
 * if yes, then the strings can be lowered here (temporary)
 */
public class LexiconSplitter extends AbstractSplitter
{	
	/**
	 * Buils a lexicon-based splitter using the
	 * specified library: JWORDSPLITTER or WORDSPLIT.
	 * 
	 * @param mode
	 * 		Represents the split library.
	 */
	public LexiconSplitter(Mode mode)
	{	super();
	
		this.mode = mode;
		if(mode==Mode.JWORDSPLITTER)
			initJWordSplitter();
	}
	
	///////////////////////////////////////////////////////////
	//	MODE								///////////////////
	///////////////////////////////////////////////////////////
	/**
	 * Represents the functioning mode
	 * of this processing. The focus is either
	 * on the parameter name, or data type name.
	 * 
	 * @author Vincent Labatut
	 */
	public enum Mode
	{	/** Use the JWordSplitter lib to split the name */
		JWORDSPLITTER,
		/** Use the WordSplit lib to split the name */
		WORDSPLIT;
	}
	
	/** Represents the library used to perform the split */
	private Mode mode;
	
	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	@Override
	public List<String> split(List<String> strings)
	{	String msg = "Spitting using case, for the strings: ";
		for(String string: strings)
			msg = msg + " '" + string + "'";
		logger.log(msg);
		logger.increaseOffset();
		List<String> result = null;
		
		if(mode==Mode.JWORDSPLITTER)
			result = applyJWordSplitter(strings);
		else if(mode==Mode.WORDSPLIT)
			result = applyWordSplit(strings);
		
		logger.decreaseOffset();
		return result;
	}
	
	///////////////////////////////////////////////////////////
	//	JWORDSPLITTER						///////////////////
	///////////////////////////////////////////////////////////
	/** JWordSplitter object */
	private static EnglishWordSplitter jWordSplitter = null;
	
	/**
	 * Initializes the JWordSplitter library.
	 */
	private void initJWordSplitter()
	{	String path = FileTools.SPLITTER_FOLDER + File.separator + "wordsEnglish.ser";
		logger.log("Initializing JWordSplitter with file "+path);
		logger.increaseOffset();
	
		try
		{	EnglishWordSplitter.initWords(path);
			jWordSplitter = new EnglishWordSplitter(true);
		}
		catch (IOException e)
		{	// problem while loading the dictionary
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{	// problem while deserializing the dictionary
			e.printStackTrace();
		}
		
		logger.decreaseOffset();
	}
	
	/**
	 * Uses the JWordSplitter library to split the strings.
	 * 
	 * @param strings
	 * 		List of strings to be split.
	 * @return
	 * 		List of resulting substrings.
	 */
	public List<String> applyJWordSplitter(List<String> strings)
	{	logger.increaseOffset();
	
		logger.log("Processing each string individually");
		logger.increaseOffset();
		List<String> result = new ArrayList<String>();
		for(String string: strings)
		{	logger.log("Processing string '"+string+"'");
			
			// apply the splitter
			Collection<String> temp = jWordSplitter.splitWord(string);
			for(String str : temp)
			{	if(!str.isEmpty())
					result.add(str);
			}
		}
		logger.decreaseOffset();
		
		// log result
		String msg = " Result:";
		for(String s: result)
			msg = msg + " '"+s+"'";
		logger.log(msg);

		logger.decreaseOffset();
		return result;
	}

	///////////////////////////////////////////////////////////
	//	WORDSPLIT							///////////////////
	///////////////////////////////////////////////////////////
	/**
	 * Uses the WordSplit library to split the strings.
	 * 
	 * @param strings
	 * 		List of strings to be split.
	 * @return
	 * 		List of resulting substrings.
	 */
	public List<String> applyWordSplit(List<String> strings)
	{	logger.increaseOffset();
		TextSegmenter wordSplit = WordSplitTools.getAccess();
		
		logger.log("Processing each string individually");
		logger.increaseOffset();
		List<String> result = new ArrayList<String>();
		for(String string: strings)
		{	logger.log("Processing string '"+string+"'");
			
			// apply the splitter
			List<String> temp = wordSplit.split(string);
			for(String str: temp)
			{	if(!str.isEmpty())
					result.add(str);
			}
		}
		logger.decreaseOffset();
	
		// log result
		String msg = " Result:";
		for(String s: result)
			msg = msg + " '"+s+"'";
		logger.log(msg);

		logger.decreaseOffset();
		return result;
	}
}
