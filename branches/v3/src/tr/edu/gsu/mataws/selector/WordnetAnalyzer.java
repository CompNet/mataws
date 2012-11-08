package tr.edu.gsu.mataws.selector;

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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import tr.edu.gsu.mataws.components.StringNode;
import tr.edu.gsu.mataws.components.SynsetNode;
import tr.edu.gsu.mataws.components.Tree;
import tr.edu.gsu.mataws2.tools.FileTools;
import edu.smu.tspell.wordnet.AdjectiveSynset;
import edu.smu.tspell.wordnet.AdverbSynset;
import edu.smu.tspell.wordnet.NounSynset;
import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.SynsetType;
import edu.smu.tspell.wordnet.VerbSynset;
import edu.smu.tspell.wordnet.WordNetDatabase;

public class WordnetAnalyzer {

	private WordNetDatabase wd;
	
	public WordnetAnalyzer(){
		System.setProperty("wordnet.database.dir",System.getProperty("user.dir") + File.separator + "dictionary");
System.setProperty("wordnet.database.dir",FileTools.WORDNET_FOLDER); // TODO modif
		wd = WordNetDatabase.getFileInstance();
	}
	
	public List<String> onlyOneRepresenter(List<String> preprocessedResult){
		List<String> result = new ArrayList<String>();
		String temp = onlyOneRepresenterCore(preprocessedResult);

		if(temp!=null){
			result.add(temp);
			return result;
		}
		else if(preprocessedResult.size() > 2){
			List<String> tempList = new ArrayList<String>();
			for(int i=preprocessedResult.size()-1;i>1;i--){
				for(int j = 0;j<preprocessedResult.size()-i+1;j++){
					tempList = new ArrayList<String>();
					for(int k = 0; k<i;k++){
						tempList.add(preprocessedResult.get(k+j));
					}
					temp = onlyOneRepresenterCore(tempList);
					if(temp!=null){
						result = new ArrayList<String>(preprocessedResult);
						String first = tempList.get(0);
						int index = preprocessedResult.indexOf(first);
						result.add(index, temp);
						for (String string : tempList) {
							result.remove(string);
						}
						return result;
					}
				}
			}
		}
		return null;
	}
	
	public String onlyOneRepresenterCore(List<String> preprocessedResult){
		
		StringBuilder sb = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		
		for (String string : preprocessedResult) {
			if(string.length()!=0)
				sb.append(string.charAt(0));
		}
		
		String abbrev = sb.toString().toUpperCase(Locale.ENGLISH);
		
		for (String string : preprocessedResult) {
			sb2.append(string);
			sb2.append(" ");
		}
		String compoundWord = sb2.toString();
		compoundWord = compoundWord.substring(0, compoundWord.length()-1);
		
		Synset[] synsets = wd.getSynsets(compoundWord);
		if(synsets.length != 0){
			for (Synset synset : synsets) {
				String[] strings = synset.getWordForms();
				
				for (String string : strings) {
					String[] splitted = string.split(" ");
					if(splitted.length == 1){
						if(!splitted[0].equals(abbrev))
							return splitted[0];
					}
				}
			}
		}
		return null;
	}
	
	public String nounAdjunctFinder(List<String> preprocessedResult){
		return preprocessedResult.get(preprocessedResult.size()-1);
	}
	
	public String frequentVerbFinder(List<String> preprocessedResult){
		int frequency = 0;
		String result = null;
		for (String string : preprocessedResult) {
			int i = getWordFrequency(string, SynsetType.VERB);
			if(i>frequency){
				frequency = i;
				result = string;
			}
		}
		if(result!=null)
			return result;
		else
			return preprocessedResult.get(0);
	}
	
	public String frequentAdjectiveOrAdverbFinder(List<String> preprocessedResult){
		int frequency = 0;
		String result = null;
		for (String string : preprocessedResult) {
			int i = getWordFrequency(string, SynsetType.ADJECTIVE);
			int k = getWordFrequency(string, SynsetType.ADVERB);
			int j;
			if(i>k)
				j = i;
			else
				j = k;
			if(j>frequency){
				frequency = i;
				result = string;
			}
		}
		if(result!=null)
			return result;
		else{
			if(preprocessedResult.size()==0)
				return "";
			else
				return preprocessedResult.get(0);
		}
			
	}
	
