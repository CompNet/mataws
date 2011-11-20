package tr.edu.gsu.mataws.analyzer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.SynsetType;
import edu.smu.tspell.wordnet.WordNetDatabase;

public class Analyzer {

	private AnalysisType analysisType = AnalysisType.NoAnalysis;
	
	public String analyzeWords(List<String> preprocessedResult){
		
		System.setProperty("wordnet.database.dir",System.getProperty("user.dir") + File.separator + "dictionary");
		
		WordNetDatabase wd = WordNetDatabase.getFileInstance();
		
		WordnetAnalyzer wa = new WordnetAnalyzer();
		
		String result = "";
		
		List<String> analyzeList = new ArrayList<String>();
		for (String string : preprocessedResult) {
			analyzeList.add(string);
		}
		
		Synset[] nounSynsets = null;
		Synset[] verbSynsets = null;
		Synset[] adjectifSynsets = null;
		Synset[] adverbSynsets = null;
		
		for(String string : preprocessedResult){
			nounSynsets = wd.getSynsets(string, SynsetType.NOUN);
			verbSynsets = wd.getSynsets(string, SynsetType.VERB);
			adjectifSynsets = wd.getSynsets(string, SynsetType.ADJECTIVE);
			adverbSynsets = wd.getSynsets(string, SynsetType.ADVERB);
			
			/*if(nounSynsets.length > verbSynsets.length && 
					nounSynsets.length > adjectifSynsets.length && 
					nounSynsets.length > adverbSynsets.length){
			
			} else{
				analyzeList.remove(string);
			}*/
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
		
		if(preprocessedResult.size() == 0)
			return result;
		
		if(analyzeList.size() == 0){
			result = preprocessedResult.get(0);
			analysisType = AnalysisType.NonNounAnnotation;
		}
		else if(analyzeList.size() == 1){
			result = analyzeList.get(0);
			analysisType = AnalysisType.OnlyOneRemaining;
		}
		else if(wa.onlyOneRepresenter(analyzeList) != null){
			result = wa.onlyOneRepresenter(analyzeList);
			analysisType = AnalysisType.OnlyOneRepresenter;
		}
		else if(wa.hypernymialRelationFinder(analyzeList) != null){
			result = wa.hypernymialRelationFinder(analyzeList);
			analysisType = AnalysisType.HypernymialRelation;
		}
		else if(wa.holonymialRelationFinder(analyzeList) != null){
			result = wa.holonymialRelationFinder(analyzeList);
			analysisType = AnalysisType.HolonymialRelation;
		}
		else{
			result = wa.nounAdjunctFinder(analyzeList);
			analysisType = AnalysisType.NounAdjunct;
		}
		
		return result;
	}
	
	public AnalysisType getAnalysisType(){
		return analysisType;
	}
	
}
