/*
 * Mataws - Multimodal Automatic Tool for the Annotation of  Web Services
 * Copyright 2011 Cihan Aksoy & Koray Man�uhan
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
package tr.edu.gsu.mataws.preprocessing.filtering;

import java.util.List;

import tr.edu.gsu.mataws.preprocessing.PreprocessingStrategy;
/**
 * Interface for various filtering strategies
 *  
 * @author Koray Mancuhan & Cihan Aksoy
 *
 */
public interface FilteringStrgy extends PreprocessingStrategy{
	/**
	 * Returns filtered little words of a parameter name.
	 * 
	 * @param paramName
	 * 			little words list of a parameter name. 
	 * @return
	 * 			filtered little words of a parameter name.
	 * 			
	 */
	public List<String> execute(List<String> paramName);

}
