package tr.edu.gsu.mataws.components;

import java.util.List;

import edu.smu.tspell.wordnet.Synset;

public class StringNode {

	private String root;
	private List<SynsetNode> children;
	
	public StringNode(String root){
		this.root = root;
	}
	
	public String getRoot() {
		return root;
	}
	public void setRoot(String root) {
		this.root = root;
	}
	public List<SynsetNode> getChildren() {
		return children;
	}
	public void setChildren(List<SynsetNode> children) {
		this.children = children;
	}

}