	public List<String> hypernymialRelationFinder(List<String> preprocessedResult){
		List<String> result = new ArrayList<String>();
		String temp = hypernymialRelationFinderCore(preprocessedResult);
		
		if(temp!=null){
			result.add(temp);
			return result;
		}
		else if(preprocessedResult.size() > 2){
			List<String> tempList = new ArrayList<String>();
			for(int i=preprocessedResult.size()-1;i>1;i--){
				for(int j = 0;j<preprocessedResult.size()-i+1;j++){
					tempList = new ArrayList<String>();
					for(int k = 0; k<i;k++){
						tempList.add(preprocessedResult.get(k+j));
					}
					temp = hypernymialRelationFinderCore(tempList);
					if(temp!=null){
						result = new ArrayList<String>(preprocessedResult);
						String first = tempList.get(0);
						int index = preprocessedResult.indexOf(first);
						result.add(index, temp);
						for (String string : tempList) {
							result.remove(string);
						}
						return result;
					}
				}
			}
		}
		return null;
	}
	
	public String hypernymialRelationFinderCore(List<String> preprocessedResult){
		
		List<Tree> treeList = new ArrayList<Tree>();
		
		////////////////////////building trees///////////////////////////////////////
		for (String string : preprocessedResult) {
			Synset[] synsets = wd.getSynsets(string, SynsetType.NOUN);
			List<SynsetNode> snList = new ArrayList<SynsetNode>();
			
			for (Synset synset : synsets) {
				
				NounSynset nSynset = (NounSynset) synset;

				SynsetNode sn = new SynsetNode(nSynset);
				snList.add(sn);
				
				NounSynset[] hyperSynsets1 = nSynset.getHypernyms();
				SynsetNode sn1 = null;
				if(hyperSynsets1.length != 0){
					sn1 = new SynsetNode(hyperSynsets1[0]);
					sn1.setParent(sn);
					sn.setChild(sn1);
					
					NounSynset[] hyperSynsets2 = hyperSynsets1[0].getHypernyms();
					SynsetNode sn2 = null;
					if(hyperSynsets2.length != 0){
						sn2 = new SynsetNode(hyperSynsets2[0]);
						sn2.setParent(sn1);
						sn1.setChild(sn2);
					}
				}
			}
			StringNode stringNode = new StringNode(string);
			stringNode.setChildren(snList);
			Tree tree = new Tree(stringNode);
			treeList.add(tree);
		}
		
		//////////////////intersection between trees////////////////
		List<SynsetNode> intersList = null;
		int intersectionQuantity = 0;
		
		/*for(int i=0;i<treeList.size()-1;i++){
			for(int j = treeList.size()-1; j>i;j--){
				temp = retainTrees(treeList.get(i), treeList.get(j));
				if(temp.size() > intersectionQuantity){
					intersectionQuantity = temp.size();
					intersList = new ArrayList<SynsetNode>(temp);
				}
			}
		}*/
		
		for(int i=0;i<treeList.size()-1;i++){
			List<SynsetNode> temp = treeList.get(i).getSynsetList();
			for(int j = treeList.size()-1; j>i;j--){
				temp.retainAll(treeList.get(j).getSynsetList());
				if(temp.size() > intersectionQuantity){
					intersectionQuantity = temp.size();
					intersList = new ArrayList<SynsetNode>(temp);
				}
			}
		}
		
		/*for (Tree tree : treeList) {
			temp.retainAll(tree.getSynsetList());
			System.out.println("temp size: "+temp.size());
			if(temp.size()>0){
				intersList.retainAll(tree.getSynsetList());
				System.out.println("intersL size: "+intersList.size());
			}
		}*/
		
		int result = 0;
		Tree resultTree = null;
		
		if(intersList != null){
			for (Tree tree : treeList) {
				for(SynsetNode synsetNode : intersList){
					if(tree.findDepth(synsetNode) > result){
						result = tree.findDepth(synsetNode);
						resultTree = tree;
					}
				}
			}
		}
		if(resultTree == null)
			return null;
		else
			return resultTree.getRoot().getRoot();
	}
	
