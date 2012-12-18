package tr.edu.gsu.mataws.component.preparator.normalizer;

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

import java.util.ArrayList;
import java.util.List;

import tr.edu.gsu.mataws.tools.semantics.JawsTools;
import tr.edu.gsu.mataws.tools.semantics.JwiTools;

/**
 * Replace a word by its stem (or lemma form).
 * Plural nouns are replaced by the singular version,
 * conjugated verbs are replaced by the infinitive form, etc.
 * <br/>
 * Example: {@code "Number"}, {@code "Of"}, <b>{@code "Tries"}</b> -> {@code "Number"}, {@code "Of"}, <b>{@code "Try"}</b>
 *   
 * @author Koray Mancuhan
 * @author Cihan Aksoy
 * @author Vincent Labatut
 */
public class StemNormalizer extends AbstractNormalizer
{	
	/**
	 * Builds and initialize a stem normalizer.
	 * 
	 * @param mode 
	 * 		Specifies how WordNet is accessed.
	 */
	public StemNormalizer(Mode mode)
	{	super();
	
		this.mode = mode;
	}
	
	///////////////////////////////////////////////////////////
	//	MODE								///////////////////
	///////////////////////////////////////////////////////////
	/** Represents the access mode of this normalizer to Word the lexicon */
	public enum Mode
	{	/** Use the Jaws API to access WordNet */
		JWAS,
		/** Use the JWI API to access WordNet */
		JWI;
	}
	
	/** Represents the library used to perform the normalization */
	private Mode mode;
	
	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	@Override
	public List<String> normalize(List<String> strings)
	{	String msg = "Normalizing using stems, for the strings: ";
		for(String string: strings)
			msg = msg + " '" + string + "'";
		logger.log(msg);
		logger.increaseOffset();
		List<String> result = null; 
	
		if(mode==Mode.JWAS)
			result = applyJaws(strings);
		else if(mode==Mode.JWI)
			result = applyJwi(strings);
		
		logger.decreaseOffset();
		return result;
	}
	
	///////////////////////////////////////////////////////////
	//	JAWS								///////////////////
	///////////////////////////////////////////////////////////
	/**
	 * Use Jaws to normalize the strings.
	 * 
	 * @param strings
	 * 		List of string to be normalized.
	 * @return
	 * 		Result of the normalization.
	 */
	public List<String> applyJaws(List<String> strings)
	{	logger.log("Using Jaws");
		logger.increaseOffset();
		List<String> result = new ArrayList<String>(); 
		
		logger.log("Processing each string individually");
		logger.increaseOffset();
		for(String string: strings)
		{	String stem = JawsTools.getStem(string);
			result.add(stem);
			logger.log("String '"+string+"' becomes '"+stem+"'");
		}
		logger.decreaseOffset();
		
		logger.decreaseOffset();
		return result;
	}

	///////////////////////////////////////////////////////////
	//	JWI									///////////////////
	///////////////////////////////////////////////////////////
	/**
	 * Use Jaws to normalize the strings.
	 * 
	 * @param strings
	 * 		List of string to be normalized.
	 * @return
	 * 		Result of the normalization.
	 */
	public List<String> applyJwi(List<String> strings)
	{	logger.log("Using Jwi");
		logger.increaseOffset();
		List<String> result = new ArrayList<String>(); 
		
		logger.log("Processing each string individually");
		logger.increaseOffset();
		for(String string: strings)
		{	String stem = JwiTools.getStem(string);
			result.add(stem);
			logger.log("String '"+string+"' becomes '"+stem+"'");
		}
		logger.decreaseOffset();
		
		logger.decreaseOffset();
		return result;
	}
}
