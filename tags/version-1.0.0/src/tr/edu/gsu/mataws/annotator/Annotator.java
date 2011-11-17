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
package tr.edu.gsu.mataws.annotator;

import java.util.List;
import java.util.Queue;

import tr.edu.gsu.mataws.components.Node;

/**
 * Interface for annotation of a parameter that encapsulated
 * as a Node.
 * 
 * @author Cihan Aksoy
 *
 */
public interface Annotator {

	/**
	 * Annotates a parameter and relevant fields.
	 * 
	 * @param queue
	 * 			FIFO structure that holds the parameter and its sub parameters.
	 * @param decompositionResult
	 * 			List that holds words come from preprocessing of a parameter.
	 * @param annotationResult
	 * 			List that holds concepts corresponding with words of decompositionResult
	 */
	public  void annotate(Queue<Node> queue, List<String> decompositionResult, List<String> annotationResult);
}
