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
package tr.edu.gsu.mataws.stats;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import tr.edu.gsu.mataws.analyzer.AnalysisType;
import tr.edu.gsu.mataws.components.CsvObjectForParams;
import tr.edu.gsu.mataws.components.CsvObjectForWords;
import tr.edu.gsu.mataws.components.TraceableParameter;
import tr.edu.gsu.mataws.output.Output;
import tr.edu.gsu.mataws.toolbox.StandardDeviation;

public class CsvOutputImpl implements Output {

	// this variable is for holding csv format result of parameters
	private String resultForParams;

	// this variable is for holding csv format result of words
	private String resultForWords;

	// this variable contains coming parameters to control the new one whether
	// it is found or not
	private List<CsvObjectForParams> controlListForUniqueParams;

	// this variable contains coming parameters to control the new one whether
	// it is found or not
	private List<CsvObjectForWords> controlListForUniqueWords;

	// this variable holds coming parameter if it is already found in control
	// list
	private CsvObjectForParams foundParam;

	// this variable holds coming word if it is already found in control
	// list
	private CsvObjectForWords foundWord;

	// these variables are to calculate standard deviation of occurences for
	// params and words
	private StandardDeviation sd;
	private double[] sdForParams;
	private double[] sdForWords;
	private NumberFormat numberFormat;

	public CsvOutputImpl() {
		resultForParams = "";
		resultForWords = "";
		controlListForUniqueParams = new ArrayList<CsvObjectForParams>();
		controlListForUniqueWords = new ArrayList<CsvObjectForWords>();
		foundParam = null;
		foundWord = null;
		sd = new StandardDeviation();
		numberFormat = new DecimalFormat("#.00");
	}

	@Override
	public void write(TraceableParameter tParameter,
			List<String> preprocessingResult, String wordToAnnotate,
			AnalysisType analysisType, String concept) {

		// parameter statistics are calculated
		CsvObjectForParams coForParam = new CsvObjectForParams(tParameter,
				wordToAnnotate, concept, analysisType.name());

		if (!isFound(coForParam)) {
			controlListForUniqueParams.add(coForParam);
			int selected = 0;

			if (Math.random() > 0.5)
				selected = 1;

			coForParam.setSelected(selected);
			coForParam.setOccurence(1);
		} else {
			int index = controlListForUniqueParams.indexOf(foundParam);
			CsvObjectForParams toModify = controlListForUniqueParams.get(index);
			int occurence = toModify.getOccurence();
			toModify.setOccurence(occurence + 1);
			controlListForUniqueParams.set(index, toModify);
		}

		// word statistics are calculated
		for (String string : preprocessingResult) {
			CsvObjectForWords coForWord = new CsvObjectForWords(string, concept);

			if (!isWordFound(coForWord)) {
				controlListForUniqueWords.add(coForWord);
				int selected = 0;

				if (Math.random() > 0.5)
					selected = 1;

				coForWord.setSelected(selected);
				coForWord.setOccurence(1);
			} else {
				int index = controlListForUniqueWords.indexOf(foundWord);
				CsvObjectForWords toModify = controlListForUniqueWords
						.get(index);
				int occurence = toModify.getOccurence();
				toModify.setOccurence(occurence + 1);
				controlListForUniqueWords.set(index, toModify);
			}
		}

	}

	private boolean isFound(CsvObjectForParams csv) {

		for (CsvObjectForParams csvControl : controlListForUniqueParams) {
			if (csvControl.getParameter().getParameter().getName()
					.equals(csv.getParameter().getParameter().getName())
					&& csvControl
							.getParameter()
							.getParameter()
							.getTypeName()
							.equals(csv.getParameter().getParameter()
									.getTypeName())) {
				if (csvControl.getParameter().getParameter().getConceptURI() != null
						&& csv.getParameter().getParameter().getConceptURI() != null) {
					if (!csvControl
							.getParameter()
							.getParameter()
							.getConceptURI()
							.equals(csv.getParameter().getParameter()
									.getConceptURI()))
						return false;
				}
				if (csvControl.getParameter().getParameter().getSubParameters() != null
						&& csv.getParameter().getParameter().getSubParameters() != null) {
					if (!csvControl
							.getParameter()
							.getParameter()
							.getSubParameters()
							.equals(csv.getParameter().getParameter()
									.getSubParameters()))
						return false;
				}
				foundParam = csvControl;
				return true;
			}
		}
		return false;
	}

