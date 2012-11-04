package tr.edu.gsu.mataws.components;

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