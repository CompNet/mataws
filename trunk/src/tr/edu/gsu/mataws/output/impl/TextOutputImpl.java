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

import tr.edu.gsu.mataws.analyzer.AnalysisType;
import tr.edu.gsu.mataws.components.TraceType;
import tr.edu.gsu.mataws.components.TraceableParameter;
import tr.edu.gsu.mataws.output.Output;
import tr.edu.gsu.mataws.statistics.StatisticsUtil;

/**
 * This class which is an implementation of Output, prepares
 * a string representation of results and saves it as a text file.
 * 
 * @author Koray Mancuhan & Cihan Aksoy
 *
 */
public class TextOutputImpl implements Output{

	private String result;
	private StatisticsUtil statistics;
	private NumberFormat numberFormat;
	
	public TextOutputImpl(){
		result = "";
		statistics = StatisticsUtil.getInstance();
		numberFormat = new DecimalFormat("#.00");
	}
	
	@Override
	public void write(TraceableParameter tParameter, List<String> preprocessingResult,
			String wordToAnnotate, AnalysisType analysisType, String concept) {
		
		String string = tParameter.getName();
		if (preprocessingResult.size() != 0) {
			if (!tParameter.getName().equals(preprocessingResult.get(0))) {
				for (int i = 0; i < preprocessingResult.size(); i++) {
					string += ("," + preprocessingResult.get(i));
				}
			}
			else{
				for (int i = 1; i < preprocessingResult.size(); i++) {
					string += ("," + preprocessingResult.get(i));
				}
			}
			string += "," + wordToAnnotate;
			string += "," + analysisType;
			string += "," + concept;
			
			for (TraceType tt : tParameter.getTraceList()) {
				string += "," + tt.toString();
			}
			
		} else {
			string += ",NoMatch";
		}
		result += string + "\n" ;
	}

	public void save() {
		File file=findFileName();
		try {
			file.createNewFile();
			BufferedWriter bw=new BufferedWriter(new FileWriter(file));
			
			String annotationResultForTotalParams = numberFormat.format(((double)statistics.getAnnotatedParameters().size() / statistics.getAllParameterObjects().size()) * 100);
			String annotationResultForDifferentParams = numberFormat.format(((double)statistics.getDifferentAnnotatedParameters().size() / statistics.getAllDifferentParameters().size()) * 100);
			
			result += "\n\n";
			result += "******************************Statistics******************************"+"\n";
			result += "*                                                                    *"+"\n";
			result += "* ****************************************************************** *"+"\n";
			result += "* *                      Parameter Statistics                      * *"+"\n";
			result += "* ****************************************************************** *"+"\n";
			result += "* *                          Total Results                         * *"+"\n";
			result += "* *     ******************************************************     * *"+"\n";
			result += "        Total number of parameters: "+statistics.getAllParameterObjects().size()+"\n";
			result += "        Number of annotated parameters: "+statistics.getAnnotatedParameters().size()+"\n";
			result += "        Number of non-annotated parameters: "+statistics.getNonAnnotatedParameters().size()+"\n";
			result += "        Percent of annotated parameters: "+annotationResultForTotalParams+"\n";
			result += "* *                         Unique Results                         * *"+"\n";
			result += "* *     ******************************************************     * *"+"\n";
			result += "        Number of unique parameters: "+statistics.getAllDifferentParameters().size()+"\n";
			result += "        Number of unique annotated parameters: "+statistics.getDifferentAnnotatedParameters().size()+"\n";
			result += "        Number of unique non-annotated parameters: "+statistics.getDifferentNonAnnotatedParameters().size()+"\n";
			result += "        Percent of unique annotated parameters: "+annotationResultForDifferentParams+"\n";
			result += "* ****************************************************************** *"+"\n";
			result += "*                                                                    *"+"\n";
			result += "* *                        Analyze Statistics                      * *"+"\n";
			result += "* ****************************************************************** *"+"\n";
			result += "* *                      Analyze Types Results                     * *"+"\n";
			result += "* *     ******************************************************     * *"+"\n";
			result += "        Number of NonNounVerbAnnotation: "+statistics.getAnalyzeTypesCounter().get(AnalysisType.NonNounVerbAnnotation)+"\n";
			result += "        Number of OnlyOneRemaining: "+statistics.getAnalyzeTypesCounter().get(AnalysisType.OnlyOneRemaining)+"\n";
			result += "        Number of OnlyOneRepresenter: "+statistics.getAnalyzeTypesCounter().get(AnalysisType.OnlyOneRepresenter)+"\n";
			result += "        Number of HypernymialRelation: "+statistics.getAnalyzeTypesCounter().get(AnalysisType.HypernymialRelation)+"\n";
			result += "        Number of HolonymialRelation: "+statistics.getAnalyzeTypesCounter().get(AnalysisType.HolonymialRelation)+"\n";
			result += "        Number of SimpleVerbAnnotation: "+statistics.getAnalyzeTypesCounter().get(AnalysisType.SimpleVerbAnnotation)+"\n";
			result += "        Number of NounAdjunct: "+statistics.getAnalyzeTypesCounter().get(AnalysisType.NounAdjunct)+"\n";
			result += "        Number of NoAnalysis: "+statistics.getAnalyzeTypesCounter().get(AnalysisType.NoAnalysis)+"\n";
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
