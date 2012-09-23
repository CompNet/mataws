package tr.edu.gsu.mataws.analyzer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import tr.edu.gsu.mataws.components.TraceType;
import tr.edu.gsu.mataws.components.TraceableParameter;

import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.SynsetType;
import edu.smu.tspell.wordnet.WordNetDatabase;

public class Analyzer {

	private AnalysisType analysisType = AnalysisType.NoAnalysis;
	private WordNetDatabase wd;
	
	public Analyzer(){
		System.setProperty("wordnet.database.dir",System.getProperty("user.dir") + File.separator + "dictionary");
		wd = WordNetDatabase.getFileInstance();
	}
	
	public String analyzeWords(TraceableParameter tParameter, List<String> preprocessedResult){
		
		WordnetAnalyzer wa = new WordnetAnalyzer();
		
		String result = "";
		List<String> controlList = new ArrayList<String>();
		
		List<String> analyzeList = new ArrayList<String>();
		for (String string : preprocessedResult) {
			analyzeList.add(string);
		}

		
		if(preprocessedResult.size() == 0)
			return result;
		
		//algo starts here
		if(analyzeList.size() == 1){
			result = analyzeList.get(0);
			analysisType = AnalysisType.OnlyOneRemaining;
			tParameter.addTraceList(TraceType.OnlyOneRemaining);
			return result;
		}
		
		if(wa.onlyOneRepresenter(analyzeList) != null){
			controlList = wa.onlyOneRepresenter(analyzeList);
			tParameter.addTraceList(TraceType.OnlyOneRepresenter);
			if(controlList.size() == 1){
				result = controlList.get(0);
				analysisType = AnalysisType.OnlyOneRepresenter;
				return result;
			}else{
				for(String string:controlList){
					tParameter.addControlList(string);
				}
				return analyzeWords(tParameter, controlList);
			}
		}
		else if(wa.hypernymialRelationFinder(analyzeList) != null){
			controlList = wa.hypernymialRelationFinder(analyzeList);
			tParameter.addTraceList(TraceType.HypernymialRelation);
			if(controlList.size() == 1){
				result = controlList.get(0);
				analysisType = AnalysisType.HypernymialRelation;
				return result;
			}else{
				for(String string:controlList){
					tParameter.addControlList(string);
				}
				return analyzeWords(tParameter, controlList);
			}
		}
		else if(wa.holonymialRelationFinder(analyzeList) != null){
			controlList = wa.holonymialRelationFinder(analyzeList);
			tParameter.addTraceList(TraceType.HolonymialRelation);
			if(controlList.size() == 1){
				result = controlList.get(0);
				analysisType = AnalysisType.HolonymialRelation;
				return result;
			}else{
				for(String string:controlList){
					tParameter.addControlList(string);
				}
				return analyzeWords(tParameter, controlList);
			}
		}
		else{
			Synset[] nounSynsets = null;
			Synset[] verbSynsets = null;
			Synset[] adjectifSynsets = null;
			Synset[] adverbSynsets = null;
			
			boolean enteredVerb = false;
			boolean enteredOther = false;
			
			//here we try to get only nouns
			for(String string : preprocessedResult){
				nounSynsets = wd.getSynsets(string, SynsetType.NOUN);
				verbSynsets = wd.getSynsets(string, SynsetType.VERB);
				adjectifSynsets = wd.getSynsets(string, SynsetType.ADJECTIVE);
				adverbSynsets = wd.getSynsets(string, SynsetType.ADVERB);
				
				if(nounSynsets.length > verbSynsets.length && 
						nounSynsets.length > adjectifSynsets.length && 
						nounSynsets.length > adverbSynsets.length){
				
				} else{
					analyzeList.remove(string);
				}
				if(nounSynsets.length != 0){
					if(verbSynsets.length !=0){
						if(((float)((float)nounSynsets.length / verbSynsets.length)) < 0.3)
							analyzeList.remove(string);
					}else if(adverbSynsets.length !=0){
						if(((float)((float)nounSynsets.length / adverbSynsets.length)) < 0.3)
							analyzeList.remove(string);
					}else if(adjectifSynsets.length !=0){
						if(((float)((float)nounSynsets.length / adjectifSynsets.length)) < 0.3)
							analyzeList.remove(string);
					}
				}else
					analyzeList.remove(string);
			}
			
			//in case of no noun exists in the list, then we try to get only verbs 
			if(analyzeList.size() == 0){
				
				enteredVerb = true;
				
				for (String string : preprocessedResult) {
					analyzeList.add(string);
				}
				
				for(String string : preprocessedResult){
					nounSynsets = wd.getSynsets(string, SynsetType.NOUN);
					verbSynsets = wd.getSynsets(string, SynsetType.VERB);
					adjectifSynsets = wd.getSynsets(string, SynsetType.ADJECTIVE);
					adverbSynsets = wd.getSynsets(string, SynsetType.ADVERB);
					
					if(verbSynsets.length != 0){
						if(adverbSynsets.length != 0){
							if(((float)((float)verbSynsets.length / adverbSynsets.length)) < 0.3)
								analyzeList.remove(string);
						}else if(adjectifSynsets.length != 0){
							if(((float)((float)verbSynsets.length / adjectifSynsets.length)) < 0.3)
								analyzeList.remove(string);
						}
					}else
						analyzeList.remove(string);
				}
			}
			
			if(analyzeList.size() == 0){
				enteredOther = true;
				for (String string : preprocessedResult) {
					analyzeList.add(string);
				}
			}
			
			if(!enteredVerb){
				tParameter.addTraceList(TraceType.NounAdjunct);
				analysisType = AnalysisType.NounAdjunct;
				result = wa.nounAdjunctFinder(analyzeList);
			}
			else if(!enteredOther){
				tParameter.addTraceList(TraceType.SimpleVerbAnnotation);
				analysisType = AnalysisType.SimpleVerbAnnotation;
				result = wa.frequentVerbFinder(analyzeList);
			}
			else{
				tParameter.addTraceList(TraceType.NonNounVerbAnnotation);
				analysisType = AnalysisType.NonNounVerbAnnotation;
				result = wa.frequentAdjectiveOrAdverbFinder(analyzeList);
			}
		}
		return result;
	}
	
	public AnalysisType getAnalysisType(){
		return analysisType;
	}
	
	public String getWordUsage(String word){
		Synset[] sNoun = wd.getSynsets(word, SynsetType.NOUN);
		Synset[] sVerb = wd.getSynsets(word, SynsetType.VERB);
		String result = "";
		
		if(sNoun.length!=0){
			if(((float)((float)sNoun.length / sVerb.length)) > 0.3){
				result += sNoun[0].getDefinition();
				String[] s = sNoun[0].getUsageExamples();
				for (String string : s) {
					result += "; "+string;
				}
			} else{
				result += sVerb[0].getDefinition();
				String[] s = sVerb[0].getUsageExamples();
				for (String string : s) {
					result += "; "+string;
				}
			}
		} else if(sVerb.length!=0){
			result += sVerb[0].getDefinition();
			String[] s = sVerb[0].getUsageExamples();
			for (String string : s) {
				result += "; "+string;
			}
		} else{
			result = null;
		}
		return result;
	}
}
