/*
 * Mataws - Multimodal Automatic Tool for the Annotation of  Web Services
 * Copyright 2011 Cihan Aksoy & Koray Mançuhan
 * 
 * This file is part of Mataws - Multimodal Automatic Tool for the Annotation of  Web Services.
 * 
 * Mataws - Multimodal Automatic Tool for the Annotation of  Web Services is 
 * free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 * 
 * Mataws - Multimodal Automatic Tool for the Annotation of  Web Services 
 * is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Mataws - Multimodal Automatic Tool for the Annotation of  Web Services.
 * If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package tr.edu.gsu.mataws.preprocessing.strategyGroups.impl;

import java.util.ArrayList;
import java.util.List;

import tr.edu.gsu.mataws.components.TraceType;
import tr.edu.gsu.mataws.components.TraceableParameter;
import tr.edu.gsu.mataws.preprocessing.PreprocessingStrategy;
import tr.edu.gsu.mataws.preprocessing.decomposition.impl.MajusculeAfterMinusculeDecomposition;
import tr.edu.gsu.mataws.preprocessing.decomposition.impl.NumberDecomposition;
import tr.edu.gsu.mataws.preprocessing.decomposition.impl.SpecialCharacterDecomposition;
import tr.edu.gsu.mataws.preprocessing.decomposition.impl.TwoMajusOneMinusDecomposition;
import tr.edu.gsu.mataws.preprocessing.filtering.impl.StopWordFiltering;
import tr.edu.gsu.mataws.preprocessing.normalization.impl.AbbreviationNormalization;
import tr.edu.gsu.mataws.preprocessing.normalization.impl.CharacterNormalization;
import tr.edu.gsu.mataws.preprocessing.strategyGroups.PreprocessingSet;

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
	public List<String> processName(TraceableParameter tParameter) {

		List<String> processedParam = new ArrayList<String>();
		
		//stringSQLExample
		processedParam = this.decomposeParameterName(tParameter.getName());
		tParameter.addTraceList(TraceType.Decomposition);
		//string,SQL,Example
		processedParam = this.normalizeParameterName(processedParam);
		tParameter.addTraceList(TraceType.Normalization);
		//string, structuredQueryLanguage, example
		processedParam = this.decomposeParameterName(processedParam);
		tParameter.addTraceList(TraceType.Decomposition);
		//string, structured, Query, Language, example
		processedParam = this.normalizeParameterName(processedParam);
		tParameter.addTraceList(TraceType.Normalization);
		//string, structured, query, language, example
		processedParam = this.filterParameterName(processedParam);
		tParameter.addTraceList(TraceType.Filtering);
		
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
		preprocessingStrategy = new MajusculeAfterMinusculeDecomposition();
		result = preprocessingStrategy.execute(result);
		preprocessingStrategy = new NumberDecomposition();
		result = preprocessingStrategy.execute(result);
		preprocessingStrategy = new TwoMajusOneMinusDecomposition();
		result = preprocessingStrategy.execute(result);
		preprocessingStrategy = new SpecialCharacterDecomposition("_");
		result = preprocessingStrategy.execute(result);
		preprocessingStrategy = new SpecialCharacterDecomposition("-");
		result = preprocessingStrategy.execute(result);
		preprocessingStrategy = new SpecialCharacterDecomposition(" ");
		result = preprocessingStrategy.execute(result);
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
		result = preprocessingStrategy.execute(result);
		preprocessingStrategy = new AbbreviationNormalization();
		result = preprocessingStrategy.execute(result);

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
		result = preprocessingStrategy.execute(result);
		return result;
	}
}
