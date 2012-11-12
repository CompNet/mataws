package tr.edu.gsu.mataws.components;

public class CsvObjectForWords {

	private String word;
	private int occurence;
	private String concept;
	private int selected;

	public CsvObjectForWords(String word, String concept) {
		this.word = word;
		this.concept = concept;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getOccurence() {
		return occurence;
	}

	public void setOccurence(int occurence) {
		this.occurence = occurence;
	}

	public String getConcept() {
		return concept;
	}

	public void setConcept(String concept) {
		this.concept = concept;
	}

	public int getSelected() {
		return selected;
	}

	public void setSelected(int selected) {
		this.selected = selected;
	}

}