	/*public List<SynsetNode> retainTrees(Tree tree1, Tree tree2){
		List<SynsetNode> temp = new ArrayList<SynsetNode>();
		List<SynsetNode> temp2 = new ArrayList<SynsetNode>();
		List<SynsetNode> result = new ArrayList<SynsetNode>();
		
		temp = tree1.getSynsetList();
		temp2 = tree2.getSynsetList();
		
		for (SynsetNode synsetNode : temp) {
			for (SynsetNode synsetNode2 : temp2) {
				if(synsetNode.getSynset().equals(synsetNode2.getSynset())){
					result.add(synsetNode);
				}
			}
		}
		return result;
	}*/
	
	public List<String> holonymialRelationFinder(List<String> preprocessedResult){
		
		List<String> result = new ArrayList<String>();
		String temp = holonymialRelationFinderCore(preprocessedResult);
		
		if(temp!=null){
			result.add(temp);
			return result;
		}
		else if(preprocessedResult.size() > 2){
			List<String> tempList = new ArrayList<String>();
			for(int i=preprocessedResult.size()-1;i>1;i--){
				for(int j = 0;j<preprocessedResult.size()-i+1;j++){
					tempList = new ArrayList<String>();
					for(int k = 0; k<i;k++){
						tempList.add(preprocessedResult.get(k+j));
					}
					temp = holonymialRelationFinderCore(tempList);
					if(temp!=null){
						result = new ArrayList<String>(preprocessedResult);
						String first = tempList.get(0);
						int index = preprocessedResult.indexOf(first);
						result.add(index, temp);
						for (String string : tempList) {
							result.remove(string);
						}
						return result;
					}
				}
			}
		}
		
		return null;
	}
	
	public String holonymialRelationFinderCore(List<String> preprocessedResult){
		
		List<List<Synset>> generalList = new ArrayList<List<Synset>>();
		
		for (String string : preprocessedResult) {
			Synset[] synsets = wd.getSynsets(string, SynsetType.NOUN);
			
			List<Synset> list = new ArrayList<Synset>();
			for (Synset synset : synsets) {
				
				NounSynset nSynset = (NounSynset) synset;
				NounSynset[] hyperSynsets1 = nSynset.getMemberHolonyms();
				NounSynset[] hyperSynsets2 = nSynset.getPartHolonyms();
				NounSynset[] hyperSynsets3 = nSynset.getSubstanceHolonyms();
				
				for (NounSynset nounSynset : hyperSynsets1) {
					list.add(nounSynset);
				}
				for (NounSynset nounSynset : hyperSynsets2) {
					list.add(nounSynset);
				}
				for (NounSynset nounSynset : hyperSynsets3) {
					list.add(nounSynset);
				}
			}
			generalList.add(list);
		}
		
		if(generalList.size()>0){
			for (int i=0; i<generalList.size()-1;i++) {
				generalList.get(0).retainAll(generalList.get(i+1));
			}
		}
		
		if(generalList.size()>0){
			if(generalList.get(0).size()>0){
				return generalList.get(0).get(0).getWordForms()[0];
			}
		}
		return null;
	}
	
	public int getWordFrequency(String word, SynsetType type){
		
		Synset[] synsets = wd.getSynsets(word, type);
		if(synsets.length==0)
			return 0;
		
		if(type.equals(SynsetType.NOUN)){
			NounSynset ns = (NounSynset) synsets[0];
			return ns.getTagCount(ns.getWordForms()[0]);
		} else if(type.equals(SynsetType.VERB)){
			VerbSynset vs = (VerbSynset) synsets[0];
			return vs.getTagCount(vs.getWordForms()[0]);
		} else if(type.equals(SynsetType.ADJECTIVE)){
			AdjectiveSynset as = (AdjectiveSynset) synsets[0];
			return as.getTagCount(as.getWordForms()[0]);
		} else {
			AdverbSynset as = (AdverbSynset) synsets[0];
			return as.getTagCount(as.getWordForms()[0]);
		}
	}
}