	private boolean isWordFound(CsvObjectForWords coForWord) {
		for (CsvObjectForWords cofw : controlListForUniqueWords) {
			if (cofw.getWord().equals(coForWord.getWord())) {
				foundWord = cofw;
				return true;
			}
		}
		return false;
	}

	@Override
	public void save() {
		File fileForParams = findFileNameForParams();
		File fileForWords = findFileNameForWords();

		try {
			fileForParams.createNewFile();
			fileForWords.createNewFile();

			sdForParams = new double[controlListForUniqueParams.size()];
			sdForWords = new double[controlListForUniqueWords.size()];
			
			BufferedWriter bwForParams = new BufferedWriter(new FileWriter(
					fileForParams));
			BufferedWriter bwForWords = new BufferedWriter(new FileWriter(
					fileForWords));

			for (int i = 0; i<controlListForUniqueParams.size(); i++) {
				CsvObjectForParams csvObject = controlListForUniqueParams.get(i);
				resultForParams += csvObject.getParameter().getParameter()
						.getName()
						+ ","
						+ csvObject.getParameter().getParameter().getTypeName()
						+ ","
						+ csvObject.getOccurence()
						+ ","
						+ csvObject.getWordToAnnotate()
						+ ","
						+ csvObject.getConcept()
						+ ","
						+ csvObject.getSelected() + "\n";

				// for sd
				sdForParams[i] = csvObject.getOccurence();
			}

			for (int j = 0; j<controlListForUniqueWords.size();j++) {
				CsvObjectForWords csvObject = controlListForUniqueWords.get(j);
				resultForWords += csvObject.getWord() + ","
						+ csvObject.getOccurence() + ","
						+ csvObject.getConcept() + ","
						+ csvObject.getSelected() + "\n";

				// for sd
				sdForWords[j] = csvObject.getOccurence();
			}

			resultForParams += "\n standard deviation for parameters: "
					+ numberFormat.format(sd
							.StandardDeviationMean(sdForParams));
			bwForParams.write(resultForParams);
			bwForParams.close();

			resultForWords += "\n standard deviation for words: "
					+ numberFormat.format(sd
							.StandardDeviationMean(sdForWords));
			bwForWords.write(resultForWords);
			bwForWords.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Finds an appropriate file name for a log file of annotation.
	 * 
	 * @return File object which represents the appropriate log file.
	 */
	private static File findFileNameForParams() {
		String path = null;
		path = System.getProperty("user.dir") + File.separator + "statistics"
				+ File.separator;
		String index = "0", fileName = path + "csvForParams_" + index + ".txt";
		File file = new File(fileName);
		int i = Integer.parseInt(index);
		while (file.exists()) {
			i++;
			Integer ic = new Integer(i);
			index = ic.toString();
			fileName = path + "csvForParams_" + index + ".txt";
			file = new File(fileName);
		}
		return file;
	}

	/**
	 * Finds an appropriate file name for a log file of annotation.
	 * 
	 * @return File object which represents the appropriate log file.
	 */
	private static File findFileNameForWords() {
		String path = null;
		path = System.getProperty("user.dir") + File.separator + "statistics"
				+ File.separator;
		String index = "0", fileName = path + "csvForWords_" + index + ".txt";
		File file = new File(fileName);
		int i = Integer.parseInt(index);
		while (file.exists()) {
			i++;
			Integer ic = new Integer(i);
			index = ic.toString();
			fileName = path + "csvForWords_" + index + ".txt";
			file = new File(fileName);
		}
		return file;
	}
}
