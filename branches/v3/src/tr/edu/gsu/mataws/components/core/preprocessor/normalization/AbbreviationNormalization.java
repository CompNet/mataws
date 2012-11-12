package tr.edu.gsu.mataws.components.core.preprocessor.normalization;

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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Normalization Strategy which detects and finds the meaning of abbreviations
 * in the list of little words of a parameter name.
 *   
 * @author Koray Mancuhan & Cihan Aksoy
 *
 */
public class AbbreviationNormalization implements NormalizationStrategy {

	@Override
	public List<String> execute(List<String> paramName) {
		
		List<String> results = paramName;
		ArrayList<String> abbreviationList=new ArrayList<String>();
		File file = null;
		file=new File(System.getProperty("user.dir")+ File.separator +"configurations" + File.separator + "Abbreviations.txt");
		
		try {
			BufferedReader br=new BufferedReader(new FileReader(file));
			String line=null;
			while((line=br.readLine())!=null){
				abbreviationList.add(line);
			}
			br.close();
			
			for(int i = 0; i < results.size(); i++)
			{
				String param = results.get(i);
				for(int j = 0; j < abbreviationList.size(); j++)
				{
					String oneOfAbbreviation = abbreviationList.get(j);
					String[] abbrevs = oneOfAbbreviation.split(",");
					if(param.equals(abbrevs[0]))
					{
						results.set(i, abbrevs[1]);
						break;
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return results;
	}

}
