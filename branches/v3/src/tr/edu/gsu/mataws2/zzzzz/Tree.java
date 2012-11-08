package tr.edu.gsu.mataws2.zzzzz;

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

public class Tree {

	private StringNode root;
	
	public Tree(StringNode root){
		this.root = root;
	}

	public StringNode getRoot() {
		return root;
	}

	public void setRoot(StringNode root) {
		this.root = root;
	}
	
	
	public int findDepth(SynsetNode sn){
		int result = 0;
		if(root.getChildren().contains(sn))
			result = 1;
		if(result == 0){
			for(SynsetNode synsetNode: root.getChildren()){
				if(synsetNode.hasChild()){
					if(synsetNode.getChild().equals(sn)){
						result = 2;
						break;
					}
				}
			}
			
		}
		if(result == 0){
			for(SynsetNode synsetNode: root.getChildren()){
				if(synsetNode.hasChild()){
					if(synsetNode.getChild().hasChild()){
						if(synsetNode.getChild().getChild().equals(sn)){
							result = 3;
							break;
						}
					}
				}
				
			}
		}
		
		return result;
	}
	
	public List<SynsetNode> getSynsetList(){
		List<SynsetNode> result = new ArrayList<SynsetNode>();
		
		for (SynsetNode synsetNode : root.getChildren()) {
			result.add(synsetNode);
			if(synsetNode.hasChild()){
				result.add(synsetNode.getChild());
				if(synsetNode.getChild().hasChild())
					result.add(synsetNode.getChild().getChild());
			}
			
		}
		
		return result;
	}
	
	/*public List<Synset> getSynsets(List<SynsetNode> synsetNodes){
		List<Synset> result = new ArrayList<Synset>();
		for (SynsetNode synsetNode : synsetNodes) {
			result.add(synsetNode.getSynset());
		}
		return result;
	}
	
	public SynsetNode getNode(Synset synset){
		List<SynsetNode> list = getSynsetList();
		for (SynsetNode synsetNode : list) {
			if(synsetNode.getSynset().equals(synset))
				return synsetNode;
		}
		return null;
	}*/
}
