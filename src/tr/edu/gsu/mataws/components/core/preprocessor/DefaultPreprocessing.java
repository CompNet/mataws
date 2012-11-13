package tr.edu.gsu.mataws.components.core.preprocessor;

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

import java.util.ArrayList;
import java.util.List;

import tr.edu.gsu.mataws.components.core.preprocessor.division.LettercaseBasedDivision;
import tr.edu.gsu.mataws.components.core.preprocessor.division.NumberBasedDivision;
import tr.edu.gsu.mataws.components.core.preprocessor.division.SeparatorBasedDivision;
import tr.edu.gsu.mataws.components.core.preprocessor.division.TwoMajusOneMinusDecomposition;
import tr.edu.gsu.mataws.components.core.preprocessor.filtering.StopWordFiltering;
import tr.edu.gsu.mataws.components.core.preprocessor.normalization.AbbreviationNormalization;
import tr.edu.gsu.mataws.components.core.preprocessor.normalization.CharacterNormalization;
import tr.edu.gsu.mataws.trace.TraceType;
import tr.edu.gsu.mataws.trace.TraceableParameter;

/**
 * An implementation of PreprocessingSet corresponding with 
 * FullDataset collection.
 *   
 * @author Koray Mancuhan & Cihan Aksoy
 *
 */
public class DefaultPreprocessing implements PreprocessingSet{

	private PreprocessingStrategy preprocessingStrategy;
	
	@Override
	public List<String> processName(TraceableParameter tParameter, String toProcess) {

		List<String> processedParam = new ArrayList<String>();
		
		//stringSQLExample
		processedParam = this.decomposeParameterName(toProcess);
		tParameter.addTraceList(TraceType.DECOMPOSITION);
		//string,SQL,Example
		processedParam = this.normalizeParameterName(processedParam);
		tParameter.addTraceList(TraceType.NORMALIZATION);
		//string, structuredQueryLanguage, example
		processedParam = this.decomposeParameterName(processedParam);
		tParameter.addTraceList(TraceType.DECOMPOSITION);
		//string, structured, Query, Language, example
		processedParam = this.normalizeParameterName(processedParam);
		tParameter.addTraceList(TraceType.NORMALIZATION);
		//string, structured, query, language, example
		processedParam = this.filterParameterName(processedParam);
		tParameter.addTraceList(TraceType.FILTERING);
		
		return processedParam;
	}
	
	/**
	 * Service method decomposing a parameter name.
	 * 
	 * @param paramName
	 * 			a parameter name
	 * @return
	 * 			the result of name decomposition
	 */
	private List<String> decomposeParameterName(String paramName) {
		List<String> result = new ArrayList<String>();
		result.add(paramName);
		preprocessingStrategy = new LettercaseBasedDivision();
		result = preprocessingStrategy.divide(result);
		preprocessingStrategy = new NumberBasedDivision();
		result = preprocessingStrategy.divide(result);
		preprocessingStrategy = new TwoMajusOneMinusDecomposition();
		result = preprocessingStrategy.divide(result);
		preprocessingStrategy = new SeparatorBasedDivision("_");
		result = preprocessingStrategy.divide(result);
		preprocessingStrategy = new SeparatorBasedDivision("-");
		result = preprocessingStrategy.divide(result);
		preprocessingStrategy = new SeparatorBasedDivision(" ");
		result = preprocessingStrategy.divide(result);
		return result;
	}
	
	/**
	 * Service method decomposing a list of parameter names.
	 * 
	 * @param paramNames
	 * 			a list of parameter name
	 * @return
	 * 			the results of name decomposition
	 */
	private List<String> decomposeParameterName(List<String> paramNames){
		List<String> result = new ArrayList<String>();
		List<String> temp = new ArrayList<String>();
		
		for (int i = 0; i < paramNames.size(); i++) {
			temp = decomposeParameterName(paramNames.get(i));
			for (int j = 0; j < temp.size(); j++) {
				result.add(temp.get(j));
			}
		}
		return result;
	}

	/**
	 * Service method normalizing a parameter name
	 * 
	 * @param decompositionResult
	 * 			list holding the result of name decomposition
	 * @return
	 * 			the result of name normalization
	 */
	private List<String> normalizeParameterName(List<String> decompositionResult) {
		List<String> result = decompositionResult;
		preprocessingStrategy = new CharacterNormalization();
		result = preprocessingStrategy.divide(result);
		preprocessingStrategy = new AbbreviationNormalization();
		result = preprocessingStrategy.divide(result);

		return result;
	}

	/**
	 * Service method filtering a parameter name
	 * 
	 * @param decompositionResult
	 * 			list holding the result of name decomposition and normalization
	 * @return
	 * 			the result of name filtering
	 */
	private List<String> filterParameterName(List<String> decompositionResult) {
		List<String> result = decompositionResult;
		preprocessingStrategy = new StopWordFiltering();
		result = preprocessingStrategy.divide(result);
		return result;
	}
}
