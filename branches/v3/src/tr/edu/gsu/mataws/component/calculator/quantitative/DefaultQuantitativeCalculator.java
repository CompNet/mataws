package tr.edu.gsu.mataws.component.calculator.quantitative;

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

import java.util.List;

import edu.smu.tspell.wordnet.Synset;

import tr.edu.gsu.mataws.data.parameter.IdentifiedWord;
import tr.edu.gsu.mataws.data.parameter.MatawsParameter;
import tr.edu.gsu.mataws.data.stat.CollectionStats;
import tr.edu.gsu.mataws.data.stat.ParameterStats;

/**
 * Default class for processing statistics regarding
 * the quantitative aspect of the annotations.
 *   
 * @author Vincent Labatut
 */
public class DefaultQuantitativeCalculator extends AbstractQuantitativeCalculator
{
	///////////////////////////////////////////////////////////
	//	PROCESS								///////////////////
	///////////////////////////////////////////////////////////
	@Override
	public CollectionStats calculate(List<MatawsParameter> parameters)
	{	logger.log("Calculating the quantitative statistics");
		logger.increaseOffset();
		
		logger.log("Initializes the stat object");
		CollectionStats result = new CollectionStats(parameters);
		
		logger.log("Proceeding with quantitative evaluation for parameter instances");
		List<ParameterStats> instances = result.getParameterInstanceStats();
		int totalScore = 0;
		for(ParameterStats instance: instances)
		{	if(instance.isAnnotated())
				totalScore++;
		}
		float averageScore = totalScore / (float)instances.size();
		// faut ajouter les moyennes/e-t/t-test ds l'objet stat
		
		
		List<ParameterStats> uniques = result.getParameterInstanceStats();
		
		for(MatawsParameter parameter: parameters)
		{	ParameterStats paramStat = new ParameterStats(parameter);
			
			
		}
		
		logger.decreaseOffset();
		return result;
	}
}
