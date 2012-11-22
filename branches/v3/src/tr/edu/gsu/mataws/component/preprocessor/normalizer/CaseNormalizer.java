package tr.edu.gsu.mataws.component.preprocessor.normalizer;

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
import java.util.Locale;

/**
 * Transform uppercase letters into their lowercase equivalent.
 * This process is not applied if the whole word is in uppercase letters,
 * because it might be an acronym.
 * <br/>
 * Example: <b>{@code "My"}</b> {@code "CC"}, <b>{@code "Parameter"}</b> -> <b>{@code "my"}</b>, {@code "CC"}, <b>{@code "parameter"}</b>
 *   
 * @author Koray Mancuhan
 * @author Cihan Aksoy
 * @author Vincent Labatut
 */
public class CaseNormalizer implements NormalizerInterface
{	
	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
    @Override
	public List<String> normalize(List<String> strings)
	{	List<String> result = new ArrayList<String>();
		
		for(String string: strings)
		{	String temp = string;
			if(string.length()>1 && !Character.isUpperCase(string.charAt(1)))
				temp = string.toLowerCase(Locale.ENGLISH);
			result.add(temp);
		}
		
		return result;
	}
}
