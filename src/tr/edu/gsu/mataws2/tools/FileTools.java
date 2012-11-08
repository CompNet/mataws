package tr.edu.gsu.mataws2.tools;

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

import java.io.File;

/**
 * This class is used to extract parameters and subparameters of a given
 * collection.
 * 
 * @author Vincent Labatut
 * 
 */
public class FileTools
{	
	public static final String RESOURCES_FOLDER = "resource";
	public static final String KNOWBASE_FOLDER = RESOURCES_FOLDER + File.separator + "kbs";
	public static final String SPLITTER_FOLDER = RESOURCES_FOLDER + File.separator + "splitter";
	public static final String WORDNET_FOLDER = RESOURCES_FOLDER + File.separator + "wordnet";
}