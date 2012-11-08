package tr.edu.gsu.mataws.output;

/*
 * Mataws - Multimodal Automatic Tool for the Annotation of Web Services
 * Copyright 2011-12 Cihan Aksoy & Koray Mançuhan
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

import tr.edu.gsu.mataws.analyzer.AnalysisType;
import tr.edu.gsu.mataws.components.TraceableParameter;

/**
 * Interface for saving annotation process informations and 
 * certain statistics.
 * 
 * @author Cihan Aksoy
 *
 */
public interface Output {

	/**
	 * Method for recording unit result including 
	 * parameter name and relevant annotation results.
	 * 
	 * @param tParameter
	 * @param preprocessingResult
	 * @param wordToAnnotate
	 * @param analyzeType
	 * @param concept
	 */
	void write(TraceableParameter tParameter, List<String> preprocessingResult,
			String wordToAnnotate, AnalysisType analysisType, String concept);
	
	/**
	 * Method for recording total statistical results.
	 */
	void save();
}
