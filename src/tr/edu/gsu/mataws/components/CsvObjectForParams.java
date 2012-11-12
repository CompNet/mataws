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
package tr.edu.gsu.mataws.components;

public class CsvObjectForParams {

	private TraceableParameter parameter;
	private int occurence;
	private String wordToAnnotate;
	private String concept;
	private String analysisType;
	private String operationName;
	private int selected;

	public CsvObjectForParams(TraceableParameter parameter, String wordToAnnotate,
			String concept, String analysisType) {

		this.parameter = parameter;
		this.wordToAnnotate = wordToAnnotate;
		this.concept = concept;
		this.analysisType = analysisType;
	}

	public TraceableParameter getParameter() {
		return parameter;
	}

	public void setParameter(TraceableParameter parameter) {
		this.parameter = parameter;
	}

	public int getOccurence() {
		return occurence;
	}

	public void setOccurence(int occurence) {
		this.occurence = occurence;
	}

	public String getWordToAnnotate() {
		return wordToAnnotate;
	}

	public void setWordToAnnotate(String wordToAnnotate) {
		this.wordToAnnotate = wordToAnnotate;
	}

	public String getConcept() {
		return concept;
	}

	public void setConcept(String concept) {
		this.concept = concept;
	}

	public String getAnalysisType() {
		return analysisType;
	}

	public void setAnalysisType(String analysisType) {
		this.analysisType = analysisType;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public int getSelected() {
		return selected;
	}

	public void setSelected(int selected) {
		this.selected = selected;
	}

}
