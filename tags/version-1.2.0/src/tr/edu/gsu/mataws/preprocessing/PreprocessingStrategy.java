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
package tr.edu.gsu.mataws.preprocessing;

import java.util.List;

/**
 * Interface for various preprocessing strategies.
 *  
 * @author Koray Mancuhan & Cihan Aksoy
 *
 */
public interface PreprocessingStrategy {

	/**
	 * This method that enforces the Strategy Pattern 
	 * is used to make preprocessing operations to the given words
	 * 
	 * @param paramNames
	 * @return a list of words
	 */
	public List<String> execute(List<String> paramNames);

	
}