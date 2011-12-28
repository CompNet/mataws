package tr.edu.gsu.mataws.components;

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
