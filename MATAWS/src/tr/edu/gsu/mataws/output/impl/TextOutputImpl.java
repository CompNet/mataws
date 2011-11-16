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
package tr.edu.gsu.mataws.output.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import tr.edu.gsu.mataws.output.Output;
import tr.edu.gsu.mataws.statistics.Statistics;
import tr.edu.gsu.mataws.statistics.impl.StatisticsUtil;

/**
 * This class which is an implementation of Output, prepares
 * a string representation of results and saves it as a text file.
 * 
 * @author Koray Mancuhan & Cihan Aksoy
 *
 */
public class TextOutputImpl implements Output {

	private String result;
	private Statistics statistics;
	private NumberFormat numberFormat;
	
	public TextOutputImpl(){
		result = "";
		statistics = StatisticsUtil.getInstance();
		numberFormat = new DecimalFormat("#.00");
	}
	
	@Override
	public void write(String parameterName,
			List<String> decompositionResult, List<String> annotationResult) {
		
		String string = parameterName;
		if (decompositionResult.size() != 0) {
			if (!parameterName.equals(decompositionResult.get(0))) {
				for (int i = 0; i < decompositionResult.size(); i++) {
					string += ("," + decompositionResult.get(i));
				}
			}
			else{
				for (int i = 1; i < decompositionResult.size(); i++) {
					string += ("," + decompositionResult.get(i));
				}
			}
			for (int i = 0; i < annotationResult.size(); i++) {
				string += ("," + annotationResult.get(i));
			}
		} else {
			string += ",NoMatch";
		}
		result += string + "\n" ;
	}

	@Override
	public void save() {
		File file=findFileName();
		try {
			file.createNewFile();
			BufferedWriter bw=new BufferedWriter(new FileWriter(file));
			
			String annotationResultForTotalParams = numberFormat.format(((double)statistics.getAnnotatedParameters().size() / statistics.getAllParameterObjects().size()) * 100);
			String annotationResultForDifferentParams = numberFormat.format(((double)statistics.getDifferentAnnotatedParameters().size() / statistics.getAllDifferentParameters().size()) * 100);
			String annotationResultForTotalWords = numberFormat.format(((double)statistics.getAnnotatedWords().size() / statistics.getAllWords().size()) * 100);
			String annotationResultForDifferentWords = numberFormat.format(((double)statistics.getDifferentAnnotatedWords().size() / statistics.getAllDifferentWords().size()) * 100);
			
			result += "\n\n";
			result += "******************************Statistics******************************"+"\n";
			result += "*                                                                    *"+"\n";
			result += "* ****************************************************************** *"+"\n";
			result += "* *                      Parameter Statistics                      * *"+"\n";
			result += "* ****************************************************************** *"+"\n";
			result += "* *                          Total Results                         * *"+"\n";
			result += "* *     ******************************************************     * *"+"\n";
			result += "        Number of total parameters: "+statistics.getAllParameterObjects().size()+"\n";
			result += "        Number of total annotated parameters: "+statistics.getAnnotatedParameters().size()+"\n";
			result += "        Number of total non-annotated parameters: "+statistics.getNonAnnotatedParameters().size()+"\n";
			result += "        Total parameter annotation percentage: "+annotationResultForTotalParams+"\n";
			result += "* *                         Unique Results                         * *"+"\n";
			result += "* *     ******************************************************     * *"+"\n";
			result += "        Number of different parameters: "+statistics.getAllDifferentParameters().size()+"\n";
			result += "        Number of different annotated parameters: "+statistics.getDifferentAnnotatedParameters().size()+"\n";
			result += "        Number of different non-annotated parameters: "+statistics.getDifferentNonAnnotatedParameters().size()+"\n";
			result += "        Different parameter annotation percentage: "+annotationResultForDifferentParams+"\n";
			result += "* ****************************************************************** *"+"\n";
			result += "* *                         Word Statistics                        * *"+"\n";
			result += "* ****************************************************************** *"+"\n";
			result += "* *                          Total Results                         * *"+"\n";
			result += "* *     ******************************************************     * *"+"\n";
			result += "        Number of total words: "+statistics.getAllWords().size()+"\n";
			result += "        Number of total annotated words: "+statistics.getAnnotatedWords().size()+"\n";
			result += "        Number of total non-annotated words: "+statistics.getNonAnnotatedWords().size()+"\n";
			result += "        Total word annotation percentage: "+annotationResultForTotalWords+"\n";
			result += "* *                         Unique Results                         * *"+"\n";
			result += "* *     ******************************************************     * *"+"\n";
			result += "        Number of different words: "+statistics.getAllDifferentWords().size()+"\n";
			result += "        Number of different annotated words: "+statistics.getDifferentAnnotatedWords().size()+"\n";
			result += "        Number of different non-annotated words: "+statistics.getDifferentNonAnnotatedWords().size()+"\n";
			result += "        Different word annotation percentage: "+annotationResultForDifferentWords+"\n";
			result += "* ****************************************************************** *"+"\n";
			result += "* *                             MATAWS                             * *"+"\n";
			result += "**********************************************************************"+"\n";

			bw.write(result);
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Finds an appropriate file name for a log file of annotation.
	 * 
	 * @return
	 * 		File object which represents the appropriate log file.
	 */
	private static File findFileName(){
		String path = null;
		path = System.getProperty("user.dir") + File.separator + "statistics" + File.separator;
		String index="0", fileName=path+"statistics_"+index+".txt";
		File file=new File(fileName);
		int i=Integer.parseInt(index);
		while(file.exists()){
			i++;
			Integer ic=new Integer(i);
			index=ic.toString();
			fileName=path+"statistics_"+index+".txt";
			file=new File(fileName);
		}
		return file;
	}
}
