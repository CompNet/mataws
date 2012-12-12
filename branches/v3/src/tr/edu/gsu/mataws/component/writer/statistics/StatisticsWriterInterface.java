package tr.edu.gsu.mataws.component.writer.statistics;

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

import java.io.FileNotFoundException;
import java.util.List;

import tr.edu.gsu.mataws.data.MatawsParameter;

/**
 * This class is used to write some metadata regarding the annotation
 * process, as various text files.
 * 
 * @author Vincent Labatut
 */
public interface StatisticsWriterInterface
{
	/**
	 * Records some statistics describing how the annotation
	 * process went. The generated files will be put in Mataws output folder.
	 * <br/>
	 * If {@code subfolder} is {@code null}, then all description files are processed.
	 * 
	 * @param subfolder
	 *		The folder containing the collection.
	 * @param parameters
	 * 		The list of annotated parameters. 
	 * 
	 * @throws FileNotFoundException 
	 * 		If no file could be found at the specified location. 
	 */
	public void write(String subfolder, List<MatawsParameter> parameters) throws FileNotFoundException;
}
