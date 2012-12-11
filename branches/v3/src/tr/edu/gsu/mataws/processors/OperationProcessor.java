package tr.edu.gsu.mataws.processors;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.smu.tspell.wordnet.Synset;

import tr.edu.gsu.mataws.component.associator.DefaultAssociator;
import tr.edu.gsu.mataws.component.assorter.AbstractAssorter;
import tr.edu.gsu.mataws.component.assorter.DefaultAssorter;
import tr.edu.gsu.mataws.component.indentificator.AbstractIdentificator;
import tr.edu.gsu.mataws.component.indentificator.DefaultIdentificator;
import tr.edu.gsu.mataws.component.preparator.AbstractPreparator;
import tr.edu.gsu.mataws.component.selector.DefaultSelector;
import tr.edu.gsu.mataws.data.IdentifiedWord;
import tr.edu.gsu.mataws.data.MatawsParameter;
import tr.edu.gsu.mataws.processors.parameter.ParameterProcessor;
import tr.edu.gsu.sine.col.Operation;
import tr.edu.gsu.sine.col.Parameter;
import tr.edu.gsu.sine.col.Way;

/**
 * This class takes advantage of an operation name to
 * analyze the names of its parameter. Some operation names
 * have a certain form, which gives a hint about the meaning
 * of its parameters. 
 * <br/>
 * For instance, for an operation whose name is 
 * {@code celsiusToFahrenheit} and parameters are {@code inParam}
 * and {@code outParam}, we can suppose the first parameter 
 * represents Celsisus degrees while the second is Fahrenheit degrees.
 * 
 * @author Vincent Labatut
 */
public class OperationProcessor
{	
	/**
	 * Builds a standard operation processor.
	 */
	public OperationProcessor()
	{	parameterProcessor = new ParameterProcessor();
		identificator = new DefaultIdentificator();
		selector = new DefaultSelector();
		assorter = new DefaultAssorter();
		associator = new DefaultAssociator();
	}
	
	///////////////////////////////////////////////////////////
	//	PROCESS							///////////////////////
	///////////////////////////////////////////////////////////
	/** Processor used to annotate the parameters */
	private ParameterProcessor parameterProcessor;
	/** Preparator component used to split the operation name */
	protected AbstractPreparator<Synset> preparator;
	/** Identificator component used to identify distinct part in the split operation name */
	private AbstractIdentificator<Synset> identificator;
	/** Selector component used to simplify the list of words extracted from operation names */
	protected static DefaultSelector selector;
	/** Assorter component used to match parts identified in the operation name and parameters */
	private AbstractAssorter<Synset> assorter;
	/** Associator component used to identify a concept for a parameter */
	private static DefaultAssociator associator;

	/**
	 * Processes one operation. Fist, it tries to use the
	 * name of the operation to help annotating the parameters.
	 * If this fails, then each parameter is processed separately.
	 * 
	 * @param operation
	 * 		A Sine object representing the operation to process. 
	 * @return
	 * 		A list of supposedly annotated parameters from this operation.
	 */
	public List<MatawsParameter> process(Operation operation)
	{	// get the list of the operation parameters
		List<MatawsParameter> result = new ArrayList<MatawsParameter>();
		List<Parameter> params = operation.getParameters();
		for(Parameter param: params)
		{	MatawsParameter parameter = new MatawsParameter(param);
			result.add(parameter);
		}
		
		// process the parameters separately
		for(MatawsParameter parameter: result)
		{	if(parameter.getConcept()==null)
				parameterProcessor.process(parameter);
		}
		
		// split the operation name
		String opName = operation.getName();
		List<IdentifiedWord<Synset>> operationList = preparator.preparate(opName);
		// identify its parts
		Map<Way,List<List<IdentifiedWord<Synset>>>> operationMap = identificator.identify(operationList);
		// reduce them to one word by parameter
		Map<Way,List<IdentifiedWord<Synset>>> wordMap = new HashMap<Way, List<IdentifiedWord<Synset>>>();
		for(Way way: Way.values())
		{	List<List<IdentifiedWord<Synset>>> lists = operationMap.get(way);
			List<IdentifiedWord<Synset>> tempList = new ArrayList<IdentifiedWord<Synset>>();
			for(List<IdentifiedWord<Synset>> list: lists)
			{	IdentifiedWord<Synset> word = selector.select(list);
				tempList.add(word);
			}
			wordMap.put(way, tempList);
		}
		// try assorting words and parameters
		assorter.assort(wordMap, result);
		// associate concepts to the concerned parameters
		for(MatawsParameter param: result)
		{	@SuppressWarnings("unchecked")
			IdentifiedWord<Synset> word = (IdentifiedWord<Synset>)param.getRepresentativeWord();
			if(word!=null && param.getConcept()==null)
			{	String concept = associator.associate(word);
				param.setConcept(concept);
			}
		}
		
		return result;
	}
}
