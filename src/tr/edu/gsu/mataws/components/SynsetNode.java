package tr.edu.gsu.mataws.components;

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

import edu.smu.tspell.wordnet.Synset;

public class SynsetNode{

	private Synset synset;
	private SynsetNode parent;
	private SynsetNode child;
	
	public SynsetNode(Synset synset){
		this.synset = synset;
	}
	
	public Synset getSynset() {
		return synset;
	}
	public void setSynset(Synset synset) {
		this.synset = synset;
	}
	public SynsetNode getParent() {
		return parent;
	}
	public void setParent(SynsetNode parent) {
		this.parent = parent;
	}
	public SynsetNode getChild() {
		return child;
	}
	public void setChild(SynsetNode child) {
		this.child = child;
	}
	public boolean hasChild(){
		if(child != null)
			return true;
		else
			return false;
	}
	
	public boolean equals(Object obj){
		
		SynsetNode s = (SynsetNode) obj;
		if(s.getSynset().equals(this.synset))
			return true;
		else
			return false;
	}
	
	
}
