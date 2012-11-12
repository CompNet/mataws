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

public class CsvObjectForWords {

	private String word;
	private int occurence;
	private String concept;
	private int selected;

	public CsvObjectForWords(String word, String concept) {
		this.word = word;
		this.concept = concept;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getOccurence() {
		return occurence;
	}

	public void setOccurence(int occurence) {
		this.occurence = occurence;
	}

	public String getConcept() {
		return concept;
	}

	public void setConcept(String concept) {
		this.concept = concept;
	}

	public int getSelected() {
		return selected;
	}

	public void setSelected(int selected) {
		this.selected = selected;
	}

}
